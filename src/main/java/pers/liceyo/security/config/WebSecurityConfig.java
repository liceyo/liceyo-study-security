package pers.liceyo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import pers.liceyo.security.access.LiceyoLoginEntryPoint;
import pers.liceyo.security.access.LiceyoPersistentLoginRepository;
import pers.liceyo.security.access.LiceyoUserDetailsServiceImpl;
import pers.liceyo.security.access.LiceyoSecurityHandlers;
import pers.liceyo.security.utils.EncryptUtil;

/**
 * 安全控制
 * @author liceyo
 * @version 2018/6/25
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LiceyoUserDetailsServiceImpl liceyoUserDetailsService;
    @Autowired
    private LiceyoPersistentLoginRepository liceyoPersistentLoginRepository;

    public final static String LOGIN_URL ="/login";
    /**
     * 不需要权限控制的连接
     */
    public final static String [] PERMIT_URLS={
            "/images/**", "/**/*.js", "/**/*.css", "/fonts/**",
            "/**/favicon.ico", "/", "/index","/login","/logout","/accessDenied"
    };


    /**
     * @see RememberMeServices
     * @see LiceyoSecurityHandlers 一些处理类，主要是为了处理Ajax请求
     * @param http http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                //permitAll 失效了，找不到原因,目前我在LiceyoMetadataSource里面写了过滤
                .antMatchers(PERMIT_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                  .formLogin()
                  .loginPage(LOGIN_URL)
                  //成功后处理
                  .successHandler(LiceyoSecurityHandlers.successHandler())
                  //失败后页面
                  .failureUrl(LOGIN_URL+"?error")
                  .permitAll()
                .and()
                  .logout()
                  .logoutUrl("/logout")
                  .logoutSuccessUrl(LOGIN_URL+"?logout")
                  .permitAll()
                .and()
                  .rememberMe()
                  //cookie有效时间
                  .tokenValiditySeconds(604800)
                  .rememberMeParameter("remember-me")
                  .rememberMeCookieName("rememberMe")
                  //rememberMe数据处理类
                  .tokenRepository(liceyoPersistentLoginRepository)
                  //要配置userDetailsService，具体看RememberMeServices源码
                  .userDetailsService(liceyoUserDetailsService)
                  //配置通过rememberMe认证成功后的处理，同登录成功处理
                  .authenticationSuccessHandler(LiceyoSecurityHandlers.successHandler())
                .and()
                  .exceptionHandling()
                  //配置权限不足（拒绝访问）的处理类
                  .accessDeniedHandler(LiceyoSecurityHandlers.accessDeniedHandler())
                  .authenticationEntryPoint(new LiceyoLoginEntryPoint(LOGIN_URL))
                .and()
                  .sessionManagement()
                  //session过期的处理类
//                  .invalidSessionUrl("/timeout")
                  .invalidSessionStrategy(LiceyoSecurityHandlers.invalidSessionStrategy())
                  //设置只能单个IP登录 未生效
                  .maximumSessions(1)
                  //session超时转向的链接
//                  .expiredUrl(LOGIN_URL)
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
