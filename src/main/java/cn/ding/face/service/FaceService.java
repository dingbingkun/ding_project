package cn.ding.face.service;

import cn.ding.face.entity.FaceEntity;

import java.util.Set;

public interface FaceService {
    void save(FaceEntity faceEntity);
    Iterable<FaceEntity> findAll();
}
