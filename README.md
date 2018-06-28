**Spring Security 学习**

使用Spring Boot 2.0 为框架，学习Spring Security。

已完成功能：

1. 完成基于数据库的用户登录验证。

2. 完成基于数据库的权限简单权限验证(用户-角色-权限)，使用自定义投票器实现鉴权。

3. 实现RememberMe功能。

4. 使用Redis记录Spring Session。

5. 简单完成各种情况下从Ajax请求跳转到页面的处理。

问题：
 
1. permitAll失效，未找到原因，可能是重写了拦截器或者MetadataSource的原因。

2. session过期后Ajax请求会请求到登录页面，可能是因为cookie的原因，又跳转回首页，就这样循环，等段时间才能正常访问。