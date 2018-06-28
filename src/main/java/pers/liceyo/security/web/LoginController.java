package pers.liceyo.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.liceyo.security.access.LiceyoRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liceyo
 * @version 2018/6/26
 */
@Controller
public class LoginController {

    /**
     * session 过期处理
     * @param request request
     * @param response response
     * @throws IOException io异常
     */
    @RequestMapping("/timeout")
    public void  timeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (LiceyoRequestMatcher.isAjaxRequest(request)){
            response.setHeader("ajaxStatus", "timeout");
            response.getWriter().close();
        } else {
            response.sendRedirect(request.getContextPath()+"/index");
        }
    }
}
