package cn.ding.arcsoft.library;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class AFT_FSDK_Version extends Structure {
	public int lCodebase;
	public int lMajor;
	public int lMinor;
	public int lBuild;
	public String Version;
	public String BuildDate;
	public String CopyRight;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList(
				new String[] { "lCodebase", "lMajor", "lMinor", "lBuild", "Version", "BuildDate", "CopyRight" });
	}

	public int getlCodebase() {
		return lCodebase;
	}

	public void setlCodebase(int lCodebase) {
		this.lCodebase = lCodebase;
	}

	public int getlMajor() {
		return lMajor;
	}

	public void setlMajor(int lMajor) {
		this.lMajor = lMajor;
	}

	public int getlMinor() {
		return lMinor;
	}

	public void setlMinor(int lMinor) {
		this.lMinor = lMinor;
	}

	public int getlBuild() {
		return lBuild;
	}

	public void setlBuild(int lBuild) {
		this.lBuild = lBuild;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getBuildDate() {
		return BuildDate;
	}

	public void setBuildDate(String buildDate) {
		BuildDate = buildDate;
	}

	public String getCopyRight() {
		return CopyRight;
	}

	public void setCopyRight(String copyRight) {
		CopyRight = copyRight;
	}
	
}
