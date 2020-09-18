package pers.miracle.miraclecloud.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.miracle.miraclecloud.common.constant.GlobalConstant;
import pers.miracle.miraclecloud.common.interceptor.JwtInterceptor;
import pers.miracle.miraclecloud.common.interceptor.VisitLimitInterceptor;

/**
 * 注册拦截器
 *
 * @version V1.0
 * @author: 蔡奇峰
 * date: 2020/3/25 14:54
 **/
@Configuration
public class MyConfigurer implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor getInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    public VisitLimitInterceptor getVisitList() {
        return new VisitLimitInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getInterceptor())
                // 不拦截登录,注册，监控请求
                .excludePathPatterns(GlobalConstant.LOGIN_URL)
                .excludePathPatterns(GlobalConstant.REGISTER_URL)
                .excludePathPatterns(GlobalConstant.ACTUATOR_URL)
                .excludePathPatterns("/webjars/**")
                .addPathPatterns("/**");

        registry.addInterceptor(getVisitList())
                .addPathPatterns("/**");
    }
}
