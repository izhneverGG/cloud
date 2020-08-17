package zh.filter;

import java.util.HashSet;

/**
 * @ClassName WhiteListFilter
 * @Description 白名单设置
 * @Author zhanghui
 * @Date 2020/8/12 13:56
 **/
public class WhiteListFilter {

    private static HashSet<String> whiteResourcesList = new HashSet<String>();;
    private static HashSet<String> whiteUserList = new HashSet<String>();;
    static {
        whiteResourcesList.add("/auth/token/get");
        whiteUserList.add("zh");
    }

    private WhiteListFilter(){
    }

    /**
     * 检测访问资源是否为白名单，是返回true，否则返回false
     * @param resource
     * @return boolean
     */
    public static boolean isWhiteResources(String resource){
        if(whiteResourcesList.contains(resource))
            return true;
        else
            return false;
    }

    /**
     * 检测登录用户是否为白名单，是返回true，否则返回false
     * @param resource
     * @return
     */
    public static boolean isWhiteUser(String resource){
        if(whiteUserList.contains(resource))
            return true;
        else
            return false;
    }
}
