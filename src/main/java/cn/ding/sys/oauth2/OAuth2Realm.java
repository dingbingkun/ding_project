package cn.ding.sys.oauth2;

import cn.ding.common.exception.DingException;
import cn.ding.common.service.RedisService;
import cn.ding.sys.entity.SysUserEntity;
import cn.ding.sys.service.ShiroService;
import cn.ding.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 认证
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Resource
    private ShiroService shiroService;

    @Resource
    private RedisService redisService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        String userId = redisService.get(accessToken);
        if(userId==null||"".equals(userId)){
            throw new DingException("token失效，请重新登录");
        }
        //存入redis的两个token过期时间要保持同步
        redisService.get(userId);

        //查询用户信息
        SysUserEntity user = sysUserService.findById(Long.parseLong(userId));

        return new SimpleAuthenticationInfo(user, accessToken, getName());
    }
}
