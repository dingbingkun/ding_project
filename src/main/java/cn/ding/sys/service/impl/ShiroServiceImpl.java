package cn.ding.sys.service.impl;

import cn.ding.sys.service.ShiroService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Override
    public Set<String> getUserPermissions(Long userId) {
        Set<String> set = new HashSet<>();
        if(userId==1){
            set.add("admin");
        }else {
            //TODO:增加角色权限
        }
        return set;
    }
}
