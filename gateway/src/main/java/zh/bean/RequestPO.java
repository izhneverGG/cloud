package zh.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName RequestPO
 * @Description Request请求表对象
 * @Author zhanghui
 * @Date 2020/8/19 17:25
 **/
public class RequestPO implements Serializable {
    private final long SERIALIZABLE_ID = 1<<31L;

    /**
     * 请求用户
     */
    private String user;
    /*
     * 请求ip
     */
    private String ip;

    /**
     * 请求时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime time;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求响应完成时间，单位ms
     */
    private int length;

    /**
     * 请求响应状态
     */
    private int status;

    /**
     * 请求响应信息
     */
    private String error;

    /*---------------Getter&Setter实现---------------*/

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
