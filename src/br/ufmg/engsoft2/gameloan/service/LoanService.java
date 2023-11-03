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
		if(deadlineString == null || "".equals(deadlineString)){
			throw new InputMismatchException("A data limite não pode ser vazia");
		}

		if(ownerEmail == null || "".equals(ownerEmail)) {
			throw new InputMismatchException("Usuário dono do jogo não encontrado.");
		}

		if(gameName == null || "".equals(gameName)) {
			throw new InputMismatchException("Nenhum jogo foi selecionado.");
		}

		Date deadline = Helper.convertStringToDate(deadlineString);

		if(deadline == null || !validateDeadline(deadline)) {
			throw new InputMismatchException("A data limite deve ser posterior à data atual.");
		}

		User owner = userService.getByEmail(ownerEmail);
		Game requestedGame = gameService.getGameByName(gameName);

		userService.checkIfUserHasGame(gameName, ownerEmail);
		
		loanDB.add
				(new Loan(owner, SessionManager.getSession().getLoggedUser(), requestedGame, deadline));
	}
	
	public List<Loan> listLoansByUser(User loggedUser) {
		
		if(loggedUser == null) {
			
			throw new InputMismatchException("Usuário não encontrado.");
			
		} else if(loggedUser.getEmail() == null || loggedUser.getEmail() == "") {
			
			throw new InputMismatchException("Email de usuário faltante.");
			
		}
		
		return this.loanDB
				   .getAll()
				   .stream()
				   .filter(loan -> loan.getRequester().getEmail().equals(loggedUser.getEmail()))
				   .toList();
	}
	
	private static boolean validateDeadline(Date deadline) {
       Date today = Helper.getCurrentDateWithoutTime();

	   return today.compareTo(deadline) < 0;
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
    		
    		for(Loan loan : userLoans) {
    			
    			String formattedDeadline = Helper.formatDateToString(loan.getDeadline());
    			
		    	System.out.printf("| %-15s | %-15s | %-15s |%n", loan.getOwner().getName(),
		    													 loan.getRequestedGame().getName(),
		    													 formattedDeadline
	    		);
    		}

    		System.out.printf("-------------------------------------------------------%n");
    	}
	}
}
