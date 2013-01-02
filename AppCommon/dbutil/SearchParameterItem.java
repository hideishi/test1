/*
 * 作成日： 2005/08/07
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
public class SearchParameterItem {
	/** 検索項目名 */
	private String name ;
	/** 検索項目の実体リンク */
	private String link ;

	/**
	 * link 取得
	 * @return link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * link 設定
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * name 取得
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * name 設定
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
