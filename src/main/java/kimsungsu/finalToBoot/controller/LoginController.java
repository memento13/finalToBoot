package kimsungsu.finalToBoot.controller;

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

        /**
         * validator 만들어야함....
         */
        // 실패
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "createUser/createUserForm";
        }
        // 성공
        return "";
    }


}
