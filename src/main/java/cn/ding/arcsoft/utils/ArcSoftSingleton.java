package cn.ding.arcsoft.utils;

import cn.ding.arcsoft.library.AFD_FSDKLibrary;
import cn.ding.arcsoft.library.AFR_FSDKLibrary;
import cn.ding.arcsoft.library.CLibrary;
import cn.ding.arcsoft.library._AFD_FSDK_OrientPriority;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public enum ArcSoftSingleton {
    INSTANCE;
    private Pointer hFDEngine;
    private Pointer hFREngine;

    private static final String APPID_WINDOWS = "BzrS7A4zYqjuan7h6SeVFveWCN8PCHZztg9MJJKn4ftE";
    private static final String FD_SDKKEY_WINDOWS = "BiYqcrmxFeSgp7FsTrJBC19MdPeCqWwywaoW1rZB2TM7";
    private static final String FR_SDKKEY_WINDOWS = "BiYqcrmxFeSgp7FsTrJBC19j7bRfyKCKuVhHEgFMiZ6F";

    private static final String APPID_LINUX = "BzrS7A4zYqjuan7h6SeVFvedMmPaTge3Ho9J4JWXZL8b";
    private static final String FD_SDKKEY_LINUX = "B4oaFS4RzuTGDVxAammk3VBbemh8GqsmQ9BZCxfAES5x";
    private static final String FR_SDKKEY_LINUX = "B4oaFS4RzuTGDVxAammk3VBipAxKfL6qBhAHffCAq9Mr";

    private static final int FD_WORKBUF_SIZE = 40 * 1024 * 1024;
    private static final int FR_WORKBUF_SIZE = 40 * 1024 * 1024;
    private static final int MAX_FACE_NUM = 10;
    private Pointer pFDWorkMem = CLibrary.INSTANCE.malloc(FD_WORKBUF_SIZE);
    private Pointer pFRWorkMem = CLibrary.INSTANCE.malloc(FR_WORKBUF_SIZE);

    ArcSoftSingleton(){
        PointerByReference phFDEngine = new PointerByReference();
        String appId = Platform.isWindows()?APPID_WINDOWS:APPID_LINUX;
        String FD_SDKKey = Platform.isWindows()?FD_SDKKEY_WINDOWS:FD_SDKKEY_LINUX;
        String FR_SDKKey = Platform.isWindows()?FR_SDKKEY_WINDOWS:FR_SDKKEY_LINUX;
        NativeLong retFD = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_InitialFaceEngine(appId, FD_SDKKey, pFDWorkMem,
                FD_WORKBUF_SIZE, phFDEngine, _AFD_FSDK_OrientPriority.AFD_FSDK_OPF_0_HIGHER_EXT, 32, MAX_FACE_NUM);
        if (retFD.longValue() != 0) {
            CLibrary.INSTANCE.free(pFDWorkMem);
        }
        hFDEngine = phFDEngine.getValue();

        PointerByReference phFREngine = new PointerByReference();
        NativeLong retFR = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_InitialEngine(appId, FR_SDKKey, pFRWorkMem, FR_WORKBUF_SIZE,
                phFREngine);
        if (retFR.longValue() != 0) {
            CLibrary.INSTANCE.free(pFRWorkMem);
        }
        hFREngine = phFREngine.getValue();
    }

    public Pointer getFDInstance(){
        return hFDEngine;
    }
    public Pointer getFRInstance(){
        return hFREngine;
    }
}
