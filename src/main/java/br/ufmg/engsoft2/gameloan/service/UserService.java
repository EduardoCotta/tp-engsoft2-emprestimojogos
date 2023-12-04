package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.exceptions.NoUserFoundException;
import br.ufmg.engsoft2.gameloan.repository.UserRepository;
import br.ufmg.engsoft2.gameloan.session.SessionManager;

import java.util.InputMismatchException;
import java.util.List;

import static br.ufmg.engsoft2.gameloan.helper.ValidatorHelper.isNullOrEmpty;

public class UserService {
    private final UserRepository userRepository;
    private final GameService gameService = new GameService();

    public UserService() {
        userRepository = new UserRepository();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String name, String interests, String password) {
        isNullOrEmpty(email, "E-mail");
        isNullOrEmpty(name, "Nome");
        isNullOrEmpty(password, "Senha");

        userRepository.add(new User(email, name, interests, password));
    }

    public User getByEmail(String email) {
        List<User> users = userRepository.getAll();
        users = users.stream()
                .filter(usuario -> usuario.getEmail().equals(email))
                .toList();

        if (users.isEmpty()) {
            throw new InputMismatchException("Usuário não encontrado.");
        }

        return users.get(0);
    }

    public void checkIfUserHasGame(String name, String email) {
        List<Game> userGames = gameService.getAll();
        userGames = userGames.stream()
                .filter(game -> game.getName().equals(name) && game.getOwnerEmail().equals(email))
                .toList();

        if (userGames.isEmpty()) {
            throw new InputMismatchException("Este usuário não possui o jogo em questão.");
        }
    }

    public void doLogin(String email, String password) {
        isNullOrEmpty(email, "E-mail");
        isNullOrEmpty(password, "Senha");

        User logged = userRepository.getByEmail(email);

        if (logged != null && logged.getPassword().equals(password)) {
            SessionManager.getSession().setLoggedUser(logged);
        } else {
            throw new NoUserFoundException("O e-mail e senha fornecidos não existem para nenhum usuário");
        }
    }
}
