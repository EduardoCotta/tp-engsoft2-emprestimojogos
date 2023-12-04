package br.ufmg.engsoft2.gameloan.repository;

import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.helper.mapper.UserMapper;
import br.ufmg.engsoft2.gameloan.infra.database.ConnectionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final ConnectionManager connectionManager;

    public UserRepository() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    public boolean add(User user){
        String sql = "INSERT INTO account (email, name, interests, password) VALUES (?, ?, ?, ?)";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getInterests());
            preparedStatement.setString(4, user.getPassword());

            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAll(){
        String sql = "SELECT * FROM account";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            var resultSet = preparedStatement.executeQuery();

            return UserMapper.map(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getByEmail(String email){
        String sql = "SELECT * FROM account WHERE email = ?";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            var resultSet = preparedStatement.executeQuery();

            Optional<User> user = UserMapper.map(resultSet).stream().findFirst();

            return user.orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByEmail(String email){
        String sql = "DELETE FROM account WHERE email = ?";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
