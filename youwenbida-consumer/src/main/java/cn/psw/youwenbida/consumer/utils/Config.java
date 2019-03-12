package cn.psw.youwenbida.consumer.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:    配置路径
 * @Version:        1.0
 */

@Component
@ConfigurationProperties(prefix="springbootdo")
public class Config {
    //图片路径
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}