package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.repository.GameRepository;
import br.ufmg.engsoft2.gameloan.session.SessionManager;

import java.util.InputMismatchException;
import java.util.List;

import static br.ufmg.engsoft2.gameloan.helper.ValidatorHelper.*;

public class GameService {
	public static final String HIFEN_LINE = "-------------------------------------------------------%n";
	private final GameRepository gameRepository;

	public GameService() {
		gameRepository = new GameRepository();
	}

    public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

    public void addGame(String name, String description, double price){
        isNullOrEmpty(name, "Nome");
		isNullOrEmpty(description, "Descrição");
		isPositive(price, "Preço");

        gameRepository.add
                (new Game(name, description, price, SessionManager.getSession().getLoggedUser().getEmail()));
    }

	public List<Game> getAll() {
		return gameRepository.getAll();
	}
    
    public Game getGameByName(String name) {
    	List<Game> games = gameRepository.getAll();
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

    	return this.gameRepository
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

	public void printAll(List<Game> games){
		System.out.printf(HIFEN_LINE);
		System.out.println("|              Jogos disponiveis              |");
		System.out.printf(HIFEN_LINE);

		games.forEach(System.out::println);

		System.out.printf(HIFEN_LINE);
	}

	public List<Game> searchByKeyword(String keyword){
		return gameRepository.getAllBySearchKey(keyword);
	}
}
