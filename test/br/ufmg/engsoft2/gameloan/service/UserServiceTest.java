package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.exceptions.NoUserFoundException;
import br.ufmg.engsoft2.gameloan.repository.UserDB;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Optional;

class UserServiceTest {

    private UserService userService;
    private UserDB userDB;
    @BeforeEach
    void setUp() {
        userService = new UserService();
        userDB = UserDB.getInstance();
    }

    @Test
    void shouldRegisterUserSuccess() {
        String name = "TesteNome";
        String email = "emailteste@mail.com";
        String interests = "TesteInteresses";
        String password = "123";

        userService.signUp(email, name, interests, password);

        Optional<User> createdUser =
                userDB.getAll().stream()
                        .filter(user -> user.getEmail().equals(email)).findFirst();

        Assertions.assertTrue(createdUser.isPresent());
        Assertions.assertEquals(name, createdUser.get().getName());
        Assertions.assertEquals(email, createdUser.get().getEmail());
        Assertions.assertEquals(interests, createdUser.get().getInterests());
        Assertions.assertEquals(password, createdUser.get().getPassword());
    }

    @Test
    void shouldReturnErrorUserRegisterEmptyEmail(){
        InputMismatchException thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> userService.signUp("", "teste", "teste", "teste"));

        Assertions.assertEquals("E-mail incorreto ou faltante", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorUserRegisterEmptyName(){
        InputMismatchException thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> userService.signUp("teste", "", "teste", "teste"));

        Assertions.assertEquals("Nome incorreto ou faltante", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorUserRegisterEmptyPassword(){
        InputMismatchException thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> userService.signUp("teste", "teste", "teste", ""));

        Assertions.assertEquals("Senha incorreta ou faltante", thrown.getMessage());
    }

    @Test
    void shouldDoLoginSuccess(){
        String email = "teste@email.com";
        String password = "123";

        User userExpected = userDB.getAll().stream().filter(user -> user.getEmail().equals(email)).findFirst().get();

        userService.doLogin(email, password);

        User loggedUser = SessionManager.getSession().getLoggedUser();

        Assertions.assertEquals(userExpected.getName(), loggedUser.getName());
        Assertions.assertEquals(email, loggedUser.getEmail());
        Assertions.assertEquals(userExpected.getInterests(), loggedUser.getInterests());
        Assertions.assertEquals(password, loggedUser.getPassword());
    }

    @Test
    void shouldReturnNoUserExceptionDoLogin(){
        SessionManager.clean();
        String email = "teste@email.com";
        String password = "errada";

        NoUserFoundException thrown = Assertions.assertThrows(
                NoUserFoundException.class, () -> userService.doLogin(email, password));

        User loggedUser = SessionManager.getSession().getLoggedUser();

        Assertions.assertEquals("O e-mail e senha fornecidos não existem para nenhum usuário", thrown.getMessage());
        Assertions.assertNull(loggedUser);
    }

}