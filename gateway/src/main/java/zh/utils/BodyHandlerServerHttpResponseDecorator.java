package zh.utils;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import zh.service.BodyHandlerFunction;

/**
 * @ClassName BodyHandlerServerHttpResponseDecorator
 * @Description 构建响应包装类
 * @Author zhanghui
 * @Date 2020/8/28 15:16
 **/
public class BodyHandlerServerHttpResponseDecorator
        extends ServerHttpResponseDecorator {

    /**
     * body 处理拦截器
     */
    private BodyHandlerFunction bodyHandler = initDefaultBodyHandler();

    private boolean isError;

    /**
     * 构造函数
     *
     * @param bodyHandler
     * @param delegate
     */
    public BodyHandlerServerHttpResponseDecorator(
            BodyHandlerFunction bodyHandler, ServerHttpResponse delegate, boolean isError) {
        super(delegate);
        this.isError = isError;
        if (bodyHandler != null) {
            this.bodyHandler = bodyHandler;
        }
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        // body拦截处理器处理响应
        ServerHttpResponse serverHttpResponse = getDelegate();
        if(isError){
            serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return bodyHandler.apply(serverHttpResponse, body);
    }

    @Override
    public Mono<Void> writeAndFlushWith(
            Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body).flatMapSequential(p -> p));
    }

    /**
     * 默认body拦截处理器
     * @return BodyHandlerFunction
     */
    private BodyHandlerFunction initDefaultBodyHandler() {
        return (resp, body) -> resp.writeWith(body);
    }
}
