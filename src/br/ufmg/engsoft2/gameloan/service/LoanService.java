package br.ufmg.engsoft2.gameloan.service;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.repository.LoanDB;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.helper.Helper;

import static br.ufmg.engsoft2.gameloan.helper.ValidatorHelper.*;

public class LoanService {

	public static final String HIFEN_LINE = "-------------------------------------------------------%n";
	private LoanDB loanDB;
	private UserService userService;
	private GameService gameService;
	
	public LoanService() {
		loanDB = LoanDB.getInstance();
		userService = new UserService();
		gameService = new GameService();
	}
	
	public void addLoan(String ownerEmail, String gameName, String deadlineString) {
		isNullOrEmpty(deadlineString, "Data limite");
		isNullOrEmpty(ownerEmail, "Email do dono do jogo");
		isNullOrEmpty(gameName, "Nome do jogo");

		Date deadline = Helper.convertStringToDate(deadlineString);

		validateDeadline(deadline);

		User owner = userService.getByEmail(ownerEmail);
		Game requestedGame = gameService.getGameByName(gameName);

		userService.checkIfUserHasGame(gameName, ownerEmail);
		
		loanDB.add
				(new Loan(owner, SessionManager.getSession().getLoggedUser(), requestedGame, deadline));
	}
	
	public List<Loan> listLoansByUser(User loggedUser) {
		isObjectNull(loggedUser, "Usuário logado");
		isNullOrEmpty(loggedUser.getEmail(), "Email do usuário logado");
		
		return this.loanDB
				   .getAll()
				   .stream()
				   .filter(loan -> loan.getRequester().getEmail().equals(loggedUser.getEmail()))
				   .toList();
	}

	public void printLoansByUser(User loggedUser) {
		List<Loan> userLoans = this.listLoansByUser(loggedUser);

		if(userLoans.isEmpty()) {
    		System.out.println("Você não possui nenhum empréstimo cadastrado!");
    	} else {
    		System.out.printf(HIFEN_LINE);
    		System.out.printf("|        Empréstimos cadastrados de %-18s|%n", loggedUser.getName());
    		System.out.printf(HIFEN_LINE);
    		System.out.printf("| %-15s | %-15s | %-15s |%n", "Dono", "Game solicitado", "Data limite");
    		System.out.printf(HIFEN_LINE);
    		
    		userLoans.forEach(System.out::println);

    		System.out.printf("-------------------------------------------------------%n");
    	}
	}
}
