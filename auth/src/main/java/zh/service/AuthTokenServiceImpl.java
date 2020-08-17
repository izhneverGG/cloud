package zh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import zh.bean.User;
import zh.bean.JsonResult;
import zh.utils.JwtUtil;
import zh.utils.RedisUtil;
import zh.utils.ToolUtil;

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
     * @decription 采取JWT的认证,一是考虑到没有授权的需求, 二是采用Auth2过于麻烦。
     *            获取token过程：
     *            1. 验证用户和密码是否正确, 正确则执行2, 否则返回
     *            2. JWT获取token
     *            3. 获得ip
     *            4. token+ip存入Redis
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
        //2. 获取token
        String token = JwtUtil.createToken(id);
        //3. 获得ip
        String ip = ToolUtil.getIpAddr(request);
        //4. 存入Redis, 默认30分钟过期
        redisUtil.setHashValue(id, token+"."+ip);
        //5. 返回token
        return token;
    }

    public boolean checkToken(User user){
        String token = user.getToken();
        String tokenRedis = redisUtil.getHashStringValue(user.getId());
        if(StringUtils.isEmpty(token)||StringUtils.isEmpty(tokenRedis)){
            return false;
        }
        if(tokenRedis.startsWith(token)){
            return true;
        }
        System.out.println("false");
        return false;
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
