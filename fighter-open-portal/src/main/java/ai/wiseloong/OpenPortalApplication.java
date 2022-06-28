package ai.wiseloong;

import ai.wiseloong.fighter.core.LoggingPropertiesSetting;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class OpenPortalApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(OpenPortalApplication.class);
        application.addListeners(new LoggingPropertiesSetting());
        application.run(args);
    }
}
