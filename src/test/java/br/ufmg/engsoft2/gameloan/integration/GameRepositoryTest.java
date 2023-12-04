package br.ufmg.engsoft2.gameloan.integration;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.repository.GameRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(DatabaseExtension.class)
class GameRepositoryTest extends BaseRepositoryIntegrationTest {

    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepository();
    }

    @Test
    void shouldAddGame() {
        Game game = new Game("Uno", "Game de Cartas", 5.0, "user1@example.com");
        boolean error = gameRepository.add(game);

        Game retrievedGame = gameRepository.getAllBySearchKey(game.getName()).get(0);

        Assertions.assertFalse(error);
        Assertions.assertEquals(game.getName(), retrievedGame.getName());
        Assertions.assertEquals(game.getDescription(), retrievedGame.getDescription());
        Assertions.assertEquals(game.getPrice(), retrievedGame.getPrice());
        Assertions.assertEquals(game.getOwnerEmail(), retrievedGame.getOwnerEmail());

        gameRepository.deleteGameById(retrievedGame.getId());
    }

    @Test
    void shouldGetAllGames() {
        List<Game> games = gameRepository.getAll();

        Assertions.assertEquals(2, games.size());
    }

    @Test
    void shouldGetAllGamesBySearchKey() {
        List<Game> games = gameRepository.getAllBySearchKey("Game A");

        Assertions.assertEquals(1, games.size());
    }

}
