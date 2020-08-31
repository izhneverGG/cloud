package zh;

import java.util.function.BiFunction;

/**
 * @ClassName test
 * @Description 一些测试
 * @Author zhanghui
 * @Date 2020/8/28 16:40
 **/
public class Test {
    public static void main(String[] args) {
        // BiFunction测试
        BiFunctionTest();
    }

    public static void BiFunctionTest(){
        /*
         * BiFunction<Integer, Integer, Integer> biFunction=(a,b) -> a+b
         * 表示定义了两个Integer的参数和一个Integer的返回值，操作是“+”
         */
        BiFunction<Integer, Integer, Integer> biFunction=(a,b) -> a+b;
        int result = biFunction.apply(5,6);
        System.out.println(result);
    }
}
