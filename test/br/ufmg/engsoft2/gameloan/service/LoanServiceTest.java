package br.ufmg.engsoft2.gameloan.service;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Optional;

import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.repository.GameDB;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.repository.LoanDB;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufmg.engsoft2.gameloan.helper.Helper;

class LoanServiceTest {
	
	private LoanDB loanDB;

    private GameDB gameDB;
	private LoanService loanService;
	private String sessionMail = "teste2@email.com";
    private String ownerMail = "teste@email.com";

    private Game requestedGame;
	@BeforeEach
    void setUp() {
        gameDB = GameDB.getInstance();
		loanDB = LoanDB.getInstance();
		loanService = new LoanService();
        SessionManager.getSession().setLoggedUser(new User(sessionMail, "Eduardo", "", "123"));
        requestedGame = gameDB.getAll().stream().filter(jogo -> jogo.getOwnerEmail().equals(ownerMail)).findFirst().get();
    }

    @AfterAll
    static void tearDown(){
        SessionManager.clean();
    }
	
	@Test
    void shouldRegisterLoanSuccess() {
        User requester = SessionManager.getSession().getLoggedUser();
        Date deadline = Helper.parseStringToDate("01/01/2024");

        loanService.addLoan(ownerMail, requestedGame.getName(), "01/01/2024");

        Optional<Loan> createdLoan = loanDB
        											.getAll()
        											.stream()
        											.filter(loan -> loan
        																  .getOwner()
        																  .getEmail()
        																  .equals(ownerMail))
        																  .findFirst();
									        		

        Assertions.assertTrue(createdLoan.isPresent());
        Assertions.assertEquals(ownerMail, createdLoan.get().getOwner().getEmail());
        Assertions.assertEquals(requester, createdLoan.get().getRequester());
        Assertions.assertEquals(requestedGame, createdLoan.get().getRequestedGame());
        Assertions.assertEquals(deadline, createdLoan.get().getDeadline());
    }

	@Test
    void shouldReturnErrorRegisterLoanEmptyOwner(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class,
                () -> loanService
                        .addLoan
                                ("", requestedGame.getName(), "01/01/2024"));

        Assertions.assertEquals("Usuário dono do jogo não encontrado.", thrown.getMessage());
    }

	@Test
    void shouldReturnErrorRegisterLoanEmptyGame(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> loanService
                									.addLoan(ownerMail, "", "01/01/2024"));

        Assertions.assertEquals("Nenhum jogo foi selecionado.", thrown.getMessage());
    }

	@Test
    void shouldReturnErrorRegisterLoanEmptyDeadline(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> loanService
                									.addLoan(ownerMail, requestedGame.getName(), ""));

        Assertions.assertEquals("A data limite não pode ser vazia", thrown.getMessage());
    }

	@Test
    void shouldReturnErrorRegisterLoanInvalidDeadline(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> loanService
                									.addLoan(ownerMail, requestedGame.getName(), "01/06/2023"));

        Assertions.assertEquals("A data limite deve ser posterior à data atual.", thrown.getMessage());
    }
}
