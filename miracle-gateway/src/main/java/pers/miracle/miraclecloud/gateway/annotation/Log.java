package pers.miracle.miraclecloud.gateway.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author: 蔡奇峰
 * date: 2020/5/1 14:34
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
}
