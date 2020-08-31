package zh.service;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

/**
 * @ClassName BodyHandlerFunction
 * @Description 定义了一个BiFunction，参数为ServerHttpResponse，Publisher。返回值为Mono
 * @Author zhanghui
 * @Date 2020/8/14 9:54
 **/
public interface BodyHandlerFunction extends
        BiFunction<ServerHttpResponse, Publisher<? extends DataBuffer>, Mono<Void>> {
}
