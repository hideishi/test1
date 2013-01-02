/*
 * 作成日： 2005/06/26
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package common.dbutil;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.*;

//import makevo.DBFieldInfo;

/**
 * @author ken
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class DbUtilTypeConv {
	static final public String indent = "    " ;

	/** 型変換テーブル*/
	static Hashtable<String, String> dbTypeMap = new Hashtable<String, String>();
	static Hashtable<String, String> binaryDbTypeMap = new Hashtable<String, String>();

	static final private String DATE_CONVERT =
		"Timestamp #PARAM1 = new Timestamp(inVo.get#PARAM2().getTime());" ;

//	static final private String binaryDataTypeTbl[] = {"NUMBER", "NUMERIC", "DECIMAL"};

//	static private LinkedList dateConvList = null ;
//	static private LinkedList setMethodList = null ;
	/** メソッド変換テーブル*/
	static private Hashtable<String, String> setMethodList ;
	/** メソッド変換テーブル*/
	static private Hashtable<String, String> getMethodList ;
/*
	static private String msg1 = "処理タイプ＝１の時";
	static private String msg2 = "usage : java MkDataClass 1 ＤＢテーブル名 定義ファイルフルパス";
	static private String msg3 = "処理タイプ＝２の時";
	static private String msg4 = "usage : java MkDataClass 2 ＤＢテーブル名";
*/

	static public void init(){

		dbTypeMap.put("TEXT",		"String");
		dbTypeMap.put("CHAR",		"String");
		dbTypeMap.put("VARCHAR",	"String");
		dbTypeMap.put("VARCHAR2",	"String");
		dbTypeMap.put("DATE",		"Date");
		dbTypeMap.put("DATETIME",	"Date");
		dbTypeMap.put("OID",		"byte[]");			// postgreSQL only
		dbTypeMap.put("BLOB",		"byte[]");
		dbTypeMap.put("CLOB",		"char[]");
		dbTypeMap.put("TIMESTAMP",	"Date");
		dbTypeMap.put("TINYINT"	,	"byte");
		dbTypeMap.put("DECIMAL", 		"int");
		dbTypeMap.put("INT", 		"int");
		dbTypeMap.put("INTEGER"	,	"int");
		dbTypeMap.put("INTEGER UNSIGNED",	"int");
		dbTypeMap.put("SMALLINT", 	"short");
		dbTypeMap.put("INT2", 		"short");
		dbTypeMap.put("MEDIUMINT",	"int");
		dbTypeMap.put("INT4",		"int");
		dbTypeMap.put("INT8",		"long");
		dbTypeMap.put("BIGINT",		"long");
		dbTypeMap.put("FLOAT4",		"float");
		dbTypeMap.put("FLOAT8",		"double");
		dbTypeMap.put("REAL",		"float");
		dbTypeMap.put("BOOL",		"boolean");
		dbTypeMap.put("BOOLEAN",	"boolean");

		binaryDbTypeMap.put("INT",	"int");
		binaryDbTypeMap.put("INTEGER",	"int");
		binaryDbTypeMap.put("NUMBER",	"int");
		binaryDbTypeMap.put("NUMERIC",	"int");
		binaryDbTypeMap.put("DECIMAL",	"int");

		setMethodList = new Hashtable<String, String>();
		setMethodList.put("String",		"setString(i++, #VOPARAM);");
		setMethodList.put("long",		"setLong(i++, #VOPARAM);");
		setMethodList.put("int",		"setInt(i++, #VOPARAM);");
		setMethodList.put("short",		"setShort(i++, #VOPARAM);");
		setMethodList.put("byte",		"setByte(i++, #VOPARAM);");
		setMethodList.put("Date",		"setTimestamp(i++, #VOPARAM);");
		setMethodList.put("float",		"setFloat(i++, #VOPARAM);");
		setMethodList.put("double",		"setDouble(i++, #VOPARAM);");
		setMethodList.put("BigDecimal",	"setBigDecimal(i++, #VOPARAM);");
//		setMethodList.put("byte[]",	"setBlob(i++, #VOPARAM);");
//		setMethodList.put("char[]",	"setClob(i++, #VOPARAM);");

		getMethodList = new Hashtable<String, String>();
		getMethodList.put("String",		"getString(#VOPARAM)");
		getMethodList.put("long",		"getLong(#VOPARAM)");
		getMethodList.put("int",		"getInt(#VOPARAM)");
		getMethodList.put("Date",		"getTimestamp(#VOPARAM)");
		getMethodList.put("float",		"getFloat(#VOPARAM)");
		getMethodList.put("double",		"getDouble(#VOPARAM)");
		getMethodList.put("BigDecimal",	"getBigDecimal(#VOPARAM)");
//		getMethodList.put("byte[]",	"getBlob(#VOPARAM)");
//		getMethodList.put("char[]",	"getClob(#VOPARAM)");

	}
	static public String convertType(
			int dbkind,
			DBFieldInfo fif
			) throws SQLException{

		String javaType = convDBType2JavaType(dbkind, fif);

		return javaType ;
	}
	/**
	 * DB型->Java型変換
	 * @param prec
	 * @param scale
	 * @return Fieldテキスト
	 * @throws SQLException
	 */
	static public void convertType(
			int dbkind,
			DBFieldInfo fif,
			LinkedList<String> dateConvList,
			LinkedList<String> methodList) throws SQLException{

		String javaType = convDBType2JavaType(dbkind, fif);

		//Date型変換
		if (dateConvList != null){
			if (javaType.equals("Date")){
				convDate(fif.getColumnName(), dateConvList);
			}
		}
		if ((fif.getColumnTypeName().compareToIgnoreCase("BLob")==0) ||
				(fif.getColumnTypeName().compareToIgnoreCase("CLob")==0)){
			throw new IllegalArgumentException("LOBデータは対象外です。");
		}
		//DBデータ型Blob, Clobについては,SQL文パラメータ設定コード編集処理は、スキップ
		//SQL文パラメータ設定コード編集
		String method = (String)setMethodList.get(javaType);
		String dst = "inVo.get" + conv2Hangary(fif.getColumnName()) + "()";
		if (javaType.equals("Date")){
			dst = fif.getColumnName() ;
		}
		method = method.replaceAll("#VOPARAM", dst);
		methodList.add(method);

	}
	/**
	 * DB型->Java型変換
	 * @param prec
	 * @param scale
	 * @return Fieldテキスト
	 * @throws SQLException
	 */
	static public void convertTypeSelect(
			int dbkind,
			DBFieldInfo fif,
			LinkedList<String> methodList) throws SQLException{

		String javaType = convDBType2JavaType(dbkind, fif);

		if ((fif.getColumnTypeName().compareToIgnoreCase("BLob")==0) ||
				(fif.getColumnTypeName().compareToIgnoreCase("CLob")==0)){
				throw new IllegalArgumentException("LOBデータは対象外です。");
		}

		//Date型変換
//		if (dateConvList != null){
//			if (javaType.equals("Date")){
//				convDate(fif.getColumnName(), dateConvList);
//			}
//		}
		//SQL文パラメータ設定コード編集
		String method = (String)getMethodList.get(javaType);
		method = "rset."+ method;
		String dst = "outVo.set" + conv2Hangary(fif.getColumnName()) + "(" + method + ");";
		dst = dst.replaceAll("#VOPARAM", "\"" + fif.getColumnName() + "\"");
		methodList.add(dst);

	}
//	static private String cnvBinType(String dbDtType, DBFieldInfo fif){
	static private String cnvBinType(String[] tkns, DBFieldInfo fif){
		String javaType = "";

//		Object obj = binaryDbTypeMap.get((Object)dbDtType) ;
		Object obj = binaryDbTypeMap.get((Object)tkns[0]) ;

		if (obj != null){
			int prec = fif.getPrecision();
			int scale = fif.getScale();

			if (scale == 0){
				if (prec < 10){
					javaType = "int" ;
				}else
				if(prec < 19){
					javaType = "long";
				}else{
					javaType = "BigDecimal";
				}
			}else{
				javaType = "double" ;
			}
		}
		return javaType;

	}
	//
	static private String convDBType2JavaType(int dbkind, DBFieldInfo fif) throws SQLException{

		String javaType = "";
		String dbDtType = fif.getColumnTypeName().toUpperCase();

		String[] tkns = new String[4];
		StringTokenizer st = new StringTokenizer(dbDtType, " ");

		// トークン値を取得
		int i=0;
	    while (st.hasMoreTokens()) {
		      tkns[i] = st.nextToken();
		      i++;
		}
		javaType = cnvBinType(tkns, fif);
		if (javaType.length() > 0){
			return javaType ;
		}
		javaType = (String)dbTypeMap.get((Object)tkns[0]) ;

		return javaType;

	}
	//
/*	static private String convDBType2JavaType(int dbtype,ResultSetMetaData rsm, int pos) throws SQLException{
		String javaType ="";
		String dbDtType   = rsm.getColumnTypeName(pos).toUpperCase();

		Object obj = binaryDbTypeMap.get((Object)dbDtType) ;

		if (obj != null){
			int prec = rsm.getPrecision(pos);
			int scale = rsm.getScale(pos);

			if (scale == 0){
				if (prec < 10){
					javaType = "int" ;
				}else
				if(prec < 19){
					javaType = "long";
				}else{
					javaType = "BigDecimal";
				}
			}else{
				javaType = "double" ;
			}
		}
		return javaType;
	}
*/
//
	static private void convDate(String fieldName, LinkedList<String> dateConvList){
		String dateConvert = null;

		dateConvert = DATE_CONVERT.replaceAll("#PARAM1", fieldName);
		dateConvert = dateConvert.replaceAll("#PARAM2", conv2Hangary(fieldName));
		dateConvList.add(dateConvert);

	}
    static public String conv2Hangary(String src){
    	return src.substring(0, 1).toUpperCase() + src.substring(1, src.length()).toLowerCase() ;
    }
    static public String conv2FieldType(String src){
    	return src.substring(0, 1).toLowerCase() + src.substring(1, src.length()) ;
    }
/*    static public void errorMsg(){
    	System.out.println(msg1);
    	System.out.println(msg2);
    	System.out.println(msg3);
    	System.out.println(msg4);

    }
*/
    static public void errorMsg(String msg){
    	System.out.println(msg);

    }
	/**
	 * 文字列末尾の","を取り除く
	 * @param input 対象文字列
	 * @return　編集した文字列
	 */
	static public String trimLastComma(String input){
		if (input.length()==0){
			return input ;
		}
        if (input.substring(input.length()-1,input.length()).equals(",")){
        	input = input.substring(0, input.length()-1);
        }
		return input;
	}
	/**
	 *
	 * @param javaType
	 * @return
	 */
	static public boolean isArrayType(String javaType){
		//先頭と最後の空白を取り除く
		String target = javaType.trim();

		//最後が"[]"と一致したら、配列とみなす
		int len = target.length();

		String txt = target.substring(len -2, len);
		if (txt.compareToIgnoreCase("[]")==0){
			return true ;
		}
		return false ;
	}
//	/**
//	 * クラス名編集
//	 * @param tblName テーブル名
//	 * @return クラス名
//	 */
//	static public String editClassName(String cmdname, String tblName){
//		return DbUtilConst.CLASS_HEADER + cmdname + editBaseClassName(tblName) ;
//	}
//	static public String editClassName(String cmdname, String tblName, String conditionName){
//		return DbUtilConst.CLASS_HEADER + cmdname + editBaseClassName(tblName) + conditionName;
//	}
//	static public String editBaseClassName(String tblName){
//		StringBuffer sbf = new StringBuffer("" );
//		sbf.append(tblName.toUpperCase().substring(0, 1));
//		sbf.append(tblName.toLowerCase().substring(1, tblName.length()));
//		return sbf.toString() ;
//	}
//	//20091024 APPEND S
//	/**
//	 * ファイルヘッダ編集
//	 *
//	 */
//	static public String editFileHeader(String tblName, String sourceBuff){
//
//	    Date curdate = new Date();  //(1)Dateオブジェクトを生成
//
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//
//		String outBuff = sourceBuff.replaceAll("#MAKEDATE", sdf.format(curdate));
//
//       	return outBuff ;
//
//	}
	//20091024 APPEND E
//	/**
//	 * Classテキスト編集
//	 * @param tblName
//	 * @return Classテキスト
//	 */
//	static public String editClass(String cmdname, String tblName, String sourceBuff){
//
//		// テーブル名をクラス名（先頭大文字、その他小文字）に変換
//		String className =  editClassName( cmdname, tblName );
//
//		String outBuff = sourceBuff.replaceAll("#TABLENAME", tblName);
//       	outBuff = outBuff.replaceAll("#CLASSNAME", className);
//       	outBuff = outBuff.replaceAll("#VOCLASSNAME",
//       			DbUtilConst.CLASS_HEADER + DbUtil.editBaseClassName(tblName));
//
//       	return outBuff ;
//
//	}
//	static public String editClass(String cmdname, String tblName, String sourceBuff, String outputClassname){
//
//		// テーブル名をクラス名（先頭大文字、その他小文字）に変換
//		String className =  editClassName( cmdname, tblName );
//		if (outputClassname != null && outputClassname.length() > 1){
//			className = outputClassname ;
//		}else{
//			className =  editClassName( cmdname, tblName );
//		}
//		String outBuff = sourceBuff.replaceAll("#TABLENAME", tblName);
//       	outBuff = outBuff.replaceAll("#CLASSNAME", className);
//       	outBuff = outBuff.replaceAll("#VOCLASSNAME",
//       			DbUtilConst.CLASS_HEADER + DbUtil.editBaseClassName(tblName));
//
//       	return outBuff ;
//
//	}
//	static public String convertDateField(List<String> dateConvList, String sourceBuff){
//		if (!dateConvList.isEmpty()){
//			//import文挿入
//			sourceBuff = sourceBuff.replaceAll("#IMPORT_TIMESTAMP", DbUtilConst.IMPORT_TIMESTAMP);
//
//			StringBuffer sbf = new StringBuffer();
//			//コメント挿入
//			sbf.append(DbUtilTypeConv.indent);
//			sbf.append(DbUtilTypeConv.indent);
//			sbf.append(DbUtilConst.DATECONVERT_COMMENT);
//			sbf.append("\r\n");
//
//			Iterator<String> dcItr = dateConvList.iterator();
//			while(dcItr.hasNext()){
//				sbf.append(indent);
//				sbf.append(indent);
//				sbf.append((String)dcItr.next());
//				sbf.append("\r\n");
//			}
//			sourceBuff = sourceBuff.replaceAll("#DATECONVERT", sbf.toString());
//		}else{
//			sourceBuff = sourceBuff.replaceAll("#IMPORT_TIMESTAMP", "");
//			sourceBuff = sourceBuff.replaceAll("#DATECONVERT", "");
//		}
//		return sourceBuff ;
//	}
}

