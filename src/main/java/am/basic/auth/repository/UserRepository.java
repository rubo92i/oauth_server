package am.basic.auth.repository;

 import am.basic.auth.model.User;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User getByUsername(String username);


}
