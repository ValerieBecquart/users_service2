package fact.it.user_service.controller;

import fact.it.user_service.model.User;
import fact.it.user_service.model.UserDTO;
import fact.it.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepo;


       private Random rnd = new Random();

    public UserController(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @PostConstruct
    public void fillDatabaseTemporary(){
        if(userRepo.count()==0) {
            for (int j = 0; j < 20; j++) {
                User user = new User();
                user.setUserID(j);
                user.setName("User " + j);
                user.setAvatarID(rnd.nextInt(1,6));
                user.setEmail("user" + j + "@test.com");
                user.setScore(rnd.nextInt(50));
                userRepo.save(user);
            }
        }

    }
    @GetMapping("/users")
    public List<User>getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/scores")
    public List<User>getTop5HighScoresAsc(){
        return userRepo.findFirst5ByOrderByScoreDesc();
    }

    @GetMapping("user/{userID}")
    public User getUserById(@PathVariable int userID){
        return userRepo.findByUserID(userID);
    }

    //create
@PostMapping("/user")
public User create(@RequestBody UserDTO user){

        User peristentUser = new User(user.getUserID(), user.getName(),user.getEmail(),user.getAvatarID(),user.getScore());

        userRepo.save(peristentUser);
        return peristentUser;
}
//replace
@PutMapping("/user")
    public ResponseEntity<Void> updateScore(@RequestBody UserDTO userToUpdate)
    {
        Optional<User> user1 = Optional.ofNullable(userRepo.findByUserID(userToUpdate.getUserID()));
        if (user1.isPresent()) {
            User u = user1.get();
            u.setScore(userToUpdate.getScore());
            u.setName(userToUpdate.getName());
            u.setEmail(userToUpdate.getEmail());
            u.setAvatarID(userToUpdate.getAvatarID());
            userRepo.save(u);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/user/{userID}")
    public ResponseEntity<Void>deleteUser(@PathVariable int userID){
        Optional<User>user= Optional.ofNullable(userRepo.findByUserID(userID));
        if(user.isPresent()){
            User userToDelete = user.get();
            userRepo.delete(userToDelete);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
