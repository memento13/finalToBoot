package kimsungsu.finalToBoot.controller;

import kimsungsu.finalToBoot.entity.User;
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
@Slf4j
public class LoginController {

    private final UserService userService;

    @RequestMapping("/")
    public String home(Model model){

        return "home";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpSession session){
        String url = "login";
        if(session.getAttribute("user")!= null){
            url = "redirect:/";
        }
        else{
            url = "login/loginForm";
        }
        return url;
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
        return "redirect:/";
    }


}
