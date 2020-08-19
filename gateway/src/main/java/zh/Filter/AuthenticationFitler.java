package zh.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
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
     * @description 全局拦截器，校验。
     *        要点：
     *          1. chain.filter，表示当前过滤器已通过，交给下个过滤器
     *          2.
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
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
        JsonResult result = authFeignApi.checkToken(user);
        if(!result.isSuccess()){
            return Mono.defer(()->{
                Exception ex = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
                return Mono.error(ex);
            });
        }
        //用户-资源权限校验
        /*
        * TODO
        * */
        return chain.filter(exchange);
    }

    public int getOrder() {
        return -100;
    }
}
