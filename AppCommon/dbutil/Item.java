package common.dbutil;

/**
 */
public class Item {
	String name ;
	String value ;

	public Item(){
	}
	public Item(String name, String value){
		this.name = name ;
		this.value = value ;
	}
	/**
	 * 処理。<pre>
	 * 【説明】
	 * 処理。
	 * </pre>
	 * @return
	 *
	 */
	public String getName() {
		return name;
	}

	/**
	 * 処理。<pre>
	 * 【説明】
	 * 処理。
	 * </pre>
	 * @return
	 *
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 処理。<pre>
	 * 【説明】
	 * 処理。
	 * </pre>
	 * @param string
	 *
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * 処理。<pre>
	 * 【説明】
	 * 処理。
	 * </pre>
	 * @param string
	 *
	 */
	public void setValue(String string) {
		value = string;
	}

}
