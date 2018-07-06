package pers.liceyo.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限决策的拦截器
 * @see LiceyoMetadataSource 用来加载该请求对应的权限
 * @see AffirmativeBased 使用UAffirmativeBased来做权限决策
 * @see LiceyoRoleVoter 使用角色投票器进行投票 目前只有一个投票器
 * @author liceyo
 * @version 2018/6/25
 */
@Service
public class LiceyoSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{

    @Autowired
    private LiceyoMetadataSource liceyoMetadataSource;

    /**
     * 指定决策器
     * @param manager 决策器
     */
    @Autowired
    public void setAccessDecisionManager(AffirmativeBased manager) {
        super.setAccessDecisionManager(manager);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        /*
        参见源码：
          调用LiceyoMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
          再调用UnanimousBased进行投票决策
         */
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * 指定LiceyoMetadataSource加载请求对应的权限信息
     * @return
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return liceyoMetadataSource;
    }
}
