package pers.liceyo.security.access;

import org.springframework.security.access.ConfigAttribute;
import pers.liceyo.security.domain.Permission;
import pers.liceyo.security.domain.Role;

import java.util.Objects;

/**
 * 自定义请求权限信息
 * @author liceyo
 * @version 2018/6/25
 */
public class LiceyoConfigAttribute implements ConfigAttribute {
    /**
     * 角色
     */
    private Role role;

    public LiceyoConfigAttribute(Role role) {
        this.role = role;
    }

    @Override
    public String getAttribute() {
        return role.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiceyoConfigAttribute)) return false;
        LiceyoConfigAttribute attribute = (LiceyoConfigAttribute) o;
        return Objects.equals(role, attribute.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(role);
    }
}
