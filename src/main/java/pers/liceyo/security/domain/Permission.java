package pers.liceyo.security.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liceyo
 * @version 2018/6/25
 */
@Entity
@Table(name = "sys_permission", schema = "security")
public class Permission implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String permissionName;
    private String permissionUrl;
    private String permissionMethod;
    private String permissionSign;
    private String parentId;
    private Integer order;
    private String description;

    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "permission_name", nullable = false, length = 255)
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Basic
    @Column(name = "permission_url", nullable = true, length = 255)
    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    @Basic
    @Column(name = "permission_method", nullable = true, length = 255)
    public String getPermissionMethod() {
        return permissionMethod;
    }

    public void setPermissionMethod(String permissionMethod) {
        this.permissionMethod = permissionMethod;
    }
    @Basic
    @Column(name = "permission_sign", nullable = true, length = 255)
    public String getPermissionSign() {
        return permissionSign;
    }

    public void setPermissionSign(String permissionSign) {
        this.permissionSign = permissionSign;
    }

    @Basic
    @Column(name = "parent_id", nullable = true, length = 64)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "permission_order", nullable = true)
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
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

        Permission that = (Permission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (permissionName != null ? !permissionName.equals(that.permissionName) : that.permissionName != null) {
            return false;
        }
        if (permissionUrl != null ? !permissionUrl.equals(that.permissionUrl) : that.permissionUrl != null) {
            return false;
        }
        if (permissionMethod != null ? !permissionMethod.equals(that.permissionMethod) : that.permissionMethod != null) {
            return false;
        }
        if (permissionSign != null ? !permissionSign.equals(that.permissionSign) : that.permissionSign != null) {
            return false;
        }
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
            return false;
        }
        if (order != null ? !order.equals(that.order) : that.order != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (permissionName != null ? permissionName.hashCode() : 0);
        result = 31 * result + (permissionUrl != null ? permissionUrl.hashCode() : 0);
        result = 31 * result + (permissionMethod != null ? permissionMethod.hashCode() : 0);
        result = 31 * result + (permissionSign != null ? permissionSign.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
