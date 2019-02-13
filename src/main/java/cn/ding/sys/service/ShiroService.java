package cn.ding.sys.service;

import java.util.Set;

public interface ShiroService {
    Set<String> getUserPermissions(Long uesrId);
}
