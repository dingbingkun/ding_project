package cn.ding.arcsoft.service;

import cn.ding.arcsoft.library.AFR_FSDK_FACEMODEL;
import cn.ding.face.entity.FaceEntity;
import cn.ding.face.service.FaceService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class EhCacheFaceFeatureService {
    @Resource
    private FaceService faceService;

    @Cacheable(value = "cacheFaceFeature")
    public Map<String,List> getFaceFeature() throws Exception{
        Map<String,List> faceFeatureMap = new HashMap<>();

        Iterable<FaceEntity> faceEntitySet = faceService.findAll();

        //所有人脸特征
        for(FaceEntity f : faceEntitySet){
            List<AFR_FSDK_FACEMODEL> ls = new ArrayList<>();
            if(!faceFeatureMap.containsKey(f.getName())){
                faceFeatureMap.put(f.getName(),ls);
            }
            faceFeatureMap.get(f.getName()).add(AFR_FSDK_FACEMODEL.fromByteArray(f.getFaceFeature()));
        }
        return faceFeatureMap;
    }
}
