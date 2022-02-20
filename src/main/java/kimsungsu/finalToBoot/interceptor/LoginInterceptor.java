package kimsungsu.finalToBoot.interceptor;

import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        log.info("인터셉터 {}",request.getRequestURI());
        HttpSession session = request.getSession();
//        if(session.getAttribute("user")==null){
//            /**
//             * 임시 계정으로 로그인
//             */
//            User user = userRepository.findOneById("085720a2-e5ec-4727-8717-dde372506db1");
//            session.setAttribute("user",user);
//
//        }
        return true;
    }
}
