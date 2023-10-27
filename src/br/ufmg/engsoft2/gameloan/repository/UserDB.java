package br.ufmg.engsoft2.gameloan.repository;

import br.ufmg.engsoft2.gameloan.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private static UserDB instance;
    private List<User> users = new ArrayList<User>();

    private UserDB() {
        this.users
                .addAll(
                        List.of(
                                new User("admin@email.com", "Admin", "Todos", "admin"),
                                new User("teste@email.com", "Eduardo", "", "123")
                        )
                );
    }

    public static UserDB getInstance() {
        if (instance == null) {
            instance = new UserDB();
        }
        return instance;
    }

    public void add(User user){
        users.add(user);
    }

    public List<User> getAll(){
        return users;
    }
}
