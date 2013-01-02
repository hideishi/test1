package common.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author hide
 *
 */
public class LogTrace {
	public static final int FATAL_LVL 	= 1;
	public static final int ERRORLVL 	= FATAL_LVL + 1;
	public static final int DEBUG_LVL 	= ERRORLVL + 1;
	public static final int WARN_LVL 	= DEBUG_LVL + 1;
	public static final int INFO_LVL 	= WARN_LVL + 1;

	/** 出力カテゴリリスト*/
	private static ArrayList<LogCategory> categoryList = new ArrayList<LogCategory>();

	/** 出力基準レベル*/
	private static int baseLevel = DEBUG_LVL ;

	/** ログファイル フォルダ名*/
	private static String logpath = ".";

	/** ログファイル名*/
	private static String logFilename = "trace.log";

	/** ログファイル フルパス*/
	private static String logFullpath = logpath + "\\" + logFilename ;

	/** 文字エンコード*/
	private static String encode = "UTF-8";

	/** ログメッセージ*/
	private static String logmsg ;

	/** タイムスタンプ出力フラグ*/
	private static boolean tmstampFlg = true ;

	/** モジュール名出力フラグ*/
	private static boolean moduleNameFlg = true ;

	/** コンソール出力フラグ*/
	private static boolean consoleOutFlg = true ;
	//
	// メソッド定義
	//
	/**
	 * ログファイル出力フォルダ設定
	 * @param _logpath ログファイル出力フォルダ
	 */
	static public void setLogpath(String _logpath){
		logpath = _logpath ;
		logFullpath = logpath + "\\" + logFilename ;
	}
	/** ログファイル名設定*/
	static public void setLogFilename(String _logFilename){
		logFilename = _logFilename ;
		logFullpath = logpath + "\\" + logFilename ;
	}
	/** 文字エンコード設定.
	 * @param enc 文字エンコード UTF-8, Shift_JISなど
	 */
	static public void setEncode(String enc){
		encode = enc ;
	}
	/**
	 * 出力基準レベル設定
	 * @param lvl 出力基準レベル
	 */
	static public void setLevel(int lvl){
		baseLevel = lvl ;
	}
	/** ログメッセージ設定.
	 * @param msg ログメッセージ
	 */
	static public void setLogmsg(String msg){
		logmsg = msg ;
	}
	/** タイムスタンプ出力フラグ設定.
	 * @param _tmstampFlg タイムスタンプ出力フラグ
	 */
	static public void setTmstampFlg(boolean _tmstampFlg ){
		tmstampFlg = _tmstampFlg ;
	}
	/**
	 * モジュール名出力フラグ設定.
	 * @param _moduleNameFlg モジュール名出力フラグ
	 */
	static public void setModuleNameFlg(boolean _moduleNameFlg ){
		moduleNameFlg = _moduleNameFlg ;
	}
	/**
	 * カテゴリリスト取得
	 * @return
	 */
	 static public ArrayList<LogCategory> getCategoryList(){

		return categoryList ;
	}

	/**
	 * ログメッセージ出力
	 * @param lvl 出力レベル
	 */
	static public void logout(int lvl){

		logout(lvl, logmsg);
	}
	/**
	 * ログメッセージ出力
	 * @param lvl 出力レベル
	 */
	static public void logout(String category,int lvl){

		Iterator itr = categoryList.iterator();
		while(itr.hasNext()){
			LogCategory logCategory = (LogCategory)itr.next();
			if (logCategory.getCategory().equals(category) && logCategory.isActive()){
				//ログ出力
				logout(lvl, logmsg);
			}
		}
	}
	/**
	 * ログメッセージ出力
	 * @param category ログカテゴリ
	 * @param lvl 出力レベル
	 * @param buff ログメッセージ
	 */
	static public void logout(String category, int lvl, String buff){
		Iterator itr = categoryList.iterator();
		while(itr.hasNext()){
			LogCategory logCategory = (LogCategory)itr.next();
			if (logCategory.getCategory().equals(category) && logCategory.isActive()){
				//ログ出力
				logout(lvl, buff);

			}
		}

	}
	/**
	 * ログメッセージ出力
	 * @param lvl 出力レベル
	 * @param buff ログメッセージ
	 */
	static public void logout(int lvl, String buff){

		if (lvl > baseLevel){
			return ;
		}
		//タイムスタンプ編集
	    String tmstamp = "";
	    if (tmstampFlg){
		    Date curdate = new Date();
		    //引数に表示フォーマットを指定して    //SimpleDateFormatオブジェクトを生成しています。
		    SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss.SSS]");
		    tmstamp = sdf.format(curdate);
	    }
	    //クラス名、メソッド名編集
	    String moduleInfo = "" ;
	    if (moduleNameFlg){
		    Throwable t = new Throwable();
		    StackTraceElement[] ste = t.getStackTrace();

		    String className = "";
		    String methodName = "";
		    String lineNumber = "";

		    for(int i=1 ; i < 3; i++){
			    className = ste[i].getClassName();
			    methodName = ste[i].getMethodName();
			    lineNumber = String.valueOf(ste[i].getLineNumber());

			    if (!className.equals(LogTrace.class.getName())){
			    	break;
			    }
		    }
		    moduleInfo = "[" + className + "::" + methodName + ":"+ lineNumber + "]";
	    }
	    //コンソール出力
	    if (consoleOutFlg){
	    	System.out.println(tmstamp + moduleInfo + buff);
	    }

		saveText(logFullpath, tmstamp + moduleInfo + buff);

	}
	/**
	 *
	 * @param lvl トレースレベル
	 * @param buff ログテキスト
	 * @param ex 例外オブジェクト
	 */
	static public void logout(int lvl, String buff, Exception ex){

		if (lvl > baseLevel){
			return ;
		}
		//タイムスタンプ編集
	    String tmstamp = "";
	    if (tmstampFlg){
		    Date curdate = new Date();
		    //引数に表示フォーマットを指定して    //SimpleDateFormatオブジェクトを生成しています。
		    SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss.SSS]");
		    tmstamp = sdf.format(curdate);
	    }
	    //クラス名、メソッド名編集
	    String moduleInfo = "" ;
	    if (moduleNameFlg){
		    Throwable t = new Throwable();
		    StackTraceElement[] ste = t.getStackTrace();

		    String className = "";
		    String methodName = "";

		    for(int i=1 ; i < 3; i++){
			    className = ste[i].getClassName();
			    methodName = ste[i].getMethodName();
			    if (!className.equals(LogTrace.class.getName())){
			    	break;
			    }
		    }
		    moduleInfo = "[" + className + "::" + methodName + "]";
	    }
	    String exinfo = "";
	    if (ex != null){
	    	exinfo = editTraceInfo(ex);
	    	//例外のスタックトレースを画面出力
	    	ex.printStackTrace();
	    }
		saveText(logFullpath, tmstamp + moduleInfo + buff + exinfo);

	}
	/**
	 * 例外のスタックトレース編集
	 * @param ex 例外オブジェクト
	 * @return 例外のスタックトレース
	 */
	static private String editTraceInfo(Exception ex){
		StringBuilder sb = new StringBuilder();

		//Throwable[] th = ex.getSuppressed();
		Throwable th = (Throwable)ex;
		String detailMessage = th.getMessage();

		String exClassName = ex.getClass().getName() ;
		sb.append("\r\n\t").append(exClassName).append(" ").append(detailMessage);

		StackTraceElement[] elems = ex.getStackTrace();
		int idx = elems.length ;

		for(int i=0; i < elems.length ;i++){
			sb.append("\r\n\t");
			sb.append(elems[i].getClassName());
			sb.append(".");
			sb.append(elems[i].getMethodName());
			sb.append("(");
			String filename = elems[i].getFileName();
			int lineNumber = elems[i].getLineNumber();

			if (filename == null){
				sb.append("unknown Source");
			}else{
				sb.append(filename ).append(":").append(lineNumber);
			}
			sb.append(")");
		}
		return sb.toString();
	}
	/**
	 * テキストファイル保存
	 * @param fullpath
	 * @param buff
	 *
	 */
	static private void saveText(String fullpath, String buff) {

		FileOutputStream fos = null;
		OutputStreamWriter osr = null ;
		BufferedWriter bw = null ;

		try {
			//追加書き込み
			fos = new FileOutputStream(fullpath, true);
			osr = new OutputStreamWriter(fos, encode);
			bw = new BufferedWriter(osr);

			bw.append(buff);
			bw.append(System.getProperty("line.separator"));


		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		finally{
			try {
				bw.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				osr.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return ;
	}

}
