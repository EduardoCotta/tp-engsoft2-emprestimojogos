package br.ufmg.engsoft2.gameloan.repository;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.infra.database.ConnectionManager;
import br.ufmg.engsoft2.gameloan.helper.mapper.GameMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class GameRepository {
    private final ConnectionManager connectionManager;

    public GameRepository() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    public boolean add(Game game){

        String sql = "INSERT INTO game (id, name, description, price, ownerEmail) VALUES (?, ?, ?, ?, ?)";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setObject(1, game.getId());
            preparedStatement.setString(2, game.getName());
            preparedStatement.setString(3, game.getDescription());
            preparedStatement.setDouble(4, game.getPrice());
            preparedStatement.setString(5, game.getOwnerEmail());



            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Game> getAll(){
        String sql = "SELECT * FROM game";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            var resultSet = preparedStatement.executeQuery();

            return GameMapper.map(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Game> getAllBySearchKey(String key){
        String sql = "SELECT * FROM game WHERE name LIKE ? OR description LIKE ?";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + key + "%");
            preparedStatement.setString(2, "%" + key + "%");

            var resultSet = preparedStatement.executeQuery();

            return GameMapper.map(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGameById(UUID id){
        String sql = "DELETE FROM game WHERE id=?";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setObject(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
