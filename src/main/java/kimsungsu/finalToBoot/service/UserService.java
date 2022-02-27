package kimsungsu.finalToBoot.service;

import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.form.LoginForm;
import kimsungsu.finalToBoot.entity.form.UserCreateForm;
import kimsungsu.finalToBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입시 입력값을 검증하고 db에 저장하는 로직
     * @param user : 들어오는 유저 입력값
     * @return : 가입 성공시 true, 실패시 false 반환
     */
    public boolean CreateUser(User user){

        boolean result = false;

        boolean validateResult = validateUser(user);

        if(validateResult){
            user.setId(UUID.randomUUID().toString());
            userRepository.save(user);
            result = true;
        }

        return result;
    }

    /**
     * 동일한 email의 유저가 존재하는지 검증함
     * @param user
     * @return 동일한 email && name이 없는경우 true, 있으면 false 반환
     */
    public boolean validateUser(User user){

        boolean result = false;

        List<User> userListByEmail = userRepository.findByEmail(user.getEmail());
        List<User> userListByName = userRepository.findByName(user.getName());
        if(userListByEmail.isEmpty() && userListByName.isEmpty()){
            result = true;
        }
        return result;
    }

    /**
     * 동일한 email의 유저가 있는지 검증
     * @param user
     * @return 동일한 email이 없는경우 true 있으면 false 반환
     */
    public boolean validateEmail(User user){
        boolean result = false;

        List<User> userListByEmail = userRepository.findByEmail(user.getEmail());
        if(userListByEmail.isEmpty()){
            result = true;
        }
        return result;
    }

    /**
     * 동일한 name의 유저가 있는지 검증
     * @param user
     * @return 동일한 name의 없는경우 true 있으면 false 반환
     */
    public boolean validateName(User user){
        boolean result = false;

        List<User> userListByName = userRepository.findByName(user.getName());
        if(userListByName.isEmpty()){
            result = true;
        }
        return result;
    }

    /**
     * loginForm 에 맞는 user 객체 반환
     * @param form
     * @return 있으면 user객체 반환 없으면 null
     */
    public User loginAuthorization(LoginForm form){
        User result = userRepository.findOneByEmailAndPassword(form.getEmail(), form.getPassword());
        return result;
    }


}
