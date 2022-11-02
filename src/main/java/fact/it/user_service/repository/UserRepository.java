package fact.it.user_service.repository;

import fact.it.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
  public  User findByUserID(int userID);
    public  User findByEmail(String email);



   List<User> findAll();
List<User>findFirst5ByOrderByScoreDesc();

}
