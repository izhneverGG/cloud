package zh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtil
 * @Description 操作redis的工具类
 * @Author zhanghui
 * @Date 2020/8/12 18:19
 **/
@Service
public class RedisUtil {

    private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate redisTemplate;

    //默认Redis存活时间
    private final static long DEFAULT_MAXALIVETIME = 30L;
    //默认Redis存活时间单位
    private  final static TimeUnit DEFAULT_UNIT = TimeUnit.MINUTES;

    public  void setHashValue(String key, String value){
        setHashValue(key,value,DEFAULT_MAXALIVETIME, DEFAULT_UNIT);
    }

    public  void setHashValue(String key, String value, long timeout, TimeUnit unit){
        try{
            redisTemplate.opsForValue().set(key,value,timeout, unit);
        }catch (Exception e){
            logger.error("setHashValue: "+"key: "+key+" , value: "+value+", "+e);
        }
    }

    public  String getHashStringValue(String key){
        Object object = getHashObjectValue(key);
        return object == null ? null : object.toString();
    }

    public  Object getHashObjectValue(String key){
        Object object = null;
        try{
            object = redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            logger.error("getHashObjectValue: "+"key: "+key+", "+e);
        }finally {
            return object;
        }
    }
}
