package pers.liceyo.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author liceyo
 * @version 2018/6/25
 */
public interface RoleRepository extends JpaRepository<Role,String> {
    /**
     * 查找角色
     * @param userId 用户id
     * @return 角色
     */
    @Query("select r from Role r,UserRoleRel urr where r.id=urr.rId and urr.uId=?1")
    List<Role> findByUserId(String userId);

    /**
     * 查找角色
     * @param permissionId 权限id
     * @return 角色
     */
    @Query("select r from Role r,RolePermissionRel rpr where r.id=rpr.rId and rpr.pId=?1")
    List<Role> findByPermissionId(String permissionId);
}
