/*
 * 作成日： 2005/07/03
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package common.dbutil;

/**
 * @author ken
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class DBFieldInfo {
	private String columnName ;
	private String columnTypeName ;
	private int length ;
	private int precision ;
	private int scale ;
	private boolean primaryKey ;
	private boolean unique ;
	private boolean notnull ;
	private String comment ;
	private String indexName ="";

	public DBFieldInfo(){
	}
	/**
	 * notnull 取得
	 * @return notnull
	 */
	public boolean isNotnull() {
		return notnull;
	}
	/**
	 * notnull 設定
	 * @param notnull
	 */
	public void setNotnull(boolean notnull) {
		this.notnull = notnull;
	}
	/**
	 * unique 取得
	 * @return unique
	 */
	public boolean isUnique() {
		return unique;
	}
	/**
	 * unique 設定
	 * @param unique
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	/**
	 * primaryKey 取得
	 * @return primaryKey
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	/**
	 * primaryKey 設定
	 * @param primaryKey
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	/**
	 * columnName 取得
	 * @return columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * columnName 設定
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * columnTypeName 取得
	 * @return columnTypeName
	 */
	public String getColumnTypeName() {
		return columnTypeName;
	}
	/**
	 * columnTypeName 設定
	 * @param columnTypeName
	 */
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	/**
	 * length 取得
	 * @return length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * length 設定
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * precision 取得
	 * @return precision
	 */
	public int getPrecision() {
		return precision;
	}
	/**
	 * precision 設定
	 * @param precision
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	/**
	 * scale 取得
	 * @return scale
	 */
	public int getScale() {
		return scale;
	}
	/**
	 * scale 設定
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

}
