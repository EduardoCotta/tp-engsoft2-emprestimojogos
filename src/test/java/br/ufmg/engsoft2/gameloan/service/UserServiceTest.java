package br.ufmg.engsoft2.gameloan.service;

import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.exceptions.NoUserFoundException;
import br.ufmg.engsoft2.gameloan.repository.UserRepository;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
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
class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void shouldRegisterUserSuccess() {
        String name = "TesteNome";
        String email = "emailteste@mail.com";
        String interests = "TesteInteresses";
        String password = "123";

        Mockito.when(userRepository.add(Mockito.any(User.class))).thenReturn(false);
        Mockito.when(userRepository.getAll()).thenReturn(List.of(new User(email, name, interests, password)));

        userService.signUp(email, name, interests, password);

        Optional<User> createdUser =
                userRepository.getAll().stream().findFirst();

        Assertions.assertTrue(createdUser.isPresent());
        Assertions.assertEquals(name, createdUser.get().getName());
        Assertions.assertEquals(email, createdUser.get().getEmail());
        Assertions.assertEquals(interests, createdUser.get().getInterests());
        Assertions.assertEquals(password, createdUser.get().getPassword());
    }

    @Test
    void shouldReturnErrorUserRegisterEmptyEmail(){
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> userService.signUp("", "teste", "teste", "teste"));

        Assertions.assertEquals("E-mail não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorUserRegisterEmptyName(){
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> userService.signUp("teste", "", "teste", "teste"));

        Assertions.assertEquals("Nome não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void shouldReturnErrorUserRegisterEmptyPassword(){
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () -> userService.signUp("teste", "teste", "teste", ""));

        Assertions.assertEquals("Senha não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void shouldDoLoginSuccess(){
        String email = "teste@email.com";
        String password = "123";

        Mockito.when(userRepository.getByEmail(email)).thenReturn(new User(email, "Teste", "", password));

        userService.doLogin(email, password);

        User loggedUser = SessionManager.getSession().getLoggedUser();

        Assertions.assertEquals("Teste", loggedUser.getName());
        Assertions.assertEquals(email, loggedUser.getEmail());
        Assertions.assertEquals("", loggedUser.getInterests());
        Assertions.assertEquals(password, loggedUser.getPassword());
    }

    @Test
    void shouldReturnNoUserExceptionDoLogin(){
        SessionManager.clean();
        String email = "teste@email.com";
        String password = "errada";

        Mockito.when(userRepository.getByEmail(email)).thenReturn(new User(email, "Teste", "", "123"));

        NoUserFoundException thrown = Assertions.assertThrows(
                NoUserFoundException.class, () -> userService.doLogin(email, password));

        User loggedUser = SessionManager.getSession().getLoggedUser();

        Assertions.assertEquals("O e-mail e senha fornecidos não existem para nenhum usuário", thrown.getMessage());
        Assertions.assertNull(loggedUser);
    }

}