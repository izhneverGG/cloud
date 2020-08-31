package zh.filter;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import zh.bean.RequestPO;

import java.net.URI;
import java.time.LocalDateTime;

/**
 * @ClassName RequestLogFilter
 * @Description 全局过滤器，记录请求日志
 * @Author zhanghui
 * @Date 2020/8/19 17:11
 **/
@Controller
public class RequestLogFilter implements GlobalFilter, Ordered {


    /**
     *  1. 给每个请求加上Access-Control-Allow-Origin:*
     *  2. 记录每个请求信息
     * @param exchange
     * @param chain
     * @return Mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){

        ServerHttpRequest newRequest = exchange.getRequest().mutate()
                                        .header("Access-Control-Allow-Origin","*").build();
        // 获取登录信息
        RequestPO requestPO = new RequestPO();
        requestPO.setUser(newRequest.getHeaders().getFirst("id"));
        requestPO.setPath(newRequest.getPath().pathWithinApplication().value());
        requestPO.setTime(LocalDateTime.now());
        requestPO.setIp(newRequest.getRemoteAddress().toString());

        /*
         * TODO 记录每次请求信息，存入Mysql数据库
         */
        return chain.filter(exchange.mutate().request(newRequest).response(exchange.getResponse()).build());
    }

    /**
     * 设置优先级，order越小，优先级越高
     * @return int
     */
    @Override
    public int getOrder(){
        return -100;
    }
}
