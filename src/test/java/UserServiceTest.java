import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malinaink.User;
import org.malinaink.UserNonUniqueException;
import org.malinaink.UserRepository;
import org.malinaink.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void whenUsersInRepositoryThenListOfLoginsShouldBeCorrectly() {
        when(userRepository.getAllUsers())
                .thenReturn(List.of(new User("test1", "test1"),
                        new User("test2", "test2"),
                        new User("test3", "test3")));
        assertThat(userService.getLoginsOfAllUsers()).isEqualTo(List.of("test1", "test2", "test3"));
    }

    @Test
    void whenRepositoryIsEmptyThenUsersLoginsListShouldBeNull() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        assertThat(userService.getLoginsOfAllUsers()).isEqualTo(new ArrayList<>());
    }

    @Test
    void whenRepositoryReturnNullThenUsersLoginsListShouldBeNull() {
        when(userRepository.getAllUsers()).thenReturn(null);
        assertThat(userService.getLoginsOfAllUsers()).isEqualTo(null);
    }

    @Test
    void whenNetworkExceptionIsRaisedThenServiceReturnNull() {
        when(userRepository.getAllUsers()).thenThrow(new RuntimeException());
        assertThat(userService.getLoginsOfAllUsers()).isEqualTo(null);
    }

    @Test
    void whenCorrectUserIsAddedThenAddUserIsCalledFromRepo() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        when(userRepository.addUser(any(User.class))).thenReturn(null);
        userService.createUser("user1", "user1");
        verify(userRepository).addUser(any());
    }

    @Test
    void whenInvalidUserIsPassedThenServiceThrousException() {
        assertThatThrownBy(() -> userService.createUser("", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User should be defined");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenExistingTeamIsPassedThenServiceThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("test", "test")));
        assertThatThrownBy(() -> userService.createUser("test", "test"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("User already exist");
    }
}
