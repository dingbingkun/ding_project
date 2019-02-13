package cn.ding.sys.service.impl;

import cn.ding.common.exception.DingException;
import cn.ding.common.service.RedisService;
import cn.ding.sys.service.SysCaptchaService;
import com.google.code.kaptcha.Producer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

@Service
public class SysCaptchaServiceImpl implements SysCaptchaService{

    @Resource
    private Producer producer;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RedisService redisService;

    //验证码失效时间
    private static long expireTime = 5*60;

    @Override
    public BufferedImage getCaptcha(String uuid) throws Exception{
        if(uuid==null||"".equals(uuid)){
            throw new DingException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        //将文字验证码存入redis中，并设置超时时间
        redisService.set(uuid,code,expireTime);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        String redisCode = redisService.get(uuid,expireTime);
        if(redisCode==null){
            return false;
        }
        redisService.delete(uuid);
        return redisCode.equals(code);
    }
}
