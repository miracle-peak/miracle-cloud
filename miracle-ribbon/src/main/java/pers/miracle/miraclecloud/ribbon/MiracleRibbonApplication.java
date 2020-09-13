package pers.miracle.miraclecloud.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author miracle
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"pers.miracle.miraclecloud.ribbon", "pers.miracle.miraclecloud.common.exception"})
public class MiracleRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiracleRibbonApplication.class, args);
    }

}
