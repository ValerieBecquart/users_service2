package fact.it.user_service.controller;

import fact.it.user_service.model.User;
import fact.it.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    public UserController(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @PostConstruct
    public void fillDatabaseTemporary(){
        userRepo.deleteAll();
        if(userRepo.count()==0) {
            Random rnd = new Random();
            for (int j = 0; j < 10; j++) {
                User user = new User();
                user.setUserID("u" + j);
                user.setName("User " + j);
                user.setAvatarID(j);
                user.setEmail("user" + j + "@test.com");
                user.setScore(rnd.nextInt(50));
                userRepo.save(user);
System.out.println(user.toString());
            }
        }

    }
    @GetMapping()
    public List<User>getAllUsers(){
        return userRepo.findAll();
    }
    @GetMapping("/{userID}")
    public User getUserById(@PathVariable String userID){

//        String ID = userId.toString();
        return userRepo.findByUserID(userID);
    }


}
