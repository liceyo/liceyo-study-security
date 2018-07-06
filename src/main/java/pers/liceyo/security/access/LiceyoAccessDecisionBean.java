package pers.liceyo.security.access;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liceyo
 * @version 2018/6/29
 */
@Configuration
public class LiceyoAccessDecisionBean {


    /**
     * UnanimousBased 决策
     * @return 决策
     */
    @Bean
    public UnanimousBased setUnanimousBased(){
        List<AccessDecisionVoter<?>> decisionVoters =new ArrayList<>();
        AccessDecisionVoter<?> voter=new LiceyoRoleVoter();
        decisionVoters.add(voter);
        return new UnanimousBased(decisionVoters);
    }

    /**
     * AffirmativeBased 决策
     * @return 决策
     */
    @Bean
    public AffirmativeBased affirmativeBased(){
        List<AccessDecisionVoter<?>> decisionVoters =new ArrayList<>();
        AccessDecisionVoter<?> voter=new LiceyoRoleVoter();
        decisionVoters.add(voter);
        return new AffirmativeBased(decisionVoters);
    }
}
