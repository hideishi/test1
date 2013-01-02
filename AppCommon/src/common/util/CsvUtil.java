package common.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import vo.TestData;

import common.exception.OtherAppException;


public class CsvUtil {
//	private String csvpath = "C:\\tmp\\testdata.csv";
	final private int headlines = 3;				//ヘッダ部の最低行数

	final private String TITLE_CLASS_NAME = "クラス名";
	final private String TITLE_PHYSICAL_ITEM_NAME = "物理項目名";
	final private String TITLE_DATATYPE_NAME = "データ型名";
	final private String TITLE_DATA_SECTION = "データ部";

	private String logmsg = "";
	private String entClassName = "" ;
	private ArrayList<String> colnameList = null ;
	private ArrayList<String> typenameList = null;

	/**
	 * CSVファイルを読み込み当該データクラスのリストを生成する
	 * 書式
	 * 	　ヘッダ部："データ部"の表示以前がヘッダ部
	 *	　　　第一カラムは、当該行のデータ種別を表す。
	 * 			クラス名：当該データを格納するエンティティ（テーブル）クラスの完全修飾名
	 *      	論理項目名：日本語項目名
	 *      	物理項目名：当該クラスのフィールド名
	 *      	データ型名：データ型のクラス名（String,Integer,Timestamp,Date,BigDecimal)
	 *
	 *	　データ部："データ部"の表示以降
	 *		ダブルクォーテーションで囲まれた場合は、囲まれているテキストをデータとする。
	 *	　注釈行：行先頭が、"#"の行は、注釈行。
	 *
	 * @throws OtherAppException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public void loadCsvData(String csvpath, List<Object> classNameList, List<Object> valueList) throws OtherAppException, ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {

		LogTrace.logout(3, "[CsvUtil::loadCsvData] start");

		LogTrace.logout(3, "[CsvUtil::loadCsvData] csvpath = " + csvpath);

		ArrayList rsltList = new ArrayList();

		try {
			FileUtil.readCsv(csvpath, "UTF-8", rsltList);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			LogTrace.logout(3, "CSVファイルの読み込みに失敗しました。");
			return;
		}

		if (rsltList.size() == 0) {
			LogTrace.logout(3, "CSVファイルデータが０件です");
			throw new OtherAppException("CSVファイルが空です");
		}
		if (rsltList.size() < headlines + 1) {
			logmsg = "ヘッダ部は"+ StringUtil.hanNumToZen(new Integer(headlines).toString()) + "行以上なので必ず";
			logmsg +=  StringUtil.hanNumToZen(new Integer(headlines+1).toString()) + "行以上必要です。";
			LogTrace.logout(3, logmsg);
			throw new OtherAppException(logmsg);
		}
		Iterator itr = rsltList.iterator();

		// ヘッダ部取得
		int dataStart =0;
		for (int i = 0; i < rsltList.size(); i++) {
			ArrayList headlist = (ArrayList) rsltList.get(i);
			String headTypeName = ((String) headlist.get(0)).trim();

			if (headTypeName.compareTo(TITLE_CLASS_NAME)==0){
				entClassName = ((String) headlist.get(1)).trim();
				classNameList.add(entClassName);
			}else
			if (headTypeName.compareTo(TITLE_PHYSICAL_ITEM_NAME)==0){
				// 物理項目名リスト取得
				colnameList = (ArrayList) rsltList.get(i);
			}else
				if (headTypeName.compareTo(TITLE_DATATYPE_NAME)==0){
					// データ型名リスト取得
					typenameList = (ArrayList) rsltList.get(i);
			}else
			if (headTypeName.compareTo(TITLE_DATA_SECTION)==0){
				dataStart = i + 1;
				break;
			}
		}
		// エラーメッセージ編集
		String msgitem = "";
		if (entClassName.length()==0){
			msgitem = TITLE_CLASS_NAME ;
		}
		if (colnameList == null){
			msgitem = TITLE_PHYSICAL_ITEM_NAME ;
		}
		if (typenameList == null){
			msgitem = TITLE_DATATYPE_NAME ;
		}
		if (dataStart==0){
			msgitem = TITLE_DATA_SECTION ;
		}
		if (msgitem.length() > 0){
			throw new OtherAppException(msgitem + "が見つかりません。ヘッダ部には、クラス名・物理項目名・データ型名が必要です。");
		}
		//
		// データ部取得
		for (int i = dataStart; i < rsltList.size(); i++) {

			ArrayList columnList = (ArrayList) rsltList.get(i);
			String firstItem = (String)columnList.get(0);
			if (firstItem.substring(0, 1).compareTo("#")==0){//先頭 # は、注釈行
				continue;
			}

			Object entityObj=null;
			try{
				entityObj = outpuData(columnList);
			}
			catch(OtherAppException ex){
				throw new OtherAppException("CSVファイルの"+(new Integer(i+1)).toString()+ "行目：" + ex.getMessage());
			}
			valueList.add(entityObj);
		}

		LogTrace.logout(3, "[CsvUtil::loadCsvData]testReadCsv end");

	}

	/**
	 * 行データから、当該クラスのオブジェクト生成する
	 *
	 * @param columnList
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws OtherAppException
	 */
	private Object outpuData(ArrayList columnList)
			throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, OtherAppException {

		Object[] dataArray = columnList.toArray();

		// インスタンスを作成します
		Class entityCls = null;
		entityCls = Class.forName(entClassName);
		Object entityObj = entityCls.newInstance();

		for (int i = 0; i < dataArray.length; i++) {
			String typeKeyName = (String) typenameList.get(i+1);
			String typeClassName = ClassNameConvertor.db2ToJava(typeKeyName.trim());

			String data = ((String) dataArray[i]).trim();
			if 	(
				(
					(data.substring(0,1).compareTo("\"")==0)  &&
					(data.substring(data.length() - 1, data.length()).compareTo("\"")!=0)
				)
				||
				(
						(data.substring(0,1).compareTo("\"")!=0)  &&
						(data.substring(data.length() - 1, data.length()).compareTo("\"")==0)
				)
				)
			{// double-quatation で囲まれていた場合
					throw new OtherAppException("ダブルクォテーションの対応が不正。");

			}
			if  (
					(data.substring(0,1).compareTo("\"")==0)  &&
					(data.substring(data.length() - 1, data.length()).compareTo("\"")==0)
					)
			{// double-quatation で囲まれていた場合
					data = data.substring(1, data.length() - 1);
			}

//			LogTrace.logout(3, data);

			// メソッドを取得します
			String colname = (String) colnameList.get(i+1);
			colname = colname.trim();
			String setterName = "set" + colname.substring(0, 1).toUpperCase()
					+ colname.substring(1);

			Class typecls = Class.forName(typeClassName);

			Method method = method = entityCls.getMethod(setterName,
					new Class[] { typecls });

			// setterメソッド実行
			invoke(entityObj, method, typeClassName, data);

//			if (i < dataArray.length - 1) {
//				LogTrace.logout(3, ", ");
//			}
		}
		return entityObj;
	}

//	/**
//	 * クラス名を完全修飾クラス名に変換
//	 *
//	 * @param keyname
//	 * @return
//	 */
//	private String javaClassNameConvertor(String keyname) {
//		String[] keytbl = { "String", "Integer", "Timestamp", "Date",
//				"BigDecimal","Float", "Double" };
//		String[] classNameTbl = { "java.lang.String", "java.lang.Integer",
//				"java.sql.Timestamp", "java.sql.Date", "java.math.BigDecimal",
//				"java.lang.Float", "java.lang.Double"
//				};
//
//		for (int i = 0; i < keytbl.length; i++) {
//			if (keyname.compareTo(keytbl[i]) == 0) {
//				return classNameTbl[i];
//			}
//		}
//		return "";
//	}

	/**
	 * setterメソッド実行
	 *
	 * @param entityObj
	 * @param method
	 * @param typeClassName
	 * @param data
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void invoke(Object entityObj, Method method, String typeClassName,
			String data) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		//
		// データ型によってケース分けし、setterメソッド実行する
		//
		if (typeClassName.compareTo("java.lang.String") == 0) {
			method.invoke(entityObj, new Object[] { data });
		} else if (typeClassName.compareTo("java.lang.Integer") == 0) {
			method.invoke(entityObj, new Object[] { new Integer(data) });
		} else if (typeClassName.compareTo("java.sql.Timestamp") == 0) {
			method.invoke(entityObj, new Object[] { java.sql.Timestamp
					.valueOf(data) });
		} else if (typeClassName.compareTo("java.sql.Date") == 0) {
			method.invoke(entityObj,
					new Object[] { java.sql.Date.valueOf(data) });
		} else if (typeClassName.compareTo("java.math.BigDecimal") == 0) {
			method.invoke(entityObj, new Object[] { new BigDecimal(data) });
		}

	}

}
