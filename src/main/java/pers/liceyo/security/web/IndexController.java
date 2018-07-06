package pers.liceyo.security.web;

import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.liceyo.security.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author liceyo
 * @version 2018/6/26
 */
@Controller
public class IndexController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello";
    }

    @RequestMapping("/hello2")
    @ResponseBody
    public String hello2(){
        return "Hello2";
    }

    @RequestMapping("/hello/{id}/say")
    @ResponseBody
    public String hello(@PathVariable String id){
        return id;
    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public User user(@AuthenticationPrincipal User user, HttpServletRequest request){
        HttpSession session=request.getSession();
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = securityContext.getAuthentication();
        User principal = (User)authentication.getPrincipal();
        System.out.println(principal.toString());
        return user;
    }
}
