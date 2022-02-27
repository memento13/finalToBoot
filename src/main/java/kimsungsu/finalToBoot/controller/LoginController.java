package kimsungsu.finalToBoot.controller;

import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.form.LoginForm;
import kimsungsu.finalToBoot.entity.form.UserCreateForm;
import kimsungsu.finalToBoot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j //로그를 추가해야 하는데..
public class LoginController {

    private final UserService userService;

    @RequestMapping("/")
    public String home(Model model, HttpSession session){

        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session){
        if(session.getAttribute("user")!= null){
            return "redirect:/";

        }
        model.addAttribute("loginForm",new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginValidate(@Valid @ModelAttribute("loginForm") LoginForm form,
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
            return "login/loginForm";
        }

        session.setAttribute("user",user);

        return "redirect:/";
    }

    @GetMapping("/createUser")
    public String createUserForm(Model model){

        model.addAttribute("userCreateForm",new UserCreateForm());

        return "createUser/createUserForm";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("userCreateForm") UserCreateForm form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setName(form.getName());


        //혹시 빈값도 감싸야하나? 안해도됨!
        boolean validateEmail = userService.validateEmail(user);
        boolean validateName = userService.validateName(user);
        if(!validateEmail){
            bindingResult.rejectValue("email","duplicatedEmail","중복된 이메일입니다.");
        }
        if(!validateName){
            bindingResult.rejectValue("name","duplicatedName","중복된 이름입니다.");
        }

        // 실패
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "createUser/createUserForm";
        }

        // 성공
        boolean createUserResult = userService.CreateUser(user);
        return "redirect:/login";
    }


}
