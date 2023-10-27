package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.repository.GameDB;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Optional;

class GameServiceTest {

    private GameDB gameDB;
    private GameService gameService;

    private String sessionEmail = "teste2@email.com";
    @BeforeEach
    void setUp() {
        gameDB = GameDB.getInstance();
        gameService = new GameService();
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

        gameService.addGame(gameName, description, price);

        Optional<Game> gameCreated =
                gameDB.getAll().stream().filter(jogo -> jogo.getOwnerEmail().equals(sessionEmail)).findFirst();

        Assertions.assertTrue(gameCreated.isPresent());
        Assertions.assertEquals(gameName, gameCreated.get().getName());
        Assertions.assertEquals(description, gameCreated.get().getDescription());
        Assertions.assertEquals(price, gameCreated.get().getPrice());
    }

    @Test
    void shouldReturnErrorRegisterGameEmptyDescription(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> gameService.addGame("teste", "", 0));

        Assertions.assertEquals("Descricao incorreta ou faltante", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorRegisterGameEmptyName(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> gameService.addGame("", "teste", 0));

        Assertions.assertEquals("Nome incorreto ou faltante", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorRegisterGameNegativePrice(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> gameService.addGame("teste", "teste", -1));

        Assertions.assertEquals("Preco incorreto, não pode ser negativo", thrown.getMessage());
    }

}