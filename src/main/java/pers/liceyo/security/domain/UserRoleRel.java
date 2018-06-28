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
@Table(name = "sys_user_role_rel", schema = "security")
public class UserRoleRel implements Serializable{
    private static final long serialVersionUID = 1L;
    private String uId;
    private String rId;

    @Id
    @Column(name = "u_id", nullable = false, length = 64)
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Id
    @Column(name = "r_id", nullable = false, length = 64)
    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRoleRel that = (UserRoleRel) o;

        if (uId != null ? !uId.equals(that.uId) : that.uId != null) {
            return false;
        }
        if (rId != null ? !rId.equals(that.rId) : that.rId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = uId != null ? uId.hashCode() : 0;
        result = 31 * result + (rId != null ? rId.hashCode() : 0);
        return result;
    }
}
