package zh.filter;

import feign.RequestTemplate;

/**
 * @ClassName FeignInterceptor
 * @Description Feign调用增加header属性
 * @Author zhanghui
 * @Date 2020/8/17 14:03
 **/
public class FeignInterceptor {

    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Access-Control-Allow-Origin", "*")
                        .header("Content-Type","application/json");
    }
}
