package cn.ding.sys.controller;

import cn.ding.common.utils.R;
import cn.ding.sys.entity.SysUserEntity;
import cn.ding.sys.form.SysLoginForm;
import cn.ding.sys.service.SysCaptchaService;
import cn.ding.sys.service.SysUserService;
import cn.ding.sys.service.SysUserTokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@RestController
public class SysLoginController {
    @Value("${base.enable-captcha}")
    private boolean enableCaptcha;

    @Resource
    private SysCaptchaService sysCaptchaService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserTokenService sysUserTokenService;

    /**
     * 获取验证码图片
     * @param response
     * @param uuid
     * @throws Exception
     */
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response,String uuid) throws Exception{
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     * @param sysLoginForm 登录表单
     * @return 成功返回token
     */
    @PostMapping("/login")
    public R login(SysLoginForm sysLoginForm){
        boolean flag = sysCaptchaService.validate(sysLoginForm.getUuid(),sysLoginForm.getCaptcha());
        if(!flag&&enableCaptcha){
            return R.error("验证码不正确");
        }
        SysUserEntity sysUserEntity = sysUserService.findByUsername(sysLoginForm.getUsername());
        flag = sysUserEntity!=null&&sysUserEntity.getPassword().equals(DigestUtils.sha256Hex(sysLoginForm.getPassword()));
        if(!flag){
            return R.error("用户名或密码不正确");
        }
        return sysUserTokenService.createToken(sysUserEntity.getUserId());
    }

    @GetMapping("/logout")
    public R logout(){
        return sysUserTokenService.logout();
    }

}
