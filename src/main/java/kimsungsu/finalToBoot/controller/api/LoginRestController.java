package kimsungsu.finalToBoot.controller.api;

import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.form.LoginForm;
import kimsungsu.finalToBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class LoginRestController {
    private final UserService userService;

    @PostMapping("/login")
    public Result loginValidate(@Valid @ModelAttribute("loginForm") LoginForm form,
                                BindingResult bindingResult,
                                HttpSession session){

        /**
         * 글로벌에러로 로그인 실패 메시지 표시
         * 성공하면 세션에 유저 저장...
         */
        User user = null;
        if(form.getEmail() != null && form.getPassword() != null){
            System.out.println("form.getEmail() = " + form.getEmail());

            user = userService.loginAuthorization(form);
            if(user==null){
                bindingResult.reject("loginFail","로그인 실패! 이메일 혹은 비밀번호가 다릅니다.");
            }
        }

        if(bindingResult.hasErrors()){

            return new Result(bindingResult.getAllErrors());
        }

        session.setAttribute("user",user);

        return new Result(user);
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
