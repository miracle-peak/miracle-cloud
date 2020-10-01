package pers.miracle.miraclecloud.system;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author miracle
 */
@MapperScan("pers.miracle.miraclecloud.system.mapper")
@EnableEncryptableProperties
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"pers.miracle.miraclecloud.system", "pers.miracle.miraclecloud.common"})
public class MiracleSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiracleSystemApplication.class, args);
    }

}
