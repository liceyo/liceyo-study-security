package pers.liceyo.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author liceyo
 * @version 2018/6/25
 */
public interface PermissionRepository extends JpaRepository<Permission,String> {

    /**
     * 查找权限
     * @param method 方法
     * @param url 链接
     * @return 权限
     */
    @Query("select p from Permission p where p.permissionMethod=?1 and p.permissionUrl =?2")
    Permission findByMethodAndUrl(String method, String url);
}
