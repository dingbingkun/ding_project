package cn.ding.arcsoft.service;

import cn.ding.arcsoft.library.*;
import cn.ding.arcsoft.utils.ArcSoftSingleton;
import cn.ding.arcsoft.utils.BufferInfo;
import cn.ding.arcsoft.utils.ImageLoader;
import cn.ding.common.exception.DingException;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.PointerByReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ding
 * @Description:
 * @Date: Create in 2018-07-04 15:21:06
 * @Modifyed by:
 */
@Service
public class ArcSoftService {

    //人脸识别引擎
    private Pointer hFDEngine = ArcSoftSingleton.INSTANCE.getFDInstance();
    private Pointer hFREngine = ArcSoftSingleton.INSTANCE.getFRInstance();

    @Resource
    private EhCacheFaceFeatureService ehCacheFaceFeatureService;

    /**
     * 从已知模型中，查找人脸
     * @param path 图片路径
     * @return
     */
    public List<String> searchFace(String path) throws Exception{
        //获取已有的人脸特征信息
        Map<String,List> faceFeatureMap = ehCacheFaceFeatureService.getFaceFeature();

        //加载图片
        ASVLOFFSCREEN inputImg = loadImage(path);
        //识别人脸区域
        FaceInfo[] faceInfo = doFaceDetection(inputImg);
        //获取人脸信息
        List<AFR_FSDK_FACEMODEL> faceFeatures = getFaceFeatureList(inputImg,faceInfo);

        List<String> result = new ArrayList<>();
        try{
            for(AFR_FSDK_FACEMODEL faceFeature :faceFeatures) {
                outerLoop:for (Map.Entry<String, List> entry : faceFeatureMap.entrySet()) {
                    String name = entry.getKey();
                    List<AFR_FSDK_FACEMODEL> faceFeatureList = entry.getValue();
                    for (AFR_FSDK_FACEMODEL fF : faceFeatureList) {
                        FloatByReference fSimilarScore = new FloatByReference(0.0f);
                        synchronized (this){
                            NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_FacePairMatching(hFREngine, faceFeature, fF,
                                    fSimilarScore);
                            if (ret.longValue() != 0) {
                                throw new DingException("检测失败");
                            }
                        }
                        if (fSimilarScore.getValue() > 0.68) {
                            result.add(name);
                            break outerLoop;
                        }else if(fSimilarScore.getValue()<0.3){
                            break;
                        }
                    }
                }
            }
            if(result.size()<1){
                throw new DingException("共检测出"+faceFeatures.size()+"张人脸，但未能识别出姓名");
            }
            return result;
        }finally {
            for(AFR_FSDK_FACEMODEL fF : faceFeatures){
                fF.freeUnmanaged();
            }
        }
    }

    /**
     * 获取人脸模型信息
     * @param inputImg
     * @param faceInfo
     * @return
     */
    public List<AFR_FSDK_FACEMODEL> getFaceFeatureList(ASVLOFFSCREEN inputImg,FaceInfo[] faceInfo) throws Exception{
        List<AFR_FSDK_FACEMODEL> faceFeatures = new ArrayList<>();
        for(FaceInfo face : faceInfo){
            AFR_FSDK_FACEMODEL faceFeature = extractFRFeature(inputImg,face);
            if(faceFeature != null){
                faceFeatures.add(faceFeature);
            }
        }
        if(faceFeatures.size()<1){
            throw new DingException("获取人脸模型信息异常");
        }
        return faceFeatures;
    }

    /**
     * 获取人脸模型信息
     * @param imagePath 图片路径
     * @return
     */
    public List<AFR_FSDK_FACEMODEL> getFaceFeatureList(String imagePath) throws Exception{
        ASVLOFFSCREEN inputImg;
        FaceInfo[] faceInfo;
        List<AFR_FSDK_FACEMODEL> faceFeatures = new ArrayList<>();
        inputImg = loadImage(imagePath);
        faceInfo = doFaceDetection(inputImg);
        if (faceInfo.length < 1) {
            return faceFeatures;
        }
        for(FaceInfo face : faceInfo){
            AFR_FSDK_FACEMODEL faceFeature = extractFRFeature(inputImg,face);
            if(faceFeature != null){
                faceFeatures.add(faceFeature);
            }
        }

        return faceFeatures;
    }

    /**
     * 加载图片
     * @param filePath
     * @return
     */
    private ASVLOFFSCREEN loadImage(String filePath) {
        ASVLOFFSCREEN inputImg = new ASVLOFFSCREEN();

        BufferInfo bufferInfo = ImageLoader.getI420FromFile(filePath);
        inputImg.u32PixelArrayFormat = ASVL_COLOR_FORMAT.ASVL_PAF_I420;
        inputImg.i32Width = bufferInfo.width;
        inputImg.i32Height = bufferInfo.height;
        inputImg.pi32Pitch[0] = inputImg.i32Width;
        inputImg.pi32Pitch[1] = inputImg.i32Width / 2;
        inputImg.pi32Pitch[2] = inputImg.i32Width / 2;
        inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
        inputImg.ppu8Plane[0].write(0, bufferInfo.buffer, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
        inputImg.ppu8Plane[1] = new Memory(inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[1].write(0, bufferInfo.buffer, inputImg.pi32Pitch[0] * inputImg.i32Height,
                inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[2] = new Memory(inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[2].write(0, bufferInfo.buffer,
                inputImg.pi32Pitch[0] * inputImg.i32Height + inputImg.pi32Pitch[1] * inputImg.i32Height / 2,
                inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[3] = Pointer.NULL;

        inputImg.setAutoRead(false);
        return inputImg;
    }


    /**
     * 识别人脸区域
     * @param inputImg
     * @return
     */
    private synchronized FaceInfo[] doFaceDetection(ASVLOFFSCREEN inputImg) {
        FaceInfo[] faceInfo = new FaceInfo[0];
        PointerByReference ppFaceRes = new PointerByReference();
        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_StillImageFaceDetection(hFDEngine, inputImg, ppFaceRes);
        if (ret.longValue() != 0) {
            throw new DingException(String.format("AFD_FSDK_StillImageFaceDetection ret 0x%x", ret.longValue()));
        }
        AFD_FSDK_FACERES faceRes = new AFD_FSDK_FACERES(ppFaceRes.getValue());
        if (faceRes.nFace > 0) {
            faceInfo = new FaceInfo[faceRes.nFace];
            for (int i = 0; i < faceRes.nFace; i++) {
                MRECT rect = new MRECT(
                        new Pointer(Pointer.nativeValue(faceRes.rcFace.getPointer()) + faceRes.rcFace.size() * i));
                int orient = faceRes.lfaceOrient.getPointer().getInt(i * 4);
                faceInfo[i] = new FaceInfo();
                faceInfo[i].left = rect.left;
                faceInfo[i].top = rect.top;
                faceInfo[i].right = rect.right;
                faceInfo[i].bottom = rect.bottom;
                faceInfo[i].orient = orient;
            }
        }
        if(faceInfo.length<1){
            throw new DingException("no face in inputImg");
        }
        return faceInfo;
    }

    /**
     * 获取人脸信息
     * @param inputImg
     * @param faceInfo
     * @return
     */
    private synchronized AFR_FSDK_FACEMODEL extractFRFeature(ASVLOFFSCREEN inputImg, FaceInfo faceInfo) throws Exception {

        AFR_FSDK_FACEINPUT faceinput = new AFR_FSDK_FACEINPUT();
        faceinput.lOrient = faceInfo.orient;
        faceinput.rcFace.left = faceInfo.left;
        faceinput.rcFace.top = faceInfo.top;
        faceinput.rcFace.right = faceInfo.right;
        faceinput.rcFace.bottom = faceInfo.bottom;

        AFR_FSDK_FACEMODEL faceFeature = new AFR_FSDK_FACEMODEL();
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_ExtractFRFeature(hFREngine, inputImg, faceinput,
                faceFeature);
        if (ret.longValue() != 0) {
            throw new DingException(String.format("AFR_FSDK_ExtractFRFeature ret 0x%x", ret.longValue()));
        }
        return faceFeature.deepCopy();
    }
}
