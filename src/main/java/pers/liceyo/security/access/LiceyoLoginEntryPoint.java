package pers.liceyo.security.access;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录前页面请求
 * @author liceyo
 * @version 2018/6/26
 */
public class LiceyoLoginEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public LiceyoLoginEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * 登录前的判断
     * Ajax的需要结合页面Ajax配置处理
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (LiceyoRequestMatcher.isAjaxRequest(request)) {
            response.setHeader("ajaxStatus", "login");
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            //将登录前的url记录下来
            String returnUrl = buildHttpReturnUrlForRequest(request);
            request.getSession().setAttribute("returnUrl",returnUrl);
            //执行commence
            super.commence(request, response, authException);
        }
    }

    /**
     * 构建url
     * @param request 请求
     * @return 构建的url
     */
    private String buildHttpReturnUrlForRequest(HttpServletRequest request) {
        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
        urlBuilder.setScheme(request.getScheme());
        urlBuilder.setServerName(request.getServerName());
        urlBuilder.setPort(request.getServerPort());
        urlBuilder.setContextPath(request.getContextPath());
        urlBuilder.setServletPath(request.getServletPath());
        urlBuilder.setPathInfo(request.getPathInfo());
        urlBuilder.setQuery(request.getQueryString());
        return urlBuilder.getUrl();
    }

}
