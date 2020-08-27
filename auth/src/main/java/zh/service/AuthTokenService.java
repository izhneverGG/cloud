package zh.service;

import org.springframework.stereotype.Service;
import zh.bean.User;
import zh.bean.JsonResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AuthService
 * @Description Auth接口方法
 * @Author zhanghui
 * @Date 2020/8/12 17:48
 **/
@Service
public interface AuthTokenService {

    /**
     *
     *  新想法：AUTH2和JWT感觉都没有必要了，因为有IP作唯一性。
     *  若Redis中以(id,ip)存储，那么检验登录为Redis中有此键值对则登录，请求ip与Redis中ip一致则同意访问。
     *  同时这样，前端也不用在请求header中放token，也解决了登录唯一性的要求。
     *  以下作废：
     *  采取JWT的认证,一是考虑到没有授权的需求, 二是采用Auth2过于麻烦。
     *  获取token过程：
     *  1. 验证用户和密码是否正确, 正确则执行2, 否则返回
     *  2. JWT获取token
     *  3. 获得ip
     *  4. token存入Redis
     *  5. 返回token
     *  下面这个是最刚开始的想法, 把授权的client信息放入数据库中, 然后取出, 相当于跳过了AUTH2的授权过程,
     *  直接来到了令牌(token)过程，那何必要用AUTH2呢？JWT就可以胜任了。
     *  根据AUTH2的简化模式，即通过client_id,client_secret,uri获得token,不需要先拿授权码。
     *  1.验证用户和密码是否正确,正确则执行2，否则返回
     *  2.通过用户ID在数据库中查询clien_id,client_secret
     *  3.通过ID,client_id,client_secret得到token
     *  4.将用户ID与token存在Redis缓存
     *  5.返回token
     * @param request
     * @return String
     */
    String getToken(HttpServletRequest request);

    /**
     * 校验token是否和Redis中一致
     * @param user
     * @return boolean
     */
    boolean checkToken(User user);

    /**
     * 登出登录，移除Redis中的键值对
     * @param request
     * @return boolean
     */
    boolean quitLogin(HttpServletRequest request);
}
