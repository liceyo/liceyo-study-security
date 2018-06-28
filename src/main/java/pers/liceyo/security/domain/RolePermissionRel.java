package pers.liceyo.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author liceyo
 * @version 2018/6/25
 */
@Entity
@Table(name = "sys_role_permission_rel", schema = "security")
public class RolePermissionRel implements Serializable{
    private static final long serialVersionUID = 1L;
    private String rId;
    private String pId;

    @Id
    @Column(name = "r_id", nullable = false, length = 64)
    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    @Id
    @Column(name = "p_id", nullable = false, length = 64)
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RolePermissionRel that = (RolePermissionRel) o;

        if (rId != null ? !rId.equals(that.rId) : that.rId != null) {
            return false;
        }
        if (pId != null ? !pId.equals(that.pId) : that.pId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = rId != null ? rId.hashCode() : 0;
        result = 31 * result + (pId != null ? pId.hashCode() : 0);
        return result;
    }
}
