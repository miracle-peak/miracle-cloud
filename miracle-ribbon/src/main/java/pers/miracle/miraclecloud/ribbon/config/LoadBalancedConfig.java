package pers.miracle.miraclecloud.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 负载均衡
 * nacos封装了ribbon,天生支持负载均衡
 *
 * @author: 蔡奇峰
 * @date: 2020/9/12 下午5:13
 **/
@Configuration
public class LoadBalancedConfig {


    /**
     * 实现负载均衡
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

    /**
     * 负载均衡使用的算法
     * 默认使用轮询（轮询：每个请求按时间顺序逐一分配到不同的后端服务器;）
     *
     * @return
     */
    @Bean
    public IRule myRule() {

        // 随机算法代替默认的轮询
        return new RandomRule();
    }
}
