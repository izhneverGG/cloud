# 写在前面的话
  在之前公司做了一年的SpringCloud开发，只认为学习到了很多，但是只理解个大概，于是想从0开发搭建一个SpringCloud的Damo，深度理解微服务，同时也会在另一个仓库上传配套的前端代码。
  这个项目会用到Nacos和Redis，请提前进行安装、配置。
# 项目介绍
  ## 框架版本
    + JDK：1.8
    + Maven：3.6.3
    + IDEA：2020.1.3
    + PC OS：Win10 64bit
    + 框架：SpringCloud-Nacos
    + 网关：GateWay
    + 认证：JWT(是否采用OAuth2待定)
    + 服务间调用：Feign
    + 数据库：Mysql(版本待定)
    + 持久层：Mybatis(版本待定)、Mybatis-Plus(版本待定)

    具体框架的版本(版本待定)在父pom.xml文件中的properties设置，子pom.xml也会有对应的版本。 
  ## 模块介绍
    模块内各模块设置如下图(图上传不了...)：
    
    + API：对外接口，直接调用Controller中的方法，不涉及任何业务或功能
    + Controller：业务功能实现类，实现业务功能，只有这里才会有try catch，一个方法可能会调用多个ServiceImpl()方法，
    + Service：功能声明接口，interface不作任何实现
    + ServiceImpl：功能接口实现类，实现Service中的方法，不涉及业务，只实现功能，也没有try catch
    + Dao：简单语句会使用注解/Mybatis-Plus实现，复杂采用xml的自定义sql
    + 其他：bean、utils工具
    
  ### Auth
    这个模块我想了很久，在V1.0的时候采用了OAUTH2，但是由于不需要授权，只需要令牌即可，所以我改采用JWT。然后用了JWT后，因为是需要  
    把token存在Redis(Redis我理解成为一个另类的session)，  相当于从JWT的无状态判定来到了有状态，感觉违反了JWT的初衷。在最后，  
    我反而觉得token随便是什么字符串都行，因为Redis会存储它。我可以用Redis判断是否登录、是否过期、是否匹配。所以在最后我把IP  
    设置token使用。
    
  ### GateWay
    目前GateWay只是有一个GlobalFilter，后面会陆续添加转发路由过滤器、熔断器等。
    
# 版本
  ## Version 1.1 (2020/8/19)
  >
   1. 增加Hystrix, Ribbon的超时配置
   2. 增加Auth模块以IP形式的Token生成
   3. 增加Auth模块的登出接口，并将接口设置为白名单
   4. 解决GateWay无法通过Feign调用Auth的问题
   5. 取消Auth模块的JWT获取的方式(代码保留)
  
  ## Version 1.0 (2020-8-17)
  >
   1.完成主体框架搭建
   2.完成GateWay模块路由配置
   3.完成Auth模块JWT的Token生成
