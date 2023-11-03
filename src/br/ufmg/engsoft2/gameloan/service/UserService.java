package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.exceptions.NoUserFoundException;
import br.ufmg.engsoft2.gameloan.repository.GameDB;
import br.ufmg.engsoft2.gameloan.repository.UserDB;
import br.ufmg.engsoft2.gameloan.session.SessionManager;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import static br.ufmg.engsoft2.gameloan.helper.ValidatorHelper.isNullOrEmpty;

public class UserService {
    private final UserDB userDB;

    public UserService() {
        userDB = UserDB.getInstance();
    }

    public void signUp(String email, String name, String interests, String password) {
        isNullOrEmpty(email, "E-mail");
        isNullOrEmpty(name, "Nome");
        isNullOrEmpty(password, "Senha");

        userDB.add(new User(email, name, interests, password));
    }

    public User getByEmail(String email) {
        List<User> users = UserDB.getInstance().getAll();
        users = users.stream()
                .filter(usuario -> usuario.getEmail().equals(email))
                .toList();

        if (users.isEmpty()) {
            throw new InputMismatchException("Usuário não encontrado.");
        }

        return users.get(0);
    }

    public void checkIfUserHasGame(String name, String email) {
        List<Game> userGames = GameDB.getInstance().getAll();
        userGames = userGames.stream()
                .filter(jogo -> jogo.getName().equals(name) && jogo.getOwnerEmail().equals(email))
                .toList();

        if (userGames.isEmpty()) {
            throw new InputMismatchException("Este usuário não possui o jogo em questão.");
        }
    }

    public void doLogin(String email, String password) {
        isNullOrEmpty(email, "E-mail");
        isNullOrEmpty(password, "Senha");

        Optional<User> logged = userDB.getAll().stream().filter(usuario -> usuario.getEmail().equals(email))
                .findFirst();

        if (logged.isPresent() && logged.get().getPassword().equals(password)) {
            SessionManager.getSession().setLoggedUser(logged.get());
        } else {
            throw new NoUserFoundException("O e-mail e senha fornecidos não existem para nenhum usuário");
        }
    }
}
