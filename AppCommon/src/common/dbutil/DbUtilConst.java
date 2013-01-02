package common.dbutil;

public class DbUtilConst {
	static final public String CLASS_HEADER = "";		//"N001Iwf";
    static final public String DATECONVERT_COMMENT = "// ��t��util.Date����sql.Timestamp�Ɍ^�ϊ�";
    static final public String IMPORT_TIMESTAMP= "import java.sql.Timestamp;";
    static final public String DB_RESOURCE_NAME[] = {
    										"jdbc/OracleDB",		//oracle
    										"jdbc/PgsqlDB",			//postgreSQL
    										"jdbc/MysqlDB"			//MySQL
    										};
    static final public int DB_ORACLEL = 1 ;
    static final public int DB_PGSQL = 2 ;
    static final public int DB_MYSQL = 3 ;
}
