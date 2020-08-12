package pers.miracle.miraclecloud.common.annotation;

import org.springframework.context.annotation.Import;
import pers.miracle.miraclecloud.common.exception.GlobalExceptionHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在启动类上使用则表示引入全局异常处理 即引入自定义的GlobalExceptionHandler类
 *
 * @author: 蔡奇峰
 * @date: 2020/8/12 下午1:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GlobalExceptionHandler.class)    //表示这个注解使用上面的类
public @interface EnableGlobalExceptionHandler {
}
