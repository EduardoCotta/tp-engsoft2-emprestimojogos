package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.repository.GameDB;
import br.ufmg.engsoft2.gameloan.session.SessionManager;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import static br.ufmg.engsoft2.gameloan.helper.ValidatorHelper.*;

public class GameService {
	public static final String HIFEN_LINE = "-------------------------------------------------------%n";
	private GameDB gameDB;

    public GameService() {
        gameDB = GameDB.getInstance();
    }

    public void addGame(String name, String description, double price){
        isNullOrEmpty(name, "Nome");
		isNullOrEmpty(description, "Descrição");
		isPositive(price, "Preço");

        gameDB.add
                (new Game(name, description, price, SessionManager.getSession().getLoggedUser().getEmail()));
    }
    
    public Game getGameByName(String name) {
    	List<Game> games = GameDB.getInstance().getAll();
    	games = games.stream()
				.filter(game -> game.getName().equals(name))
				.toList();
    	
    	if(games.isEmpty()) {
    		throw new InputMismatchException("Game não encontrado.");
    	}
    	
    	return games.get(0);
    }
    
    public List<Game> listGamesByUser(User loggedUser) {
    	isObjectNull(loggedUser, "Usuário logado");
		isNullOrEmpty(loggedUser.getEmail(), "Email do usuário logado");

    	return this.gameDB
				   .getAll()
				   .stream()
				   .filter(game -> game.getOwnerEmail().equals(loggedUser.getEmail()))
					.toList();
    }
    
	public void printGamesListByUser(User loggedUser) {
			List<Game> userGames = this.listGamesByUser(loggedUser);

			if(userGames.isEmpty()) {
	    		System.out.println("Você não possui nenhum jogo cadastrado!");
	    	}
	    	
	    	else {
	    		System.out.printf(HIFEN_LINE);
	    		System.out.printf("|              Jogos cadastrados de %-18s|%n", loggedUser.getName());
	    		System.out.printf(HIFEN_LINE);
	    		System.out.printf("| %-15s | %-15s | %-15s |%n", "Nome", "Descrição", "Preço");
	    		System.out.printf(HIFEN_LINE);
	    		
	    		userGames.forEach(System.out::println);
	
	    		System.out.printf(HIFEN_LINE);
	    	}
		}
}
