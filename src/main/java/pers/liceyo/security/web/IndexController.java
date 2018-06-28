package pers.liceyo.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
