package cn.ding.arcsoft.library;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class AFR_FSDK_Version extends Structure {
    public int lCodebase;
    public int lMajor;
    public int lMinor;
    public int lBuild;
    public int lFeatureLevel;
    public String Version;
    public String BuildDate;
    public String CopyRight;
    
    @Override
    protected List getFieldOrder() { 
        return Arrays.asList(new String[] { 
            "lCodebase", "lMajor", "lMinor", "lBuild","lFeatureLevel","Version", "BuildDate", "CopyRight"
        });
    }
}

