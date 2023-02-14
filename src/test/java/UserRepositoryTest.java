import org.junit.jupiter.api.*;
import org.malinaink.User;
import org.malinaink.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepositoryTest {
    private final UserRepository out = new UserRepository();
    User user1 = new User("Test", "test");
    User user2 = new User("Login", "password");
    User user3 = new User("User3", "qwerty");
    List<User> users = List.of(user1, user2, user3);

    @Test
    @DisplayName("when userlist is empty, then method getAllUsers return empty list")
    void checkReturnEmptyList() {
        Assertions.assertTrue(out.getAllUsers() == null || out.getAllUsers().isEmpty());
    }

    @Test
    @DisplayName("when userlist contains users, then method getAllUsers return correctly list")
    void checkCorrectlyReturnListOfUsers() {
        out.addUser(user1);
        out.addUser(user2);
        out.addUser(user3);
        Assertions.assertEquals(3, out.getAllUsers().size());
        Assertions.assertTrue(out.getAllUsers().containsAll(users));
    }

    @Test
    @DisplayName("when userlist contains login of user, then method FindByLogin return user")
    void checkFindByLoginIfContainsUser() {
        out.addUser(user1);
        out.addUser(user2);
        out.addUser(user3);
        Assertions.assertEquals(user1, out.findByLogin("Test").orElse(null));
        Assertions.assertNotNull(out.findByLogin("Test"));
    }

    @Test
    @DisplayName("when userlist not contains login, then method FindByLogin return null")
    void checkFindByLoginIfNotContainsUser() {
        out.addUser(user1);
        out.addUser(user2);
        out.addUser(user3);
        Assertions.assertEquals(Optional.empty(), out.findByLogin("notContains"));
    }

    @Test
    @DisplayName("when userlist contains login and password, then method FindByLogin return this user")
    void checkFindByLoginAndPasswordIfContainsUser() {
        out.addUser(user1);
        out.addUser(user2);
        out.addUser(user3);
        Assertions.assertEquals(user1, out.findByLoginAndPassword("Test", "test").orElse(null));
        Assertions.assertNotNull(out.findByLoginAndPassword("Test", "test"));
    }

    @Test
    @DisplayName("when userlist contains only password, then method FindByLogin return null")
    void checkFindByLoginAndPasswordIfOnlyPasswordContains() {
        out.addUser(user1);
        out.addUser(user2);
        out.addUser(user3);
        Assertions.assertEquals(Optional.empty(), out.findByLoginAndPassword("notContains", "test"));
    }

    @Test
    @DisplayName("when userlist contains only login, then method FindByLogin return null")
    void checkFindByLoginAndPasswordIfOnlyLoginContains() {
        out.addUser(user1);
        out.addUser(user2);
        out.addUser(user3);
        Assertions.assertEquals(Optional.empty(), out.findByLoginAndPassword("Test", "notContains"));
    }
}


