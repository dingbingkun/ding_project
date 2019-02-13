package cn.ding.face.service.impl;

import cn.ding.face.entity.FaceEntity;
import cn.ding.face.repository.FaceRepository;
import cn.ding.face.service.FaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class FaceServiceImpl implements FaceService {
    @Resource
    FaceRepository faceRepository;

    @Override
    public void save(FaceEntity faceEntity) {
        faceRepository.save(faceEntity);
    }

    @Override
    public Iterable<FaceEntity> findAll() {
        return faceRepository.findAll();
    }
}
