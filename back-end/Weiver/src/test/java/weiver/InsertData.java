package weiver;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import weiver.entity.User;
import weiver.repository.UserRepository;

import java.util.List;

@SpringBootTest
public class InsertData {
    @Autowired
    private UserRepository userRepository;

    @Commit
    @Test
    public void insertUser(){
        User user1 = new User("123","123","123", "123", "y","y","y");
        User user2 = new User("1234","1234","1234", "1234", "y","y","y");
        User user3 = new User("12345","12345","12345", "12345", "y","y","y");
        List<User> users = List.of(user1, user2, user3);

        userRepository.saveAll(users);
    }
}
