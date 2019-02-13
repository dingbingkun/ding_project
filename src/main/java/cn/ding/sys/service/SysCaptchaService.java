package cn.ding.sys.service;

import java.awt.image.BufferedImage;

public interface SysCaptchaService {

    /**
     * 获取图片验证码
     * @param uuid
     * @return
     * @throws Exception
     */
    BufferedImage getCaptcha(String uuid) throws Exception;

    /**
     * 校验图片验证码
     * @param uuid
     * @param code
     * @return
     */
    boolean validate(String uuid,String code);
}
