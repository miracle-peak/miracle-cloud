package pers.miracle.miraclecloud.system.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.miracle.miraclecloud.common.constant.GlobalConstant;
import pers.miracle.miraclecloud.common.utils.IpUtil;
import pers.miracle.miraclecloud.common.utils.R;
import pers.miracle.miraclecloud.system.entity.LoginLog;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.mapper.LoginLogMapper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午3:12
 */
@Aspect
@Component
public class RecordLog {
    private static final Logger log = LoggerFactory.getLogger(RecordLog.class);

    @Autowired
    private LoginLogMapper loginLogMapper;

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
        // boolean isSuccess = false;
        String msg = "";
        if (result instanceof R) {
            R r = (R) result;
            // isSuccess = r.isSuccess();
            msg = r.getMessage();
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        deal(joinPoint, request, msg, null);
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "saveLog()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        String msg = e.getMessage();
        deal(joinPoint, request, msg, e);
    }


    /**
     * 记录登录日志
     */
    public void saveLogin(String ip, String msg, String status, Object[] args) {
        User user = null;
        LoginLog loginLog = new LoginLog();
        if (args[0] instanceof User) {
            user = (User) args[0];
            loginLog.setUserName(user.getUserName());
        }
        // 设置时区
        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC+8"));

        loginLog.setIp(ip);
        loginLog.setLoginTime(time + "");
        loginLog.setCity(IpUtil.getCityInfo(ip));
        loginLog.setMsg(msg);
        loginLog.setStatus(status);

        loginLogMapper.insert(loginLog);
    }


    /**
     * 处理日志
     *
     * @param joinPoint
     * @param request
     * @param msg
     * @param e
     */
    public void deal(JoinPoint joinPoint, HttpServletRequest request, String msg, Throwable e) {
        // 获取参数
        Object[] args = joinPoint.getArgs();
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();

        String ip = IpUtil.getIpAddress(request);
        // 请求的是登录
        if (!StringUtils.isEmpty(method.getName()) &&
                GlobalConstant.LOGIN_METHOD.equals(method.getName())) {
            // null无异常则为成功 "0"
            String status = null == e ? "0" : "1";
            // 保存登录记录
            saveLogin(ip, msg, status, args);
        } else {

        }

    }

}
