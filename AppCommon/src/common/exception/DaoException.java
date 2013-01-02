package common.exception;


/**
 * タイトル：データベース入出力処理例外<pre>
 * 説明　　：データベース入出力処理において発生する例外
 * 
 * 修正情報：
 * </pre>
 */
 public class DaoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param なし
	 * @return なし
	 * @throws なし
	 */
	public DaoException(){
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
	public DaoException(String aMsg){
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
	public DaoException(String aMsg, Exception aCause){
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
	public DaoException(Exception aCause){
		super(aCause);
	}
}
