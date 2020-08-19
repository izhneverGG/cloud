package zh.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zh.bean.JsonResult;
import zh.bean.User;
import zh.config.FeignConfig;

/**
 * @ClassName AuthFeignApi
 * @Description Feign调用Auth
 * @Author zhanghui
 * @Date 2020/8/14 11:24
 **/
@FeignClient(value = "auth",configuration = FeignConfig.class)
public interface AuthFeignApi {
    @RequestMapping(value = "/token/check",method = {RequestMethod.POST})
    JsonResult checkToken(@RequestBody User user);
}
