package br.ufmg.engsoft2.gameloan.integration;

import br.ufmg.engsoft2.gameloan.domain.User;
import br.ufmg.engsoft2.gameloan.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

@ExtendWith(DatabaseExtension.class)
class UserRepositoryTest extends BaseRepositoryIntegrationTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void shouldAddUser() {
        User user = new User("test@mail.com", "Teste", "Teste", "123");
        boolean error = userRepository.add(user);

        User retrievedUser = userRepository.getByEmail(user.getEmail());

        Assertions.assertFalse(error);
        Assertions.assertEquals(user.getEmail(), retrievedUser.getEmail());
        Assertions.assertEquals(user.getName(), retrievedUser.getName());
        Assertions.assertEquals(user.getInterests(), retrievedUser.getInterests());
        Assertions.assertEquals(user.getPassword(), retrievedUser.getPassword());

        userRepository.deleteByEmail(retrievedUser.getEmail());
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = userRepository.getAll();

        Assertions.assertEquals(2, users.size());
    }

    @Test
    void shouldGetUserByEmail() {
        User user = userRepository.getByEmail("user1@example.com");

        Assertions.assertNotNull(user);
        Assertions.assertEquals("User 1", user.getName());
        Assertions.assertEquals("user1@example.com", user.getEmail());
        Assertions.assertEquals( "Gaming", user.getInterests());
    }
}
