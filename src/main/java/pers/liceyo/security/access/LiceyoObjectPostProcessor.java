package pers.liceyo.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 未使用，配置出错
 * @author liceyo
 * @version 2018/6/29
 */
@Deprecated
public class LiceyoObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {

    private AccessDecisionManager accessDecisionManager;
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    public LiceyoObjectPostProcessor(AccessDecisionManager accessDecisionManager, FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        this.accessDecisionManager = accessDecisionManager;
        this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
    }

    @Override
    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
        object.setAccessDecisionManager(accessDecisionManager);
        object.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
        return object;
    }
}
