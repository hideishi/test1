package common.dbutil;

import java.util.ArrayList;

public class MergeFileInfo {
	private String targetFilename ;
	private ArrayList<String> mergeFilelist = new ArrayList<String>();

	public String getTargetFilename() {
		return targetFilename;
	}
	public void setTargetFilename(String targetFilename) {
		this.targetFilename = targetFilename;
	}
	public ArrayList<String> getMergeFilelist() {
		return mergeFilelist;
	}
	public void setMergeFilelist(ArrayList<String> mergeFilelist) {
		this.mergeFilelist = mergeFilelist;
	}
	public void appendMergeFilelist(String filename) {
		this.mergeFilelist.add(filename);
	}



}
