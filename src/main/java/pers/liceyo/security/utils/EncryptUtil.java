package pers.liceyo.security.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author liceyo
 * @version 2018/6/26
 */
public class EncryptUtil {
    /**
     * 生成权限表主键
     * @param method 访问方法
     * @param url 访问链接
     * @return 主键
     */
    public static String generatePermissionKey(String method,String url){
        return md5(method+":"+url);
    }

    /**
     * 使用sha1加密
     * @param data 原文
     * @return 密文
     */
    public static String sha1(String data){
        return DigestUtils.sha1Hex(data.getBytes());
    }

    /**
     * 使用md5加密
     * @param data 原文
     * @return 密文
     */
    public static String md5(String data){
        return DigestUtils.md5Hex(data.getBytes());
    }

    public static void main(String []args){
        System.out.println(md5("admin"));
    }
}
