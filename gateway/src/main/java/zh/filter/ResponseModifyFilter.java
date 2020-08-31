package zh.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.synchronoss.cloud.nio.multipart.util.IOUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import zh.service.BodyHandlerFunction;
import zh.utils.BodyHandlerServerHttpResponseDecorator;

import java.io.IOException;

/**
 * @ClassName ResponseModifyFilter
 * @Description 请求完成后拦截器
 * @Author zhanghui
 * @Date 2020/8/28 15:04
 **/
@Component
public class ResponseModifyFilter implements GlobalFilter, Ordered {

    private final static String NOT_FOUND_STATUS = "\"status\":404";
    private final static String ERROR_STATUS = "\"status\":500";
    private boolean isError = false;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        /*
         * 构建响应拦截处理器
         * 解析：
         *  1. Flux.from(body)：通过body得到一个Flux对象
         *  2.
         */
        BodyHandlerFunction bodyHandler = (resp, body) -> Flux.from(body)
                .map(dataBuffer -> {
                    /*
                     * 将响应信息转换为字符串
                     */
                    String respBody = null;
                    try {
                        // dataBuffer转换为String
                        respBody = IOUtils
                                .inputStreamAsString(dataBuffer.asInputStream(), "UTF-8")
                                .replaceAll(">\\s{1,}<", "><");
                    } catch (IOException e) {
                        // 不作处理
                    }
                    return respBody;
                })
                .flatMap(respBody -> {
                    /*
                     * 根据原有的响应信息构建新响应信息并写入到resp中
                     * 此处可以根据业务进行组装新的响应信息，
                     * 例如：
                     * 在Controller层中，我们经常会用到try、catch来处理异常，但是由于异常被try、catch了，
                     * 这样即使业务代码发生了错误，HTTP状态码仍然会返回200，而不会是500。
                     * 所以我们需要手动修改HTTP状态码，并将错误保存到数据库中
                     */
                    String respBodyStr = respBody + "";
                    if(respBodyStr.contains(ERROR_STATUS)){
                        isError=true;
                        /*
                        * TODO 将错误记录到db
                        * */
//                        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//                        HttpHeaders headers = serverHttpRequest.getHeaders();
//                        headers.getFirst("id");
                    }
                    return resp.writeWith(Flux.just(respBodyStr)
                            .map(bx -> resp.bufferFactory()
                                    .wrap(bx.getBytes())));
                }).then();

        // 构建响应包装类，并修改状态码
        BodyHandlerServerHttpResponseDecorator responseDecorator = new BodyHandlerServerHttpResponseDecorator(
                bodyHandler, exchange.getResponse(), isError);
        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }


    @Override
    public int getOrder() {
        // WRITE_RESPONSE_FILTER之前执行
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }
}
