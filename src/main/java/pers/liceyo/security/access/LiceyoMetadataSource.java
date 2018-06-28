package pers.liceyo.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import pers.liceyo.security.config.WebSecurityConfig;
import pers.liceyo.security.domain.Permission;
import pers.liceyo.security.domain.PermissionRepository;
import pers.liceyo.security.utils.EncryptUtil;
import pers.liceyo.security.domain.Role;
import pers.liceyo.security.domain.RoleRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liceyo
 * @version 2018/6/25
 */
@Service
public class LiceyoMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    /**
     * 所有权限
     */
    private List<Permission> permissions;

    private void loadPermissions(){
        this.permissions=permissionRepository.findAll();
    }

    /**
     * 加载可以访问本次请求的角色
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final FilterInvocation invocation = (FilterInvocation) object;
        String requestUrl = invocation.getRequestUrl();
        //如果当前请求为permitAll时，不进行决策
        HttpServletRequest request = invocation.getRequest();
        boolean match = Arrays.stream(WebSecurityConfig.PERMIT_URLS)
                .anyMatch(url -> LiceyoRequestMatcher.matches(url, request));
        if (match){
            return null;
        }
        String method = invocation.getHttpRequest().getMethod();
        if (permissions==null){
            loadPermissions();
        }
        final List<Role> roles=new ArrayList<>();
        if (permissions!=null){
            permissions.stream()
                    .filter(permission -> method.equals(permission.getPermissionMethod()))
                    .filter(permission -> LiceyoRequestMatcher.matches(permission.getPermissionUrl(), request))
                    .map(permission -> roleRepository.findByPermissionId(permission.getId()))
                    .filter(roles1 -> roles1!=null&&!roles1.isEmpty())
                    .forEach(roles::addAll);
        }
        //设置默认角色的原因是为了所以的请求都经过决策器
        if (roles.isEmpty()){
            roles.add(Role.defaultRole());
        }
        // 能传null，但不会进决策器决策，如果传不为空的会使login无限重定向
        // 感觉有问题，antMatchers(PERMIT_URLS).permitAll()好像失效了
        //设置rejectPublicInvocations=true可以使其抛出异常
        //但是设置后登录页面等不需要验证的页面也会抛出异常
        return roles.stream().map(LiceyoConfigAttribute::new)
                .collect(Collectors.toSet());
    }

    /**
     * (程序启动时就加载到内存)
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        loadPermissions();
        return null;
    }

    /**
     * (程序启动时就加载到内存)
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
