package pers.liceyo.security.access;

import javafx.beans.binding.When;
import jdk.nashorn.internal.objects.annotations.Where;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import java.util.Collection;
import java.util.Iterator;

/**
 * 简单角色投票器
 * @author liceyo
 * @version 2018/6/25
 */
public class LiceyoRoleVoter implements AccessDecisionVoter<FilterInvocation> {
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return configAttribute instanceof LiceyoConfigAttribute;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> collection) {
        /*
        ACCESS_DENIED:失败票
        ACCESS_GRANTED:同意票
        ACCESS_ABSTAIN:弃权票
        */
        if (collection==null){
            return ACCESS_DENIED;
        }
        if (authentication==null){
            return ACCESS_DENIED;
        }
        for (ConfigAttribute attribute : collection) {
            String needRole = attribute.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                //如果是默认用户
                if (authentication instanceof AnonymousAuthenticationToken) {
                    //如果是未授权的用户则抛出未授权
                    throw new BadCredentialsException("未登录");
                } else {
                    return ACCESS_DENIED;
                }
            }
            boolean access = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(needRole));
            if (access) {
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_DENIED;
    }
}
