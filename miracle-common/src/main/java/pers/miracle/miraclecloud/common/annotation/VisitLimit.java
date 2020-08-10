package pers.miracle.miraclecloud.common.annotation;

import java.lang.annotation.*;

/**
 * 设置访问限制注解
 *
 * @author: 蔡奇峰
 * @Version V1.0
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VisitLimit {
    // 访问次数限制
    int limit();

    // 时间范围内 单位秒
    int rangeTime();

    // 限制指定时间内不能访问 单位秒
    int limitTime();
}
