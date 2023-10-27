package br.ufmg.engsoft2.gameloan.repository;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.helper.Helper;

import java.util.ArrayList;
import java.util.List;

public class GameDB {
    private static GameDB instance;
    private final List<Game> gameDB = new ArrayList<>();

    private GameDB() {
        gameDB.addAll(
                List.of(
                        new Game("Uno", "Game de Cartas", 5.0, "admin@email.com"),
                        new Game("Dos", "Game de Cartas 2", 10.0, "admin@email.com"),
                        new Game("Batalha Naval", "Batalha com navios", 2.0, "teste@email.com")));
    }

    public static GameDB getInstance() {
        if (instance == null) {
            instance = new GameDB();
        }
        return instance;
    }

    public void add(Game game) {
        gameDB.add(game);
    }

    public void printAll() {
        for (Game game : gameDB) {
            System.out.println("Nome: " + game.getName());
            System.out.println("Descrição: " + game.getDescription());
            System.out.println("Preço: R$" + game.getPrice());
            System.out.println("Email do Dono: " + game.getOwnerEmail());
            System.out.println("--------------------");
            Helper.timer();
        }
    }

    public List<Game> getAll() {
        return gameDB;
    }

    public void searchByKey(String searchKey) {
        List<Game> foundGames = new ArrayList<>();

        for (Game game : gameDB) {
            if (game.getName().toLowerCase().contains(searchKey.toLowerCase()) ||
                    game.getDescription().toLowerCase().contains(searchKey.toLowerCase())) {
                foundGames.add(game);
            }
        }

        if (foundGames.isEmpty()) {
            System.out.println("Nenhum jogo encontrado com a palavra-chave: " + searchKey);
        } else {
            System.out.println("Jogos encontrados com a palavra-chave: " + searchKey);
            for (Game game : foundGames) {
                System.out.println("Nome: " + game.getName());
                System.out.println("Descrição: " + game.getDescription());
                System.out.println("Preço: R$" + game.getPrice());
                System.out.println("Email do Dono: " + game.getOwnerEmail());
                System.out.println("--------------------");
                Helper.timer();
            }
        }
    }
}
