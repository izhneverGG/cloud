package zh.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JwtUtil(此类已作废)
 * @Description 用于jwt生成token
 * @Author zhanghui
 * @Date 2020/8/14 10:42
 **/
@Deprecated
public class JwtUtils {

    private static final String SECRET_KEY="eyJ0eXAiOiJKV1QiLCJhbGciO45fdPdNiJ9L0mko0FM";

    /**
     * @description 通过id和ip生成一个token
     * @param id
     * @param ip
     * @return String
     */
    public static String createToken(String id, String ip){
//        //添加构成JWT的参数
//        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
//                .setSubject(id)                 // 代表这个JWT的主体，即它的所有人
//                .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
//                .setNotBefore(new Date())       // 开始生效的时间
//                .signWith(SignatureAlgorithm.HS256, ip);    //加密方式
//        return builder.compact();
        Map map = new HashMap();
        map.put("typ","JWT");
        String token = JWT.create().withHeader(map)
                        .withClaim("id",id)
                        .withSubject(ip)          // 代表这个JWT的主体，即它的所有人
                        .withIssuedAt(new Date()) // 是一个时间戳，代表这个JWT的签发时间
                        .sign(Algorithm.HMAC256(SECRET_KEY));   //SECRET_KEY
        return token;
    }

    public static String getIpInToken(String token){
        String ip = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
        System.out.println("----------------------"+ip);
        return ip;
    }
}
