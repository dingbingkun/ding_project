package cn.ding.sys.service;

import cn.ding.common.utils.R;

public interface SysUserTokenService {

    /**
     * 生成token
     * @param userId
     * @return
     */
    R createToken(Long userId);

    /**
     * 登出
     * @return
     */
    R logout();
}
