package zh.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName LoggerAOP
 * @Description AOP日志记录
 * @Author zhanghui
 * @Date 2020/8/12 15:10
 **/
@Order(1)   //设置IOC容器bean的执行顺序，值越小优先级越高
@Aspect
@Component
public class LoggerAop {
//    private static Logger logger = LoggerFactory.getLogger(LoggerAop.class);
//
//    @Pointcut("execution(* * zh.*.*(..))")
//    public void doOperation(){
//    }
//
//    @Before("doOperation()")
//    public void before() throws Throwable{
//        Object[] objs = joinPoint.getArgs();
//        for (Object obj : objs) {
//            System.out.println("前置通知接受的参数:"+obj);
//        }
//        logger.info("s");
//    }
//
//    @AfterThrowing(pointcut = "doOperation()",argNames="args",throwing = "throwinfo")
//    public void afterThrowing(String args,Throwable throwinfo){
//        logger.error("ERROR:"+args+throwinfo);
//    }
}
