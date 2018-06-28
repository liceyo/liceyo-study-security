package pers.liceyo.security.access;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import java.util.Collection;

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
        boolean notNeedAuth="anonymousUser".equals(authentication.getPrincipal());
        if (!notNeedAuth){
            boolean access=authentication.getAuthorities().stream()
                    .anyMatch(ga-> {
                        String gaAuthority = ga.getAuthority();
                        return gaAuthority != null && collection.stream()
                                .anyMatch(configAttribute -> configAttribute.getAttribute().equals(gaAuthority));
                    });
            if (access){
                return ACCESS_GRANTED;
            }else {
                return ACCESS_DENIED;
            }
        }
        return ACCESS_DENIED;
    }
}
