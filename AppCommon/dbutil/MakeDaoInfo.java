package common.dbutil;

import java.util.ArrayList;

public class MakeDaoInfo {
	/** 基底ディレクトリ*/
	private String baseDirectory ;
	/** 出力ディレクトリ*/
	private String outputDirectory ;
	/** データベース種別*/
	private String dbkindName ;
	/** DB情報定義ファイル名*/
	private String dbCfgFilename ;

	/** コマンド情報リスト*/
	private ArrayList<CommandInfo> cmdinfoList = new ArrayList<CommandInfo>() ;

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public ArrayList<CommandInfo> getCmdinfoList() {
		return cmdinfoList;
	}

	public void setCmdinfoList(ArrayList<CommandInfo> cmdinfoList) {
		this.cmdinfoList = cmdinfoList;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getDbkindName() {
		return dbkindName;
	}

	public void setDbkindName(String dbkindName) {
		this.dbkindName = dbkindName;
	}

	public String getDbCfgFilename() {
		return dbCfgFilename;
	}

	public void setDbCfgFilename(String dbCfgFilename) {
		this.dbCfgFilename = dbCfgFilename;
	}


}
