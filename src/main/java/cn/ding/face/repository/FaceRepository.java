package cn.ding.face.repository;

import cn.ding.face.entity.FaceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceRepository extends CrudRepository<FaceEntity,Long> {
}
