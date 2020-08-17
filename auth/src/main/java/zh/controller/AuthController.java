package zh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zh.bean.User;
import zh.service.AuthTokenService;
import zh.bean.JsonResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AuthController
 * @Description 认证相关方法
 * @Author zhanghui
 * @Date 2020/8/14 9:54
 **/
@Component
public class AuthController {

    @Autowired
    AuthTokenService authTokenService;

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    public JsonResult getToken(HttpServletRequest request){
        String token = null;
        try{
            token = authTokenService.getToken(request);
        }catch (Exception e){
            logger.error("getToken: "+e);
        }

        if(token.startsWith("ERROR")){
            return JsonResult.errorResult(token);
        }
        return JsonResult.successResult((Object)token);
    }

    public JsonResult checkToken(User user){
        try{
            if(authTokenService.checkToken(user)){
                return JsonResult.successResult("success");
            }
        }catch (Exception e){
            logger.error("getToken: "+e);
        }
        return JsonResult.errorResult();
    }

    public JsonResult outToken(HttpServletRequest request){
        return null;
    }
}
