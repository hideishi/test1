package common.msg;

import java.util.ArrayList;
import java.util.Iterator;

import common.msg.*;
import common.exception.*;


/**
 * タイトル: メッセージユーティリティクラス<br>
 * 説明　　: 定義ファイルからのメッセージ読込み及びマップからのメッセージ取得を行う。
 * NAME: 石原英幸
 */
public class MsgUtil /*implements OssInterface*/ {
//    static final int MSGID_LEN = 12;
//    static final int MSGNO_LEN = MsgConst.MSGNO_FORM.length();
//    static final String MSGSEPA = "=";
    static final String mark = "@@";

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * 何もしない。
	 * </pre>
	 *
	 */
    private MsgUtil() {
    }

	/**
	 * インスタンス取得。<pre>
	 * 【説明】
	 * インスタンス取得処理。
	 * </pre>
	 * @return　メッセージクラスのインスタンス
	 *
	 */
//    static public MsgUtil getInstance() {
//        if (utl == null) {
//            return new MsgUtil();
//        } else {
//            return utl;
//        }
//    }
	/**
	 * メッセージ取得。<pre>
	 * 【説明】
	 * 指定されたメッセージIDに対応したメッセージの取得及び付加情報の編集を行う。
	 * </pre>
	 * @param msgid　メッセージID
	 * @param addList　付加情報
	 * @return
	 *
	 */
    static public String getMsg(String msgid, String[] addmsg) {
        String src = "";

        int cnt = MsgConst.msgmap.length ;
        String tmp1 = (String)MsgConst.msgmap[6].get("ILLEGAL_ATTR_CLASS_METHODNAME");
        String tmp2 = (String)MsgConst.msgmap[7].get("ILLEGAL_URL_FORM");
        String tmp3 = (String)MsgConst.msgmap[8].get("ILLEGAL_FILE_IO");

//        Integer n = numbers.get("two");

        //メーッセージを取得
        for(int i=0; i < MsgConst.msgmap.length; i++){
        	src = (String)MsgConst.msgmap[i].get(msgid);
        	if (src != null){
        		break;
        	}
        }

        if (src == null) {
            //当該IDのメッセージが無い
            return src;
        }

        if (addmsg == null) {
            return src;
        }
		if (addmsg.length == 0){
			return src;
		}
        StringBuffer sbf = new StringBuffer(src);

        for(int i=0; i < addmsg.length ; i++) {
            int idx = sbf.toString().indexOf(mark);

            if (idx == -1) {
                break; //マークが無いので終了
            }
            String addtxt = addmsg[i] ;
            if (addtxt.length() == 0){
            	addtxt = "[空文字]";
            }
			sbf = sbf.replace(idx, idx + mark.length(), addtxt);
        }
		String txt = sbf.toString();

//		// 末尾の"@@" は、取り除く
//		if (txt.endsWith(mark)){
//			txt.replace(mark,  "");
//		}

        return txt;
    }

    /**
     * メッセージテキスト取得。
     * <pre>
     * 【説明】
     * メッセージ先頭のメッセージタイプをスキップし、メッセージテキスト（メッセージ本文のみ）を
     * 取得する。
     * </pre>
     *
     * @param msgno メッセージ番号
     * @param addList 付加情報
     *
     * @return　メッセージテキスト
     */
//    public String getMsgText(String msgid, ArrayList addList)
//        throws OtherAppException {
//        String src = getMsg(msgid, addList);
//
//        if (src == null) {
//            //当該IDのメッセージが無い
//            return src;
//        }
//
//        //メッセージ先頭のメッセージタイプをスキップする
//        String msg = src.substring(MsgConst.MSG_TYPE_LEN + 1, src.length());
//
//        return msg;
//    }
}
