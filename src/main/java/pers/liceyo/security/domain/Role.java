package pers.liceyo.security.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liceyo
 * @version 2018/6/25
 */
@Entity
@Table(name = "sys_role", schema = "security")
public class Role implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String roleName;
    private Integer level;
    private String description;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role_name", nullable = false, length = 255)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "level", nullable = true)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public static Role defaultRole(){
        Role role=new Role();
        role.setId("0");
        role.setLevel(99);
        role.setRoleName("none");
        role.setDescription("空角色");
        return role;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null) {
            return false;
        }
        if (roleName != null ? !roleName.equals(role.roleName) : role.roleName != null) {
            return false;
        }
        if (level != null ? !level.equals(role.level) : role.level != null) {
            return false;
        }
        if (description != null ? !description.equals(role.description) : role.description != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
