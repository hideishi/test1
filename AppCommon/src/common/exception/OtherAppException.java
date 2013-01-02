package common.exception;


/**
 * タイトル：日付指定フォーマット不正例外<pre>
 * 説明　　：日付変換のフォーマットエラー時に返す例外
 * 
 * </pre>
 */
 public class OtherAppException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4148341505275939712L;

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param なし
	 * @return なし
	 * @throws なし
	 */
	public OtherAppException(){
	}

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param aMsg メッセージを指定する。
	 * @return なし
	 * @throws なし
	 */
	public OtherAppException(String aMsg){
		super(aMsg);
	}
	
	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param aMsg メッセージを指定する。
	 * @param aCause 元となった例外を指定する。
	 * @return なし
	 * @throws なし
	 */
	public OtherAppException(String aMsg, Exception aCause){
		super(aMsg, aCause);
	}
	
	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param aCause 元となった例外を指定する。
	 * @return なし
	 * @throws なし
	 */
	public OtherAppException(Exception aCause){
		super(aCause);
	}
}
