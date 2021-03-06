package kimsungsu.finalToBoot.repository;

import kimsungsu.finalToBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    User findOneById(String id);
    User findOneByEmailAndPassword(String email,String password);
    List<User> findByEmail(String email);
    List<User> findByName(String name);

}
