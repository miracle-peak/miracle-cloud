package pers.miracle.miraclecloud.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.miracle.miraclecloud.common.constant.GlobalConstant;
import pers.miracle.miraclecloud.common.utils.R;

/**
 * 全局异常处理
 *
 * @author: 蔡奇峰
 * @Version V1.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class})
    public R handleHttpMessageNotReadableException(Exception e) {
        if (e instanceof BindException) {
            log.error("BindException 400---->{}", ((BindException) e).getAllErrors().get(0).getDefaultMessage());
            return new R(GlobalConstant.EXCEPTION_400, ((BindException) e).getAllErrors().get(0).getDefaultMessage());
        }
        log.error("400 -->{}", e.getMessage());
        return new R(GlobalConstant.EXCEPTION_400, e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("没有这个方法：" + e.getMessage());
        return new R(GlobalConstant.EXCEPTION_NOT_METHOD, "小盆友别乱来哦，not method");
    }

    @ExceptionHandler(RuntimeException.class)
    public R runtimeException(RuntimeException e) {
        log.error("{}: 运行时异常：{}" ,e.getClass(), e.getMessage());

        return R.error().message(e.getMessage());
    }


    /**
     * 其他异常在此捕获
     * @param e
     * @return
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public R handleException(Throwable e) {
        if (e instanceof JedisConnectionException) {
            //redis连接异常
            return new R(StatusCode.CONNECTION_ERROR, "系统服务器连接失败！redis connect");
        } else if (e instanceof JedisException) {
            //redis异常
            return new R(StatusCode.REDIS_ERROR, "系统服务器异常请通知管理员！redis error");
        }
        return new R(StatusCode.SYSTEM_ERROR, "系统错误！throwable");
    }*/


}
