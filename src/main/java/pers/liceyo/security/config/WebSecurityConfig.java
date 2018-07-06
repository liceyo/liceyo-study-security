package pers.liceyo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import pers.liceyo.security.access.*;
import pers.liceyo.security.properties.WebSecurityProperties;
import pers.liceyo.security.utils.EncryptUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.List;

/**
 * 安全控制
 * @author liceyo
 * @version 2018/6/25
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LiceyoUserDetailsServiceImpl liceyoUserDetailsService;
    @Autowired
    private LiceyoPersistentLoginRepository liceyoPersistentLoginRepository;
    @Autowired
    private WebSecurityProperties webSecurityProperties;



    /**
     * @see PersistentTokenBasedRememberMeServices
     * @see RememberMeServices
     * @see LiceyoSecurityHandlers 一些处理类，主要是为了处理Ajax请求
     * @param http http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(webSecurityProperties.permitAll()).permitAll()
                .anyRequest().authenticated()
                .and()
                  .formLogin()
                  .loginPage("/login")
                  //成功后处理
                  .successHandler(LiceyoSecurityHandlers.successHandler())
                  .failureUrl("/login?error")
                  .permitAll()
                .and()
                  .logout()
                  .deleteCookies("remember-me")
                  .logoutSuccessUrl("/login?logout")
//                  .logoutSuccessHandler(LiceyoSecurityHandlers.logoutSuccessHandler())
                  .invalidateHttpSession(true)
                  .permitAll()
                .and()
                  .rememberMe()
                  .key("liceyo")
                  //cookie有效时间
                  .tokenValiditySeconds(604800)
                  .rememberMeParameter("remember-me")
                  .rememberMeCookieName("remember-me")
                  //如需设置authenticationSuccessHandler()，务必不要跳转到固定页面，使其登录后执行原有请求
                  //rememberMe数据处理类
                  .tokenRepository(liceyoPersistentLoginRepository)
                  //要配置userDetailsService，具体看RememberMeServices源码
                  .userDetailsService(liceyoUserDetailsService)
                .and()
                  .exceptionHandling()
                  //配置权限不足（拒绝访问）的处理类
                  .accessDeniedHandler(LiceyoSecurityHandlers.accessDeniedHandler())
                  //登录请求前处理
                  .authenticationEntryPoint(new LiceyoLoginEntryPoint("/login"))
                .and()
                  .sessionManagement()
                  //session过期的处理类
                  .invalidSessionStrategy(LiceyoSecurityHandlers.invalidSessionStrategy())
                  //设置只能单个IP登录 未生效
                  .maximumSessions(1)
                  //设置这个后，注销后无法登录，怀疑logout没有销毁session
//                  .maxSessionsPreventsLogin(true)
                  .sessionRegistry(getSessionRegistry());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置userDetailsService和密码加密
        auth.userDetailsService(liceyoUserDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return EncryptUtil.md5((String) rawPassword);
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(EncryptUtil.md5(String.valueOf(rawPassword)));
            }
        });
    }

    @Bean
    public SessionRegistry getSessionRegistry(){
        return new SessionRegistryImpl();
    }
}
