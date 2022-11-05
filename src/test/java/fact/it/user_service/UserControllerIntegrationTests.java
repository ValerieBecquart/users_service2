package fact.it.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.user_service.model.User;
import fact.it.user_service.model.UserDTO;
import fact.it.user_service.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest //the class as a class that contains tests
@AutoConfigureMockMvc // sets up theMockMvc object for us to inject
 class UserControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    //regular ReviewRepository connected to a test database
    @Autowired
    private UserRepository userRepository;

private User user1 = new User(1,"Harry", "harry@test1.com",1,0);
    private User user2 = new User(2,"Meghan", "meghan@test1.com",2,5);
    private User user3 = new User(3,"Kate", "kate@test1.com",3,20);
   // private User user4 = new User(4,"William", "William@test1.com",4,100);
   // private User userToDelete = new User(5,"Charles", "charles@test1.com",5,150);

   // Delete ALL data in the test DB
    // Insert testdata
    @BeforeEach
    public void beforeAllTests(){
        userRepository.deleteAll();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
      //  userRepository.save(user4);
       // userRepository.save(userToDelete);
    }

    @AfterEach
    public void afterAllTests(){
        userRepository.deleteAll();
    }

    //r transforms objects to Json strings
    private ObjectMapper mapper = new ObjectMapper();

    @Test
     void whenGetAllUsers_thenReturnJsonUser() throws Exception{
        mockMvc.perform(get("/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID",is(1)))
                .andExpect(jsonPath("$[0].name",is("Harry")))
                .andExpect(jsonPath("$[0].email",is("harry@test1.com")))
                .andExpect(jsonPath("$[0].avatarID",is(1)))
                .andExpect(jsonPath("$[0].score",is(0)))

                .andExpect(jsonPath("$[1].userID",is(2)))
                .andExpect(jsonPath("$[1].name",is("Meghan")))
                .andExpect(jsonPath("$[1].email",is("meghan@test1.com")))
                .andExpect(jsonPath("$[1].avatarID",is(2)))
                .andExpect(jsonPath("$[1].score",is(5)))

                .andExpect(jsonPath("$[2].userID",is(3)))
                .andExpect(jsonPath("$[2].name",is("Kate")))
                .andExpect(jsonPath("$[2].email",is("kate@test1.com")))
                .andExpect(jsonPath("$[2].avatarID",is(3)))
                .andExpect(jsonPath("$[2].score",is(20)));
    };
    @Test
     void whenGetTop5HighScores_thenReturnJsonUser() throws Exception{
        mockMvc.perform(get("/scores"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID",is(3)))
                .andExpect(jsonPath("$[0].name",is("Kate")))
                .andExpect(jsonPath("$[0].email",is("kate@test1.com")))
                .andExpect(jsonPath("$[0].avatarID",is(3)))
                .andExpect(jsonPath("$[0].score",is(20)))

                .andExpect(jsonPath("$[1].userID",is(2)))
                .andExpect(jsonPath("$[1].name",is("Meghan")))
                .andExpect(jsonPath("$[1].email",is("meghan@test1.com")))
                .andExpect(jsonPath("$[1].avatarID",is(2)))
                .andExpect(jsonPath("$[1].score",is(5)))


                   .andExpect(jsonPath("$[2].userID",is(1)))
                .andExpect(jsonPath("$[2].name",is("Harry")))
                .andExpect(jsonPath("$[2].email",is("harry@test1.com")))
                .andExpect(jsonPath("$[2].avatarID",is(1)))
                .andExpect(jsonPath("$[2].score",is(0)));

    };


    @Test
     void givenUserId_whenGetUserbyUserId_thenReturnJsonUser() throws Exception{
        mockMvc.perform(get("/user/{userID}", 3))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID",is(3)))
                .andExpect(jsonPath("$.name",is("Kate")))
                .andExpect(jsonPath("$.email",is("kate@test1.com")))
                .andExpect(jsonPath("$.avatarID",is(3)))
                .andExpect(jsonPath("$.score",is(20)));
    };
    @Test
     void whenPostUser_thenReturnJsonUser()throws Exception {
        User user5= new User(10,"Charles","charles@king.com",5,150);


        mockMvc.perform(post("/user")
                        .content(mapper.writeValueAsString(user5))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID", is(10)))
                .andExpect(jsonPath("$.name", is("Charles")))
                .andExpect(jsonPath("$.email", is("charles@king.com")))
                .andExpect(jsonPath("$.avatarID", is(5)))
                .andExpect(jsonPath("$.score", is(150)));
    }
    @Test
     void givenUser_whenUpdateScore_thenStatusOk() throws Exception{
        UserDTO userDTO=new UserDTO(1,"Harry","harry@test1.com",1,150);

        mockMvc.perform(put("/user")
                        .content(mapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
     void givenUser_whenUpdateScore_thenStatusNotFound() throws Exception{
        UserDTO userDTO=new UserDTO(10,"Harry","harry@test1.com",1,150);

        mockMvc.perform(put("/user")
                        .content(mapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
     void givenUser_whenDeleteUser_thenStatusOk()throws Exception {

        mockMvc.perform(delete("/user/{userID}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
     void givenUser_whenDeleteUser__thenStatusNotFound()throws Exception {

        mockMvc.perform(delete("/user/{userID}",10)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
