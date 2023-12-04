package br.ufmg.engsoft2.gameloan.helper.mapper;

import br.ufmg.engsoft2.gameloan.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static List<User> map(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            var name = resultSet.getString("name");
            var email = resultSet.getString("email");
            var interests = resultSet.getString("interests");
            var password = resultSet.getString("password");

            var user = new User(email, name, interests, password);
            users.add(user);
        }

        return users;
    }
}
