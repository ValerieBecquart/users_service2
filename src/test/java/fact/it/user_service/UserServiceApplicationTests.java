package fact.it.user_service;

import fact.it.user_service.controller.UserController;
import fact.it.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private UserController userController;



    @Autowired
   private UserRepository userRepository;


    @Test
    void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
        assertThat(userRepository).isNotNull();
    }

}
