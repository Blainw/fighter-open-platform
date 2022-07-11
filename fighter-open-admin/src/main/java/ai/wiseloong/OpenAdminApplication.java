package ai.wiseloong;

import ai.wiseloong.fighter.core.LoggingPropertiesSetting;
import ai.wiseloong.fighter.db.pojo.QueryCondition;
import ai.wiseloong.open.app.pojo.dto.TOpenAppDto;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class OpenAdminApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(OpenAdminApplication.class);
        application.addListeners(new LoggingPropertiesSetting());
        application.run(args);
        QueryCondition<TOpenAppDto> query=new QueryCondition<>();
    }
}
