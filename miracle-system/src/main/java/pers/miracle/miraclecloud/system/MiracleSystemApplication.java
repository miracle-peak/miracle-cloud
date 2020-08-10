package pers.miracle.miraclecloud.system;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author miracle
 */
@MapperScan("pers.miracle.miraclecloud.system.mapper")
@EnableEncryptableProperties
@SpringBootApplication
public class MiracleSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiracleSystemApplication.class, args);
    }

}
