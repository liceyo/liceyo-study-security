package pers.liceyo.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 记住我token认证
 * @author liceyo
 * @version 2018/6/28
 */
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin,String>{

    /**
     * 更新
     * @param series series
     * @param tokenValue token
     * @param lastUsed 时间
     */
    @Modifying
    @Transactional
    @Query("update PersistentLogin p set p.token=?2,p.lastUsed=?3 where p.series=?1")
    void updateToken(String series, String tokenValue, Date lastUsed);

    /**
     * 删除
     * @param username 用户名
     */
    @Modifying
    @Transactional
    @Query("delete from PersistentLogin p where p.username=?1")
    void removeByUsername(String username);
}
