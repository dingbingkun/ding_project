package cn.ding.sys.controller;

import cn.ding.sys.entity.SysUserEntity;
import cn.ding.sys.repository.SysUserRepository;
import cn.ding.common.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserRepository sysUserRepository;

    @PostMapping("/getSysUserList")
    public Map<String, Object> getSysUserList(){
        List<SysUserEntity> sysUserEntities = (List<SysUserEntity>) sysUserRepository.findAll();
        return R.ok().put("sysUserList",sysUserEntities);
    }
}
