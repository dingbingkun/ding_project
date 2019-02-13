package cn.ding.sys.repository;

import cn.ding.sys.entity.SysUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends CrudRepository<SysUserEntity,Long> {
    SysUserEntity findByUsername(String username);
}
