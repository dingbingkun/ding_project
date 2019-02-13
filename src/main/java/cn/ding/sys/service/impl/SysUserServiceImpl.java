package cn.ding.sys.service.impl;

import cn.ding.sys.entity.SysUserEntity;
import cn.ding.sys.repository.SysUserRepository;
import cn.ding.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public SysUserEntity findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public SysUserEntity findById(Long id){
        return sysUserRepository.findById(id).get();
    }

}
