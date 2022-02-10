package kimsungsu.finalToBoot.repository;

import kimsungsu.finalToBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    User findOneById(String id);
}
