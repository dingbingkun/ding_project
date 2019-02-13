package cn.ding.sys.service;

import cn.ding.sys.entity.SysUserEntity;

public interface SysUserService {

    SysUserEntity findByUsername(String username);

    SysUserEntity findById(Long id);

}
