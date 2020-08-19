package zh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import zh.bean.User;
import zh.utils.RedisUtil;
import zh.utils.ToolUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AuthServiceImpl
 * @Description Auth接口方法实现
 * @Author zhanghui
 * @Date 2020/8/12 17:48
 **/
@Component
public class AuthTokenServiceImpl implements AuthTokenService{

    @Autowired
    RedisUtil redisUtil;

    /**
     * @decription
     *            新想法：AUTH2和JWT感觉都没有必要了，因为有IP作唯一性。
     *            若Redis中以(id,ip)存储，那么检验登录为Redis中有此键值对则登录，请求ip与Redis中ip一致则同意访问。
     *            同时这样，前端也不用在请求header中放token，也解决了登录唯一性的要求。
     *
     *            以下作废：
     *            采取JWT的认证,一是考虑到没有授权的需求, 二是采用Auth2过于麻烦。
     *            获取token过程：
     *            1. 验证用户和密码是否正确, 正确则执行2, 否则返回
     *            2. JWT获取token
     *            3. 获得ip
     *            4. token存入Redis
     *            5. 返回token
     *            下面这个是最刚开始的想法, 把授权的client信息放入数据库中, 然后取出, 相当于跳过了AUTH2的授权过程,
     *            直接来到了令牌(token)过程，那何必要用AUTH2呢？JWT就可以胜任了。
     *            根据AUTH2的简化模式，即通过client_id,client_secret,uri获得token,不需要先拿授权码。
     *            1.验证用户和密码是否正确,正确则执行2，否则返回
     *            2.通过用户ID在数据库中查询clien_id,client_secret
     *            3.通过ID,client_id,client_secret得到token
     *            4.将用户ID与token存在Redis缓存
     *            5.返回token
     * @param request
     * @return String
     */
    public String getToken(HttpServletRequest request){
        String id = request.getHeader("id");
        String pwd = request.getHeader("pwd");

        //1. 验证用户ID与密码
        if(!checkUserAndPwd(id,pwd)){
            return "ERROR: Invalid Account Or Password";
        }
        //2. 获得ip
        String ip = ToolUtils.getIpAddr(request);
//        //3. 获取token
//        String token = JwtUtils.createToken(id,ip);

        //4. 存入Redis, 默认30分钟过期
        redisUtil.setHashValue(id, ip);
        //5. 返回token
        return ip;
    }

    /**
     * @description 校验token是否和Redis中一致
     * @param user
     * @return boolean
     */
    public boolean checkToken(User user){
        String token = user.getToken();
        String tokenRedis = redisUtil.getHashStringValue(user.getId());
        if(StringUtils.isEmpty(token)||StringUtils.isEmpty(tokenRedis)){
            return false;
        }
        if(tokenRedis.equals(token)){
            return true;
        }
        return false;
    }

    /**
     * @description 登出登录，移除Redis中的键值对
     * @param request
     * @return boolean
     */
    public boolean quitLogin(HttpServletRequest request){
        String id = request.getHeader("id");
        return redisUtil.cancelStringValue(id);
    }

    /**
     * @decription 通过账号、密码验证是否登录成功，默认返回失败
     * @param user
     * @param pwd
     * @return boolean
     */
    private boolean checkUserAndPwd(String user,String pwd){
        //从数据库中读取密码判断,这里简单做个判断
        if(user!=null&&pwd!=null){
            return true;
        }
        return false;
    }
}
