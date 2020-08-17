package zh.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("login")
@RestController
public class LoginApi {

    @RequestMapping("/test")
    public String test(){
        System.out.println("test ok");
        return "test ok";
    }
}
