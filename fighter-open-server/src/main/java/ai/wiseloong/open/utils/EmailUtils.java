package ai.wiseloong.open.utils;

import ai.wiseloong.open.email.pojo.OpenEmailProperties;
import ai.wiseloong.extUtils.CommonUtils;
import ai.wiseloong.extend.utils.RestResultCode;
import ai.wiseloong.fighter.core.exception.FighterRuntimeException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class EmailUtils {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private OpenEmailProperties openEmailProperties;

    public void sendEmail(String email) {
        int frequency = 1;
        if (redisTemplate.hasKey(email + "_frequency")) {
            frequency = Integer.valueOf(redisTemplate.opsForValue().get(email + "_frequency").toString());
            if (frequency > 10) {
                Long time = redisTemplate.opsForValue().getOperations().getExpire(email + "_frequency");
                throw new FighterRuntimeException("请求次数过多，请" + (time > 0 ? time + "秒" : "稍后") + "后再试！", RestResultCode.CODE_400.getCode(), false);
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
            redisTemplate.expire(email + "_frequency", 1, TimeUnit.HOURS);
        } else {
            redisTemplate.opsForValue().set(email + "_frequency", frequency, 0);
        }

    }
}
