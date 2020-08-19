package zh.Filter;

import feign.RequestTemplate;

/**
 * @ClassName FeignInterceptor
 * @Description TODO
 * @Author zhanghui
 * @Date 2020/8/17 14:03
 **/
public class FeignInterceptor {

    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Access-Control-Allow-Origin", "*")
                        .header("Content-Type","application/json");
    }
}
