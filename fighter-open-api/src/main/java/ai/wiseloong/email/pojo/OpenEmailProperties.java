package ai.wiseloong.email.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Fighter框架的配置文件使用到的属性
 *
 * @author 张晋荣 <a href="mailto:phenom_work@163.com">点此联系我</a>
 */
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
