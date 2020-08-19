package zh.service;

import org.springframework.stereotype.Service;
import zh.bean.User;
import zh.bean.JsonResult;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AuthTokenService {
    String getToken(HttpServletRequest request);
    boolean checkToken(User user);
    boolean quitLogin(HttpServletRequest request);
}
