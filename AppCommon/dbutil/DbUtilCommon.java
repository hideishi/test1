/*
 * 作成日： 2005/07/03
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package common.dbutil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

//import makedao.CommandInfo;
//import makedao.MakeDaoInfo;
//import makevo.DBFieldInfo;
//import makevo.DBTableInfo;
//import makevo.SearchParameterItem;
//import makevo.TableNameInfo;
//import mkdbconfig.MergeFileInfo;

//import org.apache.log4j.Logger;
//import org.apache.log4j.Level;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import xmlutil.XmlUtil;

//import common.dbaccess.ManageDB;

/**
 * @author ken
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。 ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞
 * コード・テンプレート
 */
public class DbUtilCommon {
//	private Logger logger = Logger.getLogger(DbUtilCommon.class);

	private XmlUtil xmlUtil = null;
	private String cfgname = null ;
	private String driver ;
	private String url ;
	private String user ;
	private String password ;
	private String encode ;
	private String useUnicode ;
	private int dbkind ;

	private ManageDB mdb = null;

	private ResultSetMetaData rsm = null;

	public DbUtilCommon() {
	}
	public DbUtilCommon(String cfgname) {
		makeXmlUtil(cfgname);
	}
	public DbUtilCommon(int dbkind, String cfgname) {
		this.dbkind = dbkind ;
		makeXmlUtil(cfgname);
		getConfigInfoDatabase(cfgname, DbUtilConst.DB_RESOURCE_NAME[dbkind - 1]);

	}
	private void makeXmlUtil(String cfgname){
		try {
			if (this.cfgname != null){
				if (cfgname.compareToIgnoreCase(this.cfgname)==0){
					return ;
				}
			}
			xmlUtil = new XmlUtil(cfgname);
			this.cfgname = cfgname ;
		} catch (ParserConfigurationException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		}

	}
	public DbUtilCommon(String cfgname, String resourceName) {

		makeXmlUtil(cfgname);
		getConfigInfoDatabase(cfgname, resourceName);

	}

//	public MkClassCommon(int dbkind) {
//		this.driver = t_driver[dbkind - 1];
//		this.url = t_url[dbkind - 1];
//		this.user = t_user[dbkind - 1];
//		this.password = t_password[dbkind - 1];
//	}
//
//	public MkClassCommon(int dbkind, String user, String password) {
//		this.driver = t_driver[dbkind - 1];
//		this.url = t_url[dbkind - 1];
//		this.user = user;
//		this.password = password;
//	}
//
//	public MkClassCommon(int dbkind, String url, String user, String password) {
//		this.driver = t_driver[dbkind - 1];
//		this.url = url;
//		this.user = user;
//		this.password = password;
//	}

	public DbUtilCommon(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	public void setDriver(String driver){
		this.driver = driver ;
	}
	public void setUser(String user){
		this.user = user ;
	}
	public void setUrl(String url){
		this.url = url ;
	}
	public void setPassword(String pwd){
		this.password = pwd ;
	}
	public String getDriver(){
		return driver ;
	}

	public String getUrl() {
		return url;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}

	public int getDbkind() {
		return dbkind;
	}
	public void setDbkind(int dbkind) {
		this.dbkind = dbkind;
	}
	/**
	 * テーブル情報取得
	 *
	 * @param tblName
	 * @return テーブル情報
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
/*	public void getTableInfo(ManageDB mdb, DBTableInfo tableInfo)
			throws SQLException, ClassNotFoundException {

		// mdb = new ManageDB(driver, url, user, password);

		// コネクションを接続（自動コミットモードＯＦＦ）
		mdb.open();

		// テーブル検索
		String sql_where = "";
		String sql = "select * from " + tableInfo.getTableName() + sql_where;
		ResultSet rset = mdb.getResultSet(sql);

		rsm = rset.getMetaData();

		tableInfo.setColumnCount(rsm.getColumnCount());

		LinkedList fiList = new LinkedList();

		for (int i = 0; i < rsm.getColumnCount(); i++) {
			DBFieldInfo fi = new DBFieldInfo();
			fi.setColumnName(rsm.getColumnName(i + 1));
			fi.setColumnTypeName(rsm.getColumnTypeName(i + 1));
			fi.setPrecision(rsm.getPrecision(i + 1));
			fi.setScale(rsm.getScale(i + 1));
			fiList.add(fi);
		}
		tableInfo.setFieldInfoList(fiList);
		return;
	}
*/
	/**
	 * テーブル情報取得
	 *
	 * @param tableInfo
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void getTableInfo(DBTableInfo tableInfo) throws SQLException,
			ClassNotFoundException {

		 mdb = new ManageDB(driver, url, user, password);

		// コネクションを接続（自動コミットモードＯＦＦ）
		mdb.open();

		String tblname =  "'" + tableInfo.getTableName().toUpperCase() + "'";

		/** コメント取得MAP */
		HashMap<String, String> commentMap = new HashMap<String, String>();
		/** INDEX情報取得MAP */
		HashMap<String, IndexInfo> indexMap = new HashMap<String, IndexInfo>();

		if (dbkind == 1){
			/** oracleの場合*/
			/** コメント取得 */
			getCommentInfo(mdb, tblname, commentMap);

			/** INDEX情報取得 */
			getIndexInfo(mdb, tblname, indexMap);
		}

		// テーブル検索
		String sql_where = "";
		String sql = "select * from " + tableInfo.getTableName() + sql_where;
		ResultSet rset = mdb.getResultSet(sql);

		rsm = rset.getMetaData();

		tableInfo.setColumnCount(rsm.getColumnCount());

		LinkedList<DBFieldInfo> fieldList = new LinkedList<DBFieldInfo>();

		for (int i = 0; i < rsm.getColumnCount(); i++) {
			DBFieldInfo fi = new DBFieldInfo();

			fi.setColumnName(DbUtilTypeConv.conv2Hangary(rsm.getColumnName(i + 1)));

			String typeName = rsm.getColumnTypeName(i + 1);
			fi.setColumnTypeName( typeName );
			fi.setPrecision(rsm.getPrecision(i + 1));
			fi.setScale(rsm.getScale(i + 1));

			int length = rsm.getPrecision(i + 1);

			if (dbkind == 2 && typeName.compareToIgnoreCase("varchar")==0){
				// postgreSQL でかつ　varcharの場合
				length = getVarcharLength("public", tableInfo.getTableName(), rsm.getColumnName(i + 1));

				System.out.println("length = " + length);
			}
			fi.setLength(length);

			/** コメント設定*/
			fi.setComment(commentMap.get(rsm.getColumnName(i + 1)));

			/** NOT NULL 制約設定*/
			int rslt = rsm.isNullable(i + 1);
			switch(rslt){
			case ResultSetMetaData.columnNoNulls:
				fi.setNotnull(true);
				break;
			case ResultSetMetaData.columnNullable:
				fi.setNotnull(false);
				break;
			case ResultSetMetaData.	columnNullableUnknown:
				fi.setNotnull(true);
			}
			/** INDEX情報設定 */
			IndexInfo info = indexMap.get(rsm.getColumnName(i + 1));
			if (info != null){
				fi.setUnique( info.isUnique() );
				fi.setIndexName(info.getIdxName());
			}
			fieldList.add(fi);
		}
		tableInfo.setFieldInfoList(fieldList);

		mdb.close();

		return;
	}
	/**
	 * postgreSQL でかつvarchar型 の場合の桁数を取得
	 * @param schemaname　スキーマ名
	 * @param tblname　テーブル名
	 * @param colname　カラム名
	 * @return　桁数
	 * @throws SQLException
	 */
	private int getVarcharLength(String schemaname, String tblname, String colname) throws SQLException{
		String sql = "select " +
		"case typname " +
		"when '_bpchar' then atttypmod - 4 " +
		"when '_varchar' then atttypmod - 4 " +
		"when '_numeric' then (atttypmod - 4) / 65536 " +
		"else attlen  " +
		"end as length " +
		"from pg_stat_user_tables as a, " +
		"pg_attribute as b, " +
		"pg_type as c " +

	  	"where schemaname='" + schemaname + "' " +
		"and relname='" + tblname + "' " +
		"and attname='" + colname + "' " +

		"and a.relid=b.attrelid " +
		"and b.attnum>0 " +
		"and b.atttypid=c.typelem " +
		"and substr(typname,1,1)='_' " +
		"order by schemaname,relname,attnum; " ;

		ResultSet rset = mdb.getResultSet(sql);

		rset.next();
		String strLength = rset.getString("length");

		return  (new Integer(strLength)).intValue();

	}
	/**
	 * コメント情報取得
	 * @param mdb
	 * @param tblname
	 * @param commentMap
	 * @throws SQLException
	 */
	private void getCommentInfo(ManageDB mdb, String tblname, HashMap<String, String> commentMap)	throws SQLException{
		{
			String sql_com = "SELECT COLUMN_NAME AS COLNAME, COMMENTS FROM USER_COL_COMMENTS WHERE TABLE_NAME = " + tblname ;

			ResultSet rset_com = mdb.getResultSet(sql_com);
			while (rset_com.next()) {
	            String key = rset_com.getString("COLNAME").toUpperCase();
	            String value = rset_com.getString("COMMENTS");
	            commentMap.put(key, value);
			}
		}
	}
	/**
	 * インデックス情報取得
	 * @param mdb
	 * @param tblname
	 * @param indexMap
	 * @throws SQLException
	 */
	private void getIndexInfo(ManageDB mdb, String tblname, HashMap<String, IndexInfo> indexMap) throws SQLException{

		String sql_idx = "SELECT B.COLUMN_NAME AS COLNAME , A.INDEX_NAME AS IDXNAME, A.UNIQUENESS AS WKUNIQUE" +
			" FROM USER_INDEXES A, USER_IND_COLUMNS B "+
			" WHERE A.TABLE_NAME=B.TABLE_NAME " +
			" AND  A.INDEX_NAME=B.INDEX_NAME " +
			" AND A.TABLE_NAME = " + tblname ;

		ResultSet rset_idx = mdb.getResultSet(sql_idx);
		while (rset_idx.next()) {
            String key = rset_idx.getString("COLNAME").toUpperCase();

            IndexInfo value = new IndexInfo();
            value.setIdxName(rset_idx.getString("IDXNAME").toUpperCase());
            String wkunique = rset_idx.getString("WKUNIQUE").toUpperCase();
            if (wkunique.compareToIgnoreCase("UNIQUE")==0){
            	value.setUnique(true);
            }else{
            	value.setUnique(false);
            }
            indexMap.put(key, value);
		}
	}
	/**
	 * DBテーブル定義情報取得
	 * @param tableInfo
	 * @param tblname
	 * @param cfgname
	 */
	public void getConfigInfoTable(DBTableInfo tableInfo,
			String tblname,
			String cfgname) {

		makeXmlUtil(cfgname);

		Element root = xmlUtil.getDocument().getDocumentElement();
		NodeList tableNodes = root.getElementsByTagName("Table");

		// itemリスト生成
		LinkedList<Element> tableList = null;

		if (tableNodes.getLength() != 0) {
			// 属性name = tblname のEllementListを取得
			tableList = xmlUtil.getElementList(tableNodes, "name", tblname);
		}

		LinkedList<DBFieldInfo> fiList = new LinkedList<DBFieldInfo>();

		Element element = null;
		NodeList columnList = null;

		Iterator<Element> itr = tableList.iterator();
		while (itr.hasNext()) {
			element = (Element) itr.next();
			columnList = element.getElementsByTagName("column");

			tableInfo.setColumnCount(columnList.getLength());

			for (int i = 0; i < columnList.getLength(); i++) {
				Element elem = (Element) columnList.item(i);

				DBFieldInfo fi = new DBFieldInfo();
				fi.setColumnName(elem.getAttribute("name"));
				fi.setColumnTypeName(elem.getAttribute("type"));
				String length = elem.getAttribute("length");
				String prec = elem.getAttribute("precision");
				String scale = elem.getAttribute("scale");
				String pkey = elem.getAttribute("primaryKey");
				String unique = elem.getAttribute("unique");
				String notnull = elem.getAttribute("notnull");
				if (length.length() == 0) {
					length = "0";
				}
				if (prec.length() == 0) {
					prec = "0";
				}
				if (scale.length() == 0) {
					scale = "0";
				}
				fi.setLength((new Integer(length)).intValue());
				fi.setPrecision((new Integer(prec)).intValue());
				fi.setScale((new Integer(scale)).intValue());
				if (pkey.equalsIgnoreCase("true")) {
					fi.setPrimaryKey(true);
				} else {
					fi.setPrimaryKey(false);
				}
				if (unique.equalsIgnoreCase("true")) {
					fi.setUnique(true);
				} else {
					fi.setUnique(false);
				}
				if (notnull.equalsIgnoreCase("true")) {
					fi.setNotnull(true);
				} else {
					fi.setNotnull(false);
				}
				fiList.add(fi);
			}
		}
		tableInfo.setFieldInfoList(fiList);

	}
	public void getConfigInfoMergeXml(String cfgFilename, String cfgname, MergeFileInfo mfi) {

		//XmlUtil util = null;
		try {
			if (xmlUtil == null){
				xmlUtil = new XmlUtil(cfgFilename);
			}
		} catch (ParserConfigurationException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		}

		Element root = xmlUtil.getDocument().getDocumentElement();

		NodeList targetNodes = root.getElementsByTagName("Target");
        Element targetElem = (Element) targetNodes.item(0);
		String targetFilename = targetElem.getAttribute("fname");
		mfi.setTargetFilename(targetFilename);

		NodeList configNodes = root.getElementsByTagName("Configs");
		LinkedList<Element> configsList = null;
		if (configNodes.getLength() != 0) {
			configsList = xmlUtil.getElementList(configNodes, "name", cfgname);
		}
		ArrayList<String> mergeFilenameList = mfi.getMergeFilelist();

		NodeList configList = null;
		Iterator<Element> itr = configsList.iterator();
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			configList = element.getElementsByTagName("config");

			for (int i = 0; i < configList.getLength(); i++) {
				Element elem = (Element) configList.item(i);

				String fname = elem.getAttribute("fname");
				mergeFilenameList.add(fname);
			}
		}
	}
	public void getConfigInfoMakeDao(String cfgFilename, MakeDaoInfo mdi) {

		makeXmlUtil(cfgFilename);

		Element root = xmlUtil.getDocument().getDocumentElement();

		//ベースディレクトリ
		String baseDir = getAttributeByName(root, "BaseDirectory", "dir");
		mdi.setBaseDirectory(baseDir);

		//定義ファイル
		String dbCfgFilename = getAttributeByName(root, "DbCfgFilename", "value");
		mdi.setDbCfgFilename(dbCfgFilename);

		//出力ディレクトリ
		String outputDir = getAttributeByName(root, "OutputDir", "value");
		mdi.setOutputDirectory(outputDir);

		//データベース種別
		String dbkind = getAttributeByName(root, "Dbkind", "value");
		mdi.setDbkindName(dbkind);

		ArrayList<CommandInfo> cmdinfoList= mdi.getCmdinfoList();
		NodeList commandList = root.getElementsByTagName("Command");
		for(int i=0; i < commandList.getLength();i++){
			Element elem = (Element) commandList.item(i);

			CommandInfo cmdinfo = new CommandInfo();

			cmdinfo.setDbCfgfileName(elem.getAttribute("cfgFilename"));
			cmdinfo.setCommandName(elem.getAttribute("commandName"));
			cmdinfo.setConditionName(elem.getAttribute("conditionName"));
			cmdinfo.setDbkindName(elem.getAttribute("dbkind"));
			cmdinfo.setOutputDir(elem.getAttribute("outputDir"));
			cmdinfo.setTableName(elem.getAttribute("tableName"));

			cmdinfo.setOutputClassname(elem.getAttribute("outputClassname"));
			cmdinfo.setInsertCondition(elem.getAttribute("insertCondition"));
			cmdinfo.setUpdateCondition(elem.getAttribute("updateCondition"));

			cmdinfoList.add(cmdinfo);
		}
	}
	/**
	 * 属性値を取得
	 * @param parent	親element
	 * @param tagname	タグ名
	 * @param attrname	属性名
	 * @return
	 */
	private String getAttributeByName(Element parent, String tagname, String attrname){
		NodeList targetNodes = parent.getElementsByTagName( tagname );
        Element targetElem = (Element) targetNodes.item(0);
		return targetElem.getAttribute( attrname );
	}
	/**
	 * 指定したタグの指定した属性値を取得
	 *
	 * 対応する書式
	 * <root>
	 * 		<tagName attrName="" />
	 * </root>
	 *
	 * @param cfgFilename
	 * @param tagName
	 * @param attrName
	 * @return
	 */
	public String getAttrValue(String cfgFilename, String tagName, String attrName){
		try {
			if (xmlUtil == null){
				xmlUtil = new XmlUtil(cfgFilename);
			}
		} catch (ParserConfigurationException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成した catch ブロック
			e.printStackTrace();
		}

		Element root = xmlUtil.getDocument().getDocumentElement();

		NodeList targetNodes = root.getElementsByTagName( tagName );
        Element targetElem = (Element) targetNodes.item(0);

        return targetElem.getAttribute( attrName );

	}
	/**
	 * データベース定義情報取得
	 * @param itemList
	 * @param tblname
	 * @param cfgname
	 */
	private void getConfigInfoDatabase(String cfgname, String resourceName) {

		Element root = xmlUtil.getDocument().getDocumentElement();
		NodeList resourceParamsNodes = root
				.getElementsByTagName("ResourceParams");

		// itemリスト生成
		LinkedList<Element> resourceParamsList = null;

		if (resourceParamsNodes.getLength() != 0) {
			resourceParamsList = xmlUtil.getElementList(resourceParamsNodes,
					"name", resourceName);
		}
		if (resourceParamsList == null){
			throw new IllegalStateException("データベース接続情報が定義されていません") ;
		}
		// LinkedList itemList = new LinkedList();
		NodeList parameterList = null;

		Iterator<Element> itr = resourceParamsList.iterator();
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			parameterList = element.getElementsByTagName("parameter");

			for (int i = 0; i < parameterList.getLength(); i++) {
				Element elem = (Element) parameterList.item(i);

				String item = elem.getAttribute("name");
//				String value = null;
//				itemList.add(item);
				if (item.compareToIgnoreCase("driverClassName")==0){
					driver = elem.getAttribute("value");
				}
				else if (item.compareToIgnoreCase("url")==0){
					url = elem.getAttribute("value");
				}
				else if (item.compareToIgnoreCase("username")==0){
					user = elem.getAttribute("value");
				}
				else if (item.compareToIgnoreCase("password")==0){
					password = elem.getAttribute("value");
				}
				else if (item.compareToIgnoreCase("encode")==0){
					encode = elem.getAttribute("value");
				}
				else if (item.compareToIgnoreCase("useUnicode")==0){
					useUnicode = elem.getAttribute("value");
				}

			}
		}
		if (resourceName.compareTo(DbUtilConst.DB_RESOURCE_NAME[DbUtilConst.DB_MYSQL-1])==0){
			//MySQLの場合
			StringBuilder sbl = new StringBuilder(url);
			sbl.append("?useUnicode=").append(useUnicode).append("&").append("characterEncoding=").append(encode);

			url = sbl.toString();
		}
	}

	/**
	 * INSERT情報取得
	 * @param itemList
	 * @param condname 条件名
	 * @param cfgname
	 */
	public void getConfigInfoInsert(LinkedList<String> itemList, String condname,
			String cfgname) {

		makeXmlUtil(cfgname);

		Element root = xmlUtil.getDocument().getDocumentElement();
		NodeList insertConditionNodes = root
				.getElementsByTagName("InsertCondition");

		// itemリスト生成
		LinkedList<Element> insertConditionList = null;

		if (insertConditionNodes.getLength() != 0) {
			insertConditionList = xmlUtil.getElementList(insertConditionNodes,
					"name", condname);
		}
		// LinkedList itemList = new LinkedList();
		NodeList parameterList = null;

		Iterator<Element> itr = insertConditionList.iterator();
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			parameterList = element.getElementsByTagName("parameter");

			for (int i = 0; i < parameterList.getLength(); i++) {
				Element elem = (Element) parameterList.item(i);

				String item = elem.getAttribute("name").toLowerCase();
				itemList.add(item);
			}
		}
	}

	/**
	 * SELECT情報取得
	 * @param searchItemList
	 * @param condParamItemList
	 * @param conditionItemList
	 * @param tblname
	 * @param cfgname
	 */
	public void getConfigInfoSelect(
			LinkedList<SearchParameterItem> searchItemList,
			LinkedList<String> condParamItemList,
			LinkedList<String> conditionItemList,
			LinkedList<TableNameInfo> joinTableItemList,
			String conditionName,
			String cfgname) {

//		logger.log(Level.TRACE, "MkClassCommon#getConfigInfoSelect conditionName = " + conditionName);
//		logger.log(Level.TRACE, "MkClassCommon#getConfigInfoSelect cfgname = " + cfgname);

		makeXmlUtil(cfgname);

		Element root = xmlUtil.getDocument().getDocumentElement();
		NodeList selectConditionNodes = root
				.getElementsByTagName("SelectCondition");

		// itemリスト生成
		LinkedList<Element> selectConditionList = null;

		if (selectConditionNodes.getLength() != 0) {
			selectConditionList = xmlUtil.getElementList(selectConditionNodes,
					"name", conditionName);
		} else {
			return;
		}
		// LinkedList itemList = new LinkedList();
		NodeList joinTableList = null;
		NodeList searchParameterList = null;
		NodeList condParameterList = null;
		NodeList conditionList = null;

		Iterator<Element> itr = selectConditionList.iterator();
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			joinTableList = element.getElementsByTagName("searchTable");
			searchParameterList = element
					.getElementsByTagName("searchParameter");
			condParameterList = element.getElementsByTagName("condParameter");
			conditionList = element.getElementsByTagName("condition");

			for (int i = 0; i < joinTableList.getLength(); i++) {
				Element elem = (Element) joinTableList.item(i);
				TableNameInfo tni = new TableNameInfo();
				tni.setTblName(elem.getAttribute("name").toLowerCase());
				tni.setAliasName(elem.getAttribute("alias").toLowerCase());
				joinTableItemList.add(tni);
			}
			for (int i = 0; i < searchParameterList.getLength(); i++) {
				Element elem = (Element) searchParameterList.item(i);
				SearchParameterItem sitem = new SearchParameterItem();
				sitem.setName(elem.getAttribute("name").toLowerCase());
				if (elem.getAttribute("link") != null) {
					if (elem.getAttribute("link").length() != 0) {
						sitem.setLink(elem.getAttribute("link"));
					}
				}
				searchItemList.add(sitem);
			}
			for (int i = 0; i < condParameterList.getLength(); i++) {
				Element elem = (Element) condParameterList.item(i);
				String item = elem.getAttribute("name").toLowerCase();
				condParamItemList.add(item);
			}
			for (int i = 0; i < conditionList.getLength(); i++) {
				Element elem = (Element) conditionList.item(i);
				String item = elem.getAttribute("value");
				conditionItemList.add(item);
			}
		}

	}

	/**
	 * UPDATE情報取得
	 * @param searchItemList
	 * @param condParamItemList
	 * @param conditionItemList
	 * @param conditionName
	 * @param cfgname
	 */
	public void getConfigInfoUpdate(LinkedList<String> updateItemList,
			LinkedList<String> condParamItemList,
			LinkedList<String> conditionItemList,
			String conditionName, String cfgname) {

		makeXmlUtil(cfgname);

		Element root = xmlUtil.getDocument().getDocumentElement();
		NodeList updateConditionNodes = root
				.getElementsByTagName("UpdateCondition");

		// itemリスト生成
		LinkedList<Element> updateConditionList = null;

		if (updateConditionNodes.getLength() != 0) {
			updateConditionList = xmlUtil.getElementList(updateConditionNodes,
					"name", conditionName);
		}
		// LinkedList itemList = new LinkedList();
		NodeList updateParameterList = null;

		Iterator<Element> itr = updateConditionList.iterator();
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			updateParameterList = element.getElementsByTagName("setParameter");

			for (int i = 0; i < updateParameterList.getLength(); i++) {
				Element elem = (Element) updateParameterList.item(i);

				//UpdateSetParam param = new UpdateSetParam();
				String item = elem.getAttribute("name").toLowerCase();
				updateItemList.add(item);
			}
		}
		NodeList condParameterList = null;

		itr = updateConditionList.iterator();
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			condParameterList = element.getElementsByTagName("condParameter");

			for (int i = 0; i < condParameterList.getLength(); i++) {
				Element elem = (Element) condParameterList.item(i);

				String item = elem.getAttribute("name").toLowerCase();
				condParamItemList.add(item);
			}
		}
		NodeList conditionList = null;

		itr = updateConditionList.iterator();
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			conditionList = element.getElementsByTagName("condition");

			for (int i = 0; i < conditionList.getLength(); i++) {
				Element elem = (Element) conditionList.item(i);

				String item = elem.getAttribute("value").toLowerCase();
				conditionItemList.add(item);
			}
		}
	}

}