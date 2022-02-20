package kimsungsu.finalToBoot.repository;

import kimsungsu.finalToBoot.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {


    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저동일확인() throws Exception{
        User user = new User();
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setEmail("test@test.com");
        user.setPassword("1q2w3e4r5t6y!");
        user.setName("KIM");
        userRepository.save(user);
        User findUser = userRepository.findOneById(uuid);

        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());
    }



}