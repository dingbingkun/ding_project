package cn.ding.arcsoft.library;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class AFT_FSDK_FACERES extends Structure{
    public static class ByReference extends AFT_FSDK_FACERES implements Structure.ByReference {
        public ByReference() {

        }

        public ByReference(Pointer p) {
            super(p);
        }
    };
	public int nFace;
    public MRECT.ByReference rcFace;
    public IntByReference lfaceOrient;
    public AFT_FSDK_FACERES() {

    }

    public AFT_FSDK_FACERES(Pointer p) {
        super(p);
        read();
    }
    @Override
	protected List<String> getFieldOrder() {
    	return Arrays.asList(new String[] { "nFace", "rcFace" ,"lfaceOrient"});
	}

}
