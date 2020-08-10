package pers.miracle.miraclecloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 蔡奇峰
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MiracleGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiracleGatewayApplication.class, args);
    }

}
