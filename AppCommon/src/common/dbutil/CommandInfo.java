package common.dbutil;

public class CommandInfo {
	private String commandName ;
	private String tableName ;
	private String outputDir ;
	private String dbkindName ;
	private String dbCfgFilename ;
	private String conditionName ;

	private String insertCondition ;
	private String updateCondition ;
	private String outputClassname ;

	public String getCommandName() {
		return commandName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
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
	public void setDbCfgfileName(String dbCfgFilename) {
		this.dbCfgFilename = dbCfgFilename;
	}
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	public String getOutputClassname() {
		return outputClassname;
	}
	public void setOutputClassname(String outputClassname) {
		this.outputClassname = outputClassname;
	}
	public String getInsertCondition() {
		return insertCondition;
	}
	public void setInsertCondition(String insertCondition) {
		this.insertCondition = insertCondition;
	}
	public String getUpdateCondition() {
		return updateCondition;
	}
	public void setUpdateCondition(String updateCondition) {
		this.updateCondition = updateCondition;
	}


}
