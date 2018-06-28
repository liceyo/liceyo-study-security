package pers.liceyo.security.access;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * request matcher
 * @author liceyo
 * @version 2018/6/26
 */
public class LiceyoRequestMatcher {
    /**
     * 判断请求是否是Ajax请求
     * @param request 请求
     * @return 是否是Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) ||
                request.getHeader("Accept") != null &&
                        request.getHeader("Accept").contains("application/json");
    }

    /**
     * 判断连接匹配当前请求
     * @param url url
     * @param request 请求
     * @return 是否匹配
     */
    public static boolean matches(String url, HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        return matcher.matches(request);
    }
}
