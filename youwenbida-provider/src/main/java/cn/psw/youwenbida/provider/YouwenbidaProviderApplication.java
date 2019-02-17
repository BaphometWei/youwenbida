package cn.psw.youwenbida.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.psw.youwenbida.provider.mapper")
public class YouwenbidaProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouwenbidaProviderApplication.class, args);
    }

}

