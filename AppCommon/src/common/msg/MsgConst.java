package common.msg;

import java.util.Hashtable;

/**
 * タイトル: メッセージ定義クラス<br>
 * 説明　　: メッセージ文字列をを定義する。
 *
 *
 */
public class MsgConst {

//	static public final String MSGNO_FORM                 = "000";
//	/** メッセージタイプの文字列長 */
//	static public final int MSG_TYPE_LEN 				= 3 ;
//	/** メッセージタイプ 通知 */
//	static public final String MSG_TYPE_INFOMATION		= "INF";
//	/** メッセージタイプ 警告 */
//	static public final String MSG_TYPE_WARNING			= "WAR";
//	/** メッセージタイプ エラー */
//	static public final String MSG_TYPE_ERROR			= "ERR";
//	/** メッセージタイプ 致命的エラー */
//	static public final String MSG_TYPE_FATAL			= "FTL";
//	/** メッセージタイプ リトライ */
//	static public final String MSG_TYPE_RETRY			= "RTY";
//	/** メッセージタイプ 確認 */
//	static public final String MSG_TYPE_CONFIRM			= "CNF";

	/** メッセージキー*/
	static public final String MSG_ATTR_NOTEXIST = "ATTR_NOTEXIST";
	static public final String MSG_ILLEGAL_ATTR = "ILLEGAL_ATTR";
	static public final String MSG_METHOD_NOTFOUND = "METHOD_NOTFOUND";
	static public final String MSG_ATTR_NOTEXIST_CLASSNAME = "ATTR_NOTEXIST_CLASSNAME";
	static public final String MSG_ILLEGAL_ATTR_CLASSNAME = "ILLEGAL_ATTR_CLASSNAME";
	static public final String MSG_ATTR_NOTEXIST_CLASS_METHODNAME = "ATTR_NOTEXIST_CLASS_METHODNAME";
	static public final String MSG_ILLEGAL_ATTR_CLASS_METHODNAME = "ILLEGAL_ATTR_CLASS_METHODNAME";

	static public final String MSG_ILLEGAL_URL_FORM = "ILLEGAL_URL_FORM";
	static public final String MSG_ILLEGAL_FILE_IO = "ILLEGAL_FILE_IO";

	/** メッセージ文字列定義 */
	public final static Hashtable[] msgmap = {
          new Hashtable<String, String>() {{put
        	  (MSG_ATTR_NOTEXIST, "[@@]タグの[@@]属性が未定義です。");
        	  }},
              new Hashtable<String, String>() {{put
            	  (MSG_ILLEGAL_ATTR, "[@@]タグの[@@]属性が不正です。値：@@");
              }},
              new Hashtable<String, String>() {{put
            	  (MSG_METHOD_NOTFOUND, "メソッド情報の検索に失敗しました。クラス名：@@、メソッド名：@@");
              }},
              new Hashtable<String, String>() {{put
            	  (MSG_ATTR_NOTEXIST_CLASSNAME, "[@@]タグの[@@]属性が未定義です。クラス名：@@");
              }},
              new Hashtable<String, String>() {{put
            	  (MSG_ILLEGAL_ATTR_CLASSNAME, "[@@]タグの[@@]属性が不正です。クラス名：@@、値：@@");
              }},
              new Hashtable<String, String>() {{put
            	  (MSG_ATTR_NOTEXIST_CLASS_METHODNAME, "[@@]タグの[@@]属性が未定義です。クラス名：@@、メソッド名：@@");
              }},
              new Hashtable<String, String>() {{put
            	  (MSG_ILLEGAL_ATTR_CLASS_METHODNAME, "[@@]タグの[@@]属性が不正です。クラス名：@@、メソッド名：@@、値：@@");
              }},
              new Hashtable<String, String>() {{put
            	  (MSG_ILLEGAL_URL_FORM, "URLの書式が無効です。@@");
              }},
              new Hashtable<String, String>() {{put
            	  (MSG_ILLEGAL_FILE_IO, "ファイル入出力障害。");
              }},
	};


}
