package br.ufmg.engsoft2.gameloan.domain;

public class User {

    private final String email;
    private final String name;
    private final String interests;
    private final String password;

    public User(String email, String nome, String interests, String password) {
        this.email = email;
        this.name = nome;
        this.interests = interests;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
        this.name = "";
        this.interests = "";
        this.password = "";
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getInterests() {
        return interests;
    }

    public String getPassword() {
        return password;
    }
}
