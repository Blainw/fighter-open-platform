package ai.wiseloong.open.utils;

import ai.wiseloong.email.pojo.OpenEmailProperties;
import ai.wiseloong.extUtils.CommonUtils;
import ai.wiseloong.extend.utils.RestResultCode;
import ai.wiseloong.fighter.core.exception.FighterRuntimeException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class EmailUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OpenEmailProperties openEmailProperties;

    public void sendEmail(String email) {
        int frequency = 1;
        if (redisTemplate.hasKey(email)) {
            frequency = Integer.valueOf(redisTemplate.opsForValue().get(email + "_frequency").toString());
            if (frequency > 10) {
                throw new FighterRuntimeException("请求次数过多，请稍后再试！", RestResultCode.CODE_400.getCode(), false);
            }
            frequency++;
        }

        String random = CommonUtils.createRandom(false, 6);

        try {
            HtmlEmail entity = new HtmlEmail();
            entity.setHostName(openEmailProperties.getHost());
            entity.setCharset("utf-8");
            entity.addTo(email);
            entity.setFrom(openEmailProperties.getUsername(), openEmailProperties.getTitle());
            entity.setAuthentication(openEmailProperties.getUsername(), openEmailProperties.getPassword());
            entity.setSubject("注册验证");
            entity.setMsg("【" + openEmailProperties.getTitle() + "】您的登录验证码为：" + random + ",有效期10分钟，如非本人操作请忽略。");
            entity.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

        redisTemplate.opsForValue().set(email, random, 10, TimeUnit.MINUTES);

        if (frequency == 1) {
            redisTemplate.opsForValue().set(email + "_frequency", frequency);
            redisTemplate.expireAt(email + "_frequency", new Date(Instant.now().toEpochMilli() + 3600 * 1000));
        } else {
            redisTemplate.opsForValue().set(email + "_frequency", frequency, 0);
        }

    }
}
