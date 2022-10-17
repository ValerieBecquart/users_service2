package fact.it.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.user_service.model.User;
import fact.it.user_service.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest //the class as a class that contains tests
@AutoConfigureMockMvc // sets up theMockMvc object for us to inject
public class UserControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    //regular ReviewRepository connected to a test database
    @Autowired
    private UserRepository userRepository;

private User user1 = new User(1,"Harry", "harry@test1.com",1,0);
    private User user2 = new User(2,"meghan", "meghan@test1.com",2,5);
    private User user3 = new User(3,"Kate", "Kate@test1.com",3,20);
    private User user4 = new User(4,"William", "William@test1.com",4,100);
    private User userToDelete = new User(5,"Charles", "charles@test1.com",5,150);

   // Delete ALL data in the test DB
    // Insert testdata
    @BeforeEach
    public void beforeAllTests(){
        userRepository.deleteAll();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(userToDelete);
    }

    @AfterEach
    public void afterAllTests(){
        userRepository.deleteAll();
    }

    //r transforms objects to Json strings
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void toBeDefined(){};

}
