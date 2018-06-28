package pers.liceyo.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liceyo
 * @version 2018/6/25
 */
public interface UserRepository extends JpaRepository<User,String> {
    /**
     * 查找用户
     * @param userName 用户名
     * @return 用户
     */
    User findByUserName(String userName);
}
