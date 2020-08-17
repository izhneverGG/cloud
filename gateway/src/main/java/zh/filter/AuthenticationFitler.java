package zh.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import zh.Feign.AuthFeignApi;
import zh.bean.JsonResult;
import zh.bean.User;

/**
 * @ClassName AuthenticationFitler
 * @Description 全局过滤器,校验访问资源和访问用户
 * @Author zhanghui
 * @Date 2020/8/12 14:56
 **/
@Controller
public class AuthenticationFitler implements GlobalFilter, Ordered {

    @Autowired(required = true)
    AuthFeignApi authFeignApi;
    /**
     * 拦截器，校验
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
     //   exchange.getRequest().getHeaders().set("Access-Control-Allow-Origin","*");
        String token = exchange.getRequest().getHeaders().getFirst("token");
         String id = exchange.getRequest().getHeaders().getFirst("id");
        String pwd = exchange.getRequest().getHeaders().getFirst("pwd");
        String path = exchange.getRequest().getPath().toString();

        //是否为白名单
        if(WhiteListFilter.isWhiteUser(id)||WhiteListFilter.isWhiteResources(path))
            return chain.filter(exchange);

        //token校验
        User user = new User();
        user.setToken(token);
        user.setId(id);
        user.setPwd(pwd);
        JsonResult result = authFeignApi.checkToken(user);
        if(!result.isSuccess()){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //用户-资源权限校验
        return chain.filter(exchange);
    }

    public int getOrder() {
        return -100;
    }
}
