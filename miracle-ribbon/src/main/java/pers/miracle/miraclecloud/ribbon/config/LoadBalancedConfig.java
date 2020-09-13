package pers.miracle.miraclecloud.ribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: 蔡奇峰
 * @date: 2020/9/12 下午5:13
 **/
@Configuration
public class LoadBalancedConfig {


    /**
     * 实现负载均衡, 默认使用轮询算法
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
