package common.util;

/**
 * ログ出力カテゴリ制御用クラス<br>
 * カテゴリにはクラス名などを使用する
 * @author hide
 *
 */
public class LogCategory {
	private boolean active ;
	private String category ;

	public LogCategory(){

	}
	public LogCategory(boolean _active, String _category){
		active = _active ;
		category = _category ;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
