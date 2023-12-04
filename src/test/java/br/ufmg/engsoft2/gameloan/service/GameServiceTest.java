package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.repository.GameRepository;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    private GameService gameService;

    private final String sessionEmail = "teste2@email.com";
    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository);
        SessionManager.getSession().setLoggedUser(new User(sessionEmail, "Eduardo", "", "123"));
    }

    @AfterAll
    static void tearDown(){
        SessionManager.clean();
    }

    @Test
    void shouldRegisterGameSuccess() {
        String gameName = "JogoTeste";
        String description = "DescricaoTeste";
        double price = 10;

        Mockito.when(gameRepository.add(Mockito.any(Game.class))).thenReturn(false);
        Mockito.when(gameRepository.getAll()).thenReturn(List.of(new Game("JogoTeste", "DescricaoTeste", 10, sessionEmail)));

        gameService.addGame(gameName, description, price);

        Optional<Game> gameCreated =
                gameRepository.getAll().stream().findFirst();

        Assertions.assertTrue(gameCreated.isPresent());
        Assertions.assertEquals(gameName, gameCreated.get().getName());
        Assertions.assertEquals(description, gameCreated.get().getDescription());
        Assertions.assertEquals(price, gameCreated.get().getPrice());
    }

    @Test
    void shouldReturnErrorRegisterGameEmptyDescription(){
        IllegalArgumentException  thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> gameService.addGame("teste", "", 0));

        Assertions.assertEquals("Descrição não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorRegisterGameEmptyName(){
        IllegalArgumentException  thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> gameService.addGame("", "teste", 0));

        Assertions.assertEquals("Nome não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorRegisterGameNegativePrice(){
        IllegalArgumentException  thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> gameService.addGame("teste", "teste", -1));

        Assertions.assertEquals("Preço não pode ser negativo.", thrown.getMessage());
    }

}