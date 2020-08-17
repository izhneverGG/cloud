package zh.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDateTime;

/**
 * @ClassName JwtUtil
 * @Description 用于jwt生成token
 * @Author zhanghui
 * @Date 2020/8/14 10:42
 **/
public class JwtUtil {

    /**
     * @description 通过id和时间生成一个token
     * @param id
     * @return String
     */
    public static String createToken(String id){
        String token = JWT.create().withAudience(id).sign(Algorithm.HMAC256(LocalDateTime.now().toString()));
        return token;
    }
}
