package zh.bean;

import java.io.Serializable;

/**
 * @ClassName JsonResult
 * @Description 自定义的返回结果类
 * @Author zhanghui
 * @Date 2020/8/12 17:51
 **/
public class JsonResult implements Serializable {
    private final long SERIALIZABLE_ID = 1<<31L;

    private int status;
    private boolean success;
    private String message;
    private Object result;

    private JsonResult(){
        status=200;
        success=true;
        message=null;
        result=null;
    }

    public static JsonResult ErrorResult(){
        JsonResult result = new JsonResult();
        result.setStatus(500);
        result.setSuccess(false);
        return result;
    }

    public static JsonResult ErrorResult(String msg){
        JsonResult result = new JsonResult();
        result.setStatus(500);
        result.setSuccess(false);
        result.setMessage(msg);
        return result;
    }

    public static JsonResult SuccessResult(String msg){
        JsonResult result = new JsonResult();
        result.setMessage(msg);
        return result;
    }

    public static JsonResult SuccessResult(Object data){
        JsonResult result = new JsonResult();
        result.setResult(data);
        return result;
    }

    public static JsonResult SuccessResult(String msg,Object data){
        JsonResult result = new JsonResult();
        result.setMessage(msg);
        result.setResult(data);
        return result;
    }
    /*------------Getter&Setter实现------------*/
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
