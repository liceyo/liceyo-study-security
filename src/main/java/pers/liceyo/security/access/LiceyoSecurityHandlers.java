package pers.liceyo.security.access;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理集合
 * @author liceyo
 * @version 2018/6/26
 */
public class LiceyoSecurityHandlers {
    /**
     * 登录失败后处理
     * @return handler
     */
    public static AuthenticationFailureHandler failureHandler(){
        return (request, response, e) -> {
            request.getRequestDispatcher("/login?error").forward(request,response);
        };
    }

    /**
     * 登录成功后处理
     * @return handler
     */
    public static AuthenticationSuccessHandler successHandler(){
        return (request, response, authentication) -> {
            Object returnUrl = request.getSession().getAttribute("returnUrl");
            request.getSession().removeAttribute("returnUrl");
            String url;
            if(returnUrl!=null&&(url=String.valueOf(returnUrl)).length()>0){
                response.sendRedirect(url);
            }else{
                response.sendRedirect(request.getContextPath()+"/index");
            }
        };
    }

    /**
     * 拒绝访问处理
     * @return
     */
    public static AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            if (LiceyoRequestMatcher.isAjaxRequest(request)) {
                response.setHeader("ajaxStatus", "accessDenied");
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                response.sendRedirect(request.getContextPath()+"/accessDenied");
            }
        };
    }

    /**
     * session过期处理
     * @return
     */
    public static InvalidSessionStrategy invalidSessionStrategy(){
        return (request, response) -> {
            if (LiceyoRequestMatcher.isAjaxRequest(request)) {
                response.setHeader("ajaxStatus", "timeout");
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                response.sendRedirect(request.getContextPath()+"/login");
            }
        };
    }
}