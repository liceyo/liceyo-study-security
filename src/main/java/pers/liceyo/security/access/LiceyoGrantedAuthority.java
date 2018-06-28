package pers.liceyo.security.access;

import org.springframework.security.core.GrantedAuthority;
import pers.liceyo.security.domain.Role;

/**
 * 用户授权信息
 * @author liceyo
 * @version 2018/6/25
 */
public class LiceyoGrantedAuthority implements GrantedAuthority {
    /**
     * 用户角色
     */
    private Role role;

    public LiceyoGrantedAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getId();
    }

    public Role getRole() {
        return role;
    }
}
