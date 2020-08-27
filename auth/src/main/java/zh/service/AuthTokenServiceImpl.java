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

    @Override
    public String getToken(HttpServletRequest request){
        String id = request.getHeader("id");
        String pwd = request.getHeader("pwd");

        //1. 验证用户ID与密码
        if(!checkUserAndPwd(id,pwd)){
            return "ERROR: Invalid Account Or Password";
        }
        //2. 获得ip
        String ip = ToolUtils.getIpAddr(request);

        //3. 存入Redis, 默认30分钟过期
        redisUtil.setHashValue(id, ip);

        //4. 返回token
        return ip;
    }

    @Override
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

    @Override
    public boolean quitLogin(HttpServletRequest request){
        String id = request.getHeader("id");
        return redisUtil.cancelStringValue(id);
    }

    /**
     * 通过账号、密码验证是否登录成功，默认返回失败
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
