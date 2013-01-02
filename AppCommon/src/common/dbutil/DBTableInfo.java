/*
 * 作成日： 2005/07/03
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package common.dbutil;

import java.util.LinkedList;

/**
 * @author ken
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class DBTableInfo {
	private String tableName ;
	private int ColumnCount ;
	private LinkedList<DBFieldInfo> fieldInfoList ;

	/**
	 * fieldInfo 取得
	 * @return fieldInfo
	 */
	public LinkedList<DBFieldInfo> getFieldInfoList() {
		return fieldInfoList;
	}
	/**
	 * fieldInfo 設定
	 * @param fieldInfo
	 */
	public void setFieldInfoList(LinkedList<DBFieldInfo> fieldInfoList) {
		this.fieldInfoList = fieldInfoList;
	}
	/**
	 * tableName 取得
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * tableName 設定
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * columnCount 取得
	 * @return columnCount
	 */
	public int getColumnCount() {
		return ColumnCount;
	}
	/**
	 * columnCount 設定
	 * @param columnCount
	 */
	public void setColumnCount(int columnCount) {
		ColumnCount = columnCount;
	}
}
