package br.ufmg.engsoft2.gameloan.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.repository.LoanRepository;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufmg.engsoft2.gameloan.helper.Helper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private GameService gameService;

    @Mock
    private UserService userService;


	private LoanService loanService;

    private final String ownerMail = "teste@email.com";

    private Game requestedGame;
	@BeforeEach
    void setUp() {
		loanService = new LoanService(loanRepository, userService, gameService);
        String sessionMail = "teste2@email.com";
        SessionManager.getSession().setLoggedUser(new User(sessionMail, "Eduardo", "", "123"));
        requestedGame = new Game("JogoTeste", "DescricaoTeste", 10, ownerMail);

    }

    @AfterAll
    static void tearDown(){
        SessionManager.clean();
    }
	
	@Test
    void shouldRegisterLoanSuccess() {
        User requester = SessionManager.getSession().getLoggedUser();
        Date deadline = Helper.parseStringToDate("01/01/2024");
        
        Mockito.when(userService.getByEmail(ownerMail)).thenReturn(new User(ownerMail, "Lucas", "", "123"));
        Mockito.when(loanRepository.getAll()).thenReturn(List.of(new Loan(new User(ownerMail, "Lucas", "", "123"), requester, requestedGame, deadline)));

        loanService.addLoan(ownerMail, requestedGame.getName(), "01/01/2024");

        Optional<Loan> createdLoan =
                loanService.listLoansByUser(requester).stream().findFirst();
									        		

        Assertions.assertTrue(createdLoan.isPresent());
        Assertions.assertEquals(ownerMail, createdLoan.get().getOwner().getEmail());
        Assertions.assertEquals(requester, createdLoan.get().getRequester());
        Assertions.assertEquals(requestedGame, createdLoan.get().getRequestedGame());
        Assertions.assertEquals(deadline, createdLoan.get().getDeadline());
    }

	@Test
    void shouldReturnErrorRegisterLoanEmptyOwner(){
        String requestedGameName = requestedGame.getName();
        IllegalArgumentException  thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> loanService
                        .addLoan
                                ("", requestedGameName, "01/01/2024"));

        Assertions.assertEquals("Email do dono do jogo não pode ser nulo ou vazio.", thrown.getMessage());
    }

	@Test
    void shouldReturnErrorRegisterLoanEmptyGame(){
        IllegalArgumentException  thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> loanService
                									.addLoan(ownerMail, "", "01/01/2024"));

        Assertions.assertEquals("Nome do jogo não pode ser nulo ou vazio.", thrown.getMessage());
    }

	@Test
    void shouldReturnErrorRegisterLoanEmptyDeadline(){
        String requestedGameName = requestedGame.getName();
        IllegalArgumentException  thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> loanService
                									.addLoan(ownerMail, requestedGameName, ""));

        Assertions.assertEquals("Data limite não pode ser nulo ou vazio.", thrown.getMessage());
    }

	@Test
    void shouldReturnErrorRegisterLoanInvalidDeadline(){
        String requestedGameName = requestedGame.getName();
        IllegalArgumentException  thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> loanService
                									.addLoan(ownerMail, requestedGameName, "01/06/2023"));

        Assertions.assertEquals("A data limite deve ser posterior à data atual.", thrown.getMessage());
    }
}
