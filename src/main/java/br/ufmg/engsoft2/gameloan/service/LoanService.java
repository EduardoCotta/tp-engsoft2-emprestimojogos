package br.ufmg.engsoft2.gameloan.service;

import java.util.Date;
import java.util.List;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.repository.LoanRepository;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.helper.Helper;

import static br.ufmg.engsoft2.gameloan.helper.ValidatorHelper.*;

public class LoanService {

	public static final String HIFEN_LINE = "-------------------------------------------------------%n";
	private final LoanRepository loanRepository;
	private final UserService userService;
	private final GameService gameService;
	
	public LoanService() {
		userService = new UserService();
		gameService = new GameService();
		loanRepository = new LoanRepository();
	}

	public LoanService(LoanRepository loanRepository, UserService userService, GameService gameService) {
		this.userService = userService;
		this.gameService = gameService;
		this.loanRepository = loanRepository;
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
		
		loanRepository.add(new Loan(owner, SessionManager.getSession().getLoggedUser(), requestedGame, deadline));
	}
	
	public List<Loan> listLoansByUser(User loggedUser) {
		isObjectNull(loggedUser, "Usuário logado");
		isNullOrEmpty(loggedUser.getEmail(), "Email do usuário logado");
		
		return this.loanRepository
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

    		System.out.printf(HIFEN_LINE);
    	}
	}
}
