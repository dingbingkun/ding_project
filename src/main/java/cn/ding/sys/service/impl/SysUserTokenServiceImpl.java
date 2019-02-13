package cn.ding.sys.service.impl;

import cn.ding.common.utils.R;
import cn.ding.common.service.RedisService;
import cn.ding.sys.entity.SysUserEntity;
import cn.ding.sys.oauth2.TokenGenerator;
import cn.ding.sys.repository.SysUserRepository;
import cn.ding.sys.service.SysUserTokenService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserTokenServiceImpl implements SysUserTokenService {
    @Resource
    private RedisService redisService;

    @Override
    public R createToken(Long userId) {
        //删除该用户之前的登录信息
        String oldToken = redisService.get(userId.toString());
        if(oldToken!=null){
            redisService.delete(oldToken);
        }
        String token = TokenGenerator.generateValue();
        redisService.set(userId.toString(),token);
        redisService.set(token,userId);
        return R.ok().put("token",token);
    }

    @Override
    public R logout() {
        SysUserEntity sysUserEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        String token = redisService.get(""+sysUserEntity.getUserId());
        redisService.delete(token);
        return R.ok("退出登录成功");
    }
}
