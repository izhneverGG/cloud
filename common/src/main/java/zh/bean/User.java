package zh.bean;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @ClassName User
 * @Description 用户类
 * @Author zhanghui
 * @Date 2020/8/13 17:25
 **/
public class User implements Serializable {
    private final static long SERIALIZABLE_ID=1<<31L;
    private String id;
    private String pwd;
    private String token;
    private String path;
    private HttpServletRequest request;

    /*------Getter&Setter实现-----*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
