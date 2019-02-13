package cn.ding.face.controller;

import cn.ding.arcsoft.library.AFR_FSDK_FACEMODEL;
import cn.ding.arcsoft.library.ASVLOFFSCREEN;
import cn.ding.arcsoft.library.FaceInfo;
import cn.ding.arcsoft.service.ArcSoftService;
import cn.ding.common.utils.R;
import cn.ding.face.entity.FaceEntity;
import cn.ding.face.service.FaceService;
import cn.ding.sys.oauth2.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/face")
public class FaceController {

    @Value("${base.faceUploadPath}")
    private String faceUploadPath;

    @Resource
    private ArcSoftService arcSoftService;

    @Resource
    private FaceService faceService;


    @PostMapping("/insertFace")
    public R insertFace(@RequestParam("file") MultipartFile file, String name) throws Exception {

        //获取上传的图片并保存到服务器上
        String fileName = TokenGenerator.generateValue();
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String imagePath = faceUploadPath+fileName+fileType;
        File img = new File(imagePath);
        file.transferTo(img);

        //获取人脸信息
        List<AFR_FSDK_FACEMODEL> faceFeatures = arcSoftService.getFaceFeatureList(imagePath);
        if(faceFeatures.size()==0){
            img.delete();
            return R.error("未检测到人脸，请重新上传！");
        }else if(faceFeatures.size()>1){
            img.delete();
            return R.error("检测到超过1张人脸，请重新上传！");
        }

        //存库
        FaceEntity faceEntity = new FaceEntity();
        faceEntity.setName(name);
        faceEntity.setPath(imagePath);
        faceEntity.setFaceFeature(faceFeatures.get(0).toByteArray());
        faceService.save(faceEntity);
        return R.ok();
    }

    @PostMapping("/searchFace")
    public R searchFace(@RequestParam("file") MultipartFile file) throws Exception{
        String fileName = TokenGenerator.generateValue();
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String imagePath = faceUploadPath+fileName+fileType;
        File img = new File(imagePath);
        file.transferTo(img);
        try{
            List<String> faces = arcSoftService.searchFace(imagePath);
            return R.ok(faces.toString());
        }finally {
            img.delete();
        }
    }
}
