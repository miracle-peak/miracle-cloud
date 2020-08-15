package pers.miracle.miraclecloud.gateway.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pers.miracle.miraclecloud.common.utils.R;
import java.lang.reflect.Method;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午3:12
 */
//@Aspect
//@Component
public class RecordLog {
    private static final Logger log = LoggerFactory.getLogger(RecordLog.class);

    /**
     * 切入点,使用注解@Log的方法则保存日志
     */
    @Pointcut("@annotation(pers.miracle.miraclecloud.common.annotation.Log)")
    public void saveLog() {
    }

    /**
     * 记录操作日志
     *
     * @param joinPoint
     * @param result    切入点方法返回的对象
     */
    @AfterReturning(value = "saveLog()", returning = "result")
    public void saveLog(JoinPoint joinPoint, Object result) {
        log.info("----log----");
        boolean isSuccess = false;
        String msg = "";
        if (result instanceof R) {
            R r = (R) result;
            isSuccess = r.isSuccess();

            msg = r.getMessage();
        }

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();

        log.info("method--->" + method.getName());


    }

    /**
     * 记录登录日志
     */
    public void saveLogin() {

    }


}
