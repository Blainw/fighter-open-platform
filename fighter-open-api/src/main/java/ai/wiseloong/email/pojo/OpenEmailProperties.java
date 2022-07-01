package ai.wiseloong.email.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "spring.mail")
@Component
public class OpenEmailProperties {


    private String username;


    private String password;

    /**
     * 邮箱服务器
     */
    private String host;

    /**
     * 邮件标题
     */
    private String title;

}
