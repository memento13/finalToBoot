package kimsungsu.finalToBoot.controller.api;

import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.form.LoginForm;
import kimsungsu.finalToBoot.entity.form.Message;
import kimsungsu.finalToBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class LoginRestController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Message> loginValidate(@Valid @ModelAttribute("loginForm") LoginForm form,
                                                 BindingResult bindingResult,
                                                 HttpSession session){

        /**
         * 글로벌에러로 로그인 실패 메시지 표시
         * 성공하면 세션에 유저 저장...
         */
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        User user = null;

        if(form.getEmail() != null && form.getPassword() != null){
            user = userService.loginAuthorization(form);
            if(user==null){
                bindingResult.reject("loginFail","로그인 실패! 이메일 혹은 비밀번호가 다릅니다.");
            }
        }

        if(bindingResult.hasErrors()){
            message.setMessage("로그인 실패");
            message.setData(bindingResult.getAllErrors());
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.BAD_REQUEST);
        }

        session.setAttribute("user",user);

        message.setMessage("로그인 성공");
        message.setData(user);
        message.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(message,httpHeaders, HttpStatus.OK);
    }

}
