package fact.it.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.user_service.model.User;
import fact.it.user_service.model.UserDTO;
import fact.it.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//No test data needed as we can control the entire behaviour of the repository per test
@SpringBootTest //configures the class as a class that contains tests
@AutoConfigureMockMvc //sets up the MockMvc object for us to inject
public class UserControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //uses Mockito to create a ReviewRepository mock which we can fully control as to which response it will give to which method call
    private UserRepository userRepository;

    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void given_WhenGetTop5HighScores_thenReturnJsonList() throws Exception {
        User u3 = new User(1,"Harry", "harry@test1.com",1,0);
        User u2 = new User(2,"Meghan", "meghan@test1.com",2,5);
        User u1 = new User(3,"Kate", "kate@test1.com",3,20);
List<User> userList = new ArrayList<>();
userList.add(u1);
userList.add(u2);
        userList.add(u3);
given(userRepository.findFirst5ByOrderByScoreDesc()).willReturn(userList);
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

    }


    @Test
    public void whenPostUser_thenReturnJsonGame()throws Exception {
        UserDTO user5= new UserDTO(10,"Charles","charles@king.com",5,150);

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
    public void givenUser_whenUpdateScore_thenStatusOk() throws Exception{
        User user=new User(1,"Harry", "harry@test1.com",1,0);
given(userRepository.findByUserID(1)).willReturn(user);
        UserDTO userUpdate=new UserDTO(1,"Harry","harry@test1.com",1,150);
        mockMvc.perform(put("/user")
                        .content(mapper.writeValueAsString(userUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void givenUser_whenUpdateScore_thenStatusNotFound() throws Exception{
        UserDTO userDTO=new UserDTO(10,"Harry","harry@test1.com",1,150);

        mockMvc.perform(put("/user")
                        .content(mapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    public void givenGame_whenDeleteGame_thenStatusOk()throws Exception {
        User user=new User(1,"Harry", "harry@test1.com",1,0);
        given(userRepository.findByUserID(1)).willReturn(user);

        mockMvc.perform(delete("/user/{userID}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void givenGame_whenDeleteGame_thenStatusNotFound()throws Exception {

        mockMvc.perform(delete("/user/{userID}",10)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    public void given_WhenGetUsers_thenReturnJsonList() throws Exception {
        User u3 = new User(1,"Harry", "harry@test1.com",1,0);
        User u2 = new User(2,"Meghan", "meghan@test1.com",2,5);
        User u1 = new User(3,"Kate", "kate@test1.com",3,20);
        List<User> userList = Arrays.asList(u1,u2,u3);

        given(userRepository.findAll()).willReturn(userList);
        mockMvc.perform(get("/users"))
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

    }

    @Test
    public void givenUserID_WhenGetUserID_thenReturnJsonUser() throws Exception {

        User u1 = new User(1,"Kate", "kate@test1.com",3,20);

        given(userRepository.findByUserID(1)).willReturn(u1);
        mockMvc.perform(get("/user/{userID}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID",is(1)))
                .andExpect(jsonPath("$.name",is("Kate")))
                .andExpect(jsonPath("$.email",is("kate@test1.com")))
                .andExpect(jsonPath("$.avatarID",is(3)))
                .andExpect(jsonPath("$.score",is(20)));



    }

}
