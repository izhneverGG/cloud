package zh.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zh.bean.User;
import zh.controller.AuthController;
import zh.bean.JsonResult;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AuthApi
 * @Description 认证接口
 * @Author zhanghui
 * @Date 2020/8/12 17:45
 **/
@RestController
@RequestMapping("token")
@CrossOrigin("*")
public class AuthApi {

    @Autowired
    AuthController authController;

    /**
     * @decription 登录获取token
     * @param request
     * @return JsonResult
     */
    @RequestMapping("/get")
    public JsonResult getToken(HttpServletRequest request){
        return authController.getToken(request);
    }

    /**
     * @decription 验证用户token及资源权限
     * @param user
     * @return JsonResult
     */
    @RequestMapping("/check")
    public JsonResult checkToken(@RequestBody User user){
        return authController.checkToken(user);
    }

    /**
     * @decription 退出登录
     * @param request
     * @return JsonResult
     */
    @RequestMapping("/out")
    public JsonResult outToken(HttpServletRequest request){
        return authController.outToken(request);
    }
}