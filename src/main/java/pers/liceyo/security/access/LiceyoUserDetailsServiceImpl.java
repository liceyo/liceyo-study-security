package pers.liceyo.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.liceyo.security.domain.Role;
import pers.liceyo.security.domain.RoleRepository;
import pers.liceyo.security.domain.User;
import pers.liceyo.security.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 重写UserDetailsService，实现与数据库对接
 * @see UserDetailsService
 * @author liceyo
 * @version 2018/6/25
 */
@Service
public class LiceyoUserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * 从数据库加载用户信息（根据用户名），并加载用户角色信息
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //加载用户信息
        User user = userRepository.findByUserName(username);
        if (user!=null){
            //加载用户拥有的权限信息（角色）
            List<Role> roles = roleRepository.findByUserId(user.getId());
            List<LiceyoGrantedAuthority> authorities = roles.stream()
                    .map(LiceyoGrantedAuthority::new)
                    .collect(Collectors.toList());
            user.setAuthorities(authorities);
            return user;
        }else {
            throw new UsernameNotFoundException("admin:"+username+" 用户不存在");
        }
    }
}
