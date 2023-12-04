package br.ufmg.engsoft2.gameloan.helper.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufmg.engsoft2.gameloan.domain.Game;


public class GameMapper {

    private GameMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Game> map(ResultSet resultSet) throws SQLException {
        List<Game> games = new ArrayList<>();
        while (resultSet.next()) {
            var id = resultSet.getObject("id", java.util.UUID.class);
            var name = resultSet.getString("name");
            var description = resultSet.getString("description");
            var price = resultSet.getDouble("price");
            var ownerEmail = resultSet.getString("ownerEmail");

            var game = new Game(id, name, description, price, ownerEmail);
            games.add(game);
        }

        return games;
    }
}
