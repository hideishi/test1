package common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * タイトル: ユーティリティクラス<br>
 * 説明　　: ユーティリティクラス。
 *
 */
public class StringUtil {

	static public boolean isNumericText(String txt){
		String tbl = "0123456789";

		if (txt.length() == 0){
			return false ;
		}
		for(int i=0; i < txt.length(); i++){
			String chr = txt.substring(i, i+1);
			if (tbl.indexOf(chr) < 0){
				return false ;
			}
		}
		return true ;
	}
	static public boolean isAlphaText(String txt){
		String tbl = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		if (txt.length() == 0){
			return false ;
		}
		for(int i=0; i < txt.length(); i++){
			String chr = txt.substring(i, i+1);
			if (tbl.indexOf(chr) < 0){
				return false ;
			}
		}
		return true ;
	}

	static public String capitalize(java.lang.String str){
		if ((str == null) || (str.length()==0)){
			throw new IllegalArgumentException("引数が不正です。");
		}
		return str.substring(0,1).toUpperCase() + str.substring(1, str.length());
	}
	static public String uncapitalize(java.lang.String str){
		if ((str == null) || (str.length()==0)){
			throw new IllegalArgumentException("引数が不正です。");
		}
		return str.substring(0,1).toLowerCase() + str.substring(1, str.length());
	}

	/**
	 * 媒体入出力電文送信時刻の時刻文字列取得。<pre>
	 * 【説明】
	 * 媒体入出力電文送信時刻の時刻文字列を取得する。
	 * </pre>
	 * @return　時刻文字列
	 */
	static public String getSendTime(){
		//媒体入出力電文送信時刻の時刻文字列
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmss");
		return sdf.format(dt);
	}
	/**
	 * 電文フォルダ名の時刻文字列取得。<pre>
	 * 【説明】
	 * 電文フォルダ名の時刻文字列を取得する。
	 * </pre>
	 * @return　時刻文字列
	 *
	 */
	static public String getFolderTime(){
		//電文フォルダ名の時刻文字列
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat ("yyMMddHH");
		return sdf.format(dt);
	}
	/**
	 * 電文リストファイル名の時刻文字列取得。<pre>
	 * 【説明】
	 * 電文リストファイル名の時刻文字列を取得する。
	 * </pre>
	 * @return　時刻文字列
	 *
	 */
	static public String getListFileTime(){
		//電文リストファイル名の時刻文字列
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat ("yyMMddHHmmss");
		return sdf.format(dt);
	}

	/**
	 * 文字列分割処理。<pre>
	 * 【説明】
	 * 指定された文字列を指定された単位文字列長で分割する。
	 * </pre>
	 * @param unitLength 単位文字列長
	 * @param text 分割対象文字列
	 * @return 文字列リスト
	 */
	static public LinkedList splitString(int unitLength, String text){
		if (unitLength <= 0){
			throw new IllegalArgumentException("\"unitLength\" is illegal. \"unitLength\" is " + unitLength + ".");
		}
		if (text == null){
			throw new IllegalArgumentException("\"text\" is null.");
		}
		if (text.length() <= 0){
			throw new IllegalArgumentException("\"text\" is empty String.");
		}
		//sendMaxSize		readDataList
		int div = text.length() / unitLength +
				(((text.length() % unitLength) == 0) ? 0 : 1) ;

		int pos = 0;
		LinkedList dataList = new LinkedList();
		for(int i=0 ; i < div; i++){
			String s = "" ;
			if (text.length() < (pos + unitLength)){
				s = text.substring(pos, text.length());
			}else{
				s = text.substring(pos, pos + unitLength);
			}
			dataList.add(s);
			pos += unitLength ;
		}
		return dataList ;
	}
	/**
	 * 同一名重複チェック。<pre>
	 * 【説明】
	 * リストのテキストに重複があるかチェックしインデックスを戻す。
	 * </pre>
	 * @param srcList　文字列リスト
	 * @return　インデックス
	 *
	 */
	static public LinkedList isExistsSameName(ArrayList<String> srcList){
		LinkedList rtnList = new LinkedList();

		if (srcList.size() < 2)
			return rtnList ;


		ArrayList<String> dstList = new ArrayList<String>();
		dstList = srcList ;

		for(int i=0; i < srcList.size() - 1; i++){
			String src = (String)srcList.get(i);
			/* チェック */
			for(int j=i+1; j < srcList.size(); j++){
				String dst = (String)dstList.get(j);
				if (src.equals(dst)){
					rtnList.add(new Integer(i)) ;
					rtnList.add(new Integer(j)) ;
					break;
				}
			}
		}
		return rtnList ;
	}
	/**
	 * 文字列=>Date変換。<pre>
	 * 【説明】
	 * 指定された日付文字列をDate型に変換する。
	 * </pre>
	 * @param date 日付文字列(形式は、"YYYYMMDD"で、年＝2000年以上、月＝01～12）
	 * @return Dateオブジェクト
	 *
	 */
	static public Date stringToDate(String date){

		if (!isDateString(date)){
			return null ;
		}
		int year 	= (new Integer(date.substring(0,4))).intValue();
		int month	= (new Integer(date.substring(4,6))).intValue();
		int day  	= (new Integer(date.substring(6,8))).intValue();


		Calendar cal = Calendar.getInstance(new Locale("ja", "JAPAN"));
		cal.set(year, month-1, day, 0, 0, 0);
		Date dt = cal.getTime();

		return dt;

	}
	/**
	 * 日付文字列妥当性判定。<pre>
	 * 【説明】
	 * 指定された日付文字列が妥当か判定する。
	 * </pre>
	 * @param date 日付文字列(形式は、"YYYYMMDD"で、年＝2000年以上、月＝01～12）
	 * @return 判定結果
	 *
	 */
	static private boolean isDateString(String date){
		final int[] dayArray = {
			31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
		};

		if (date.length() < 8){
			throw new IllegalArgumentException("日付文字列長が不正。") ;
		}

		int year 	= (new Integer(date.substring(0,4))).intValue();
		int month	= (new Integer(date.substring(4,6))).intValue();
		int day  	= (new Integer(date.substring(6,8))).intValue();

		if (year < 2000){
			throw new IllegalArgumentException("年の値が不正（日付＝" + date + "）。") ;
		}
		if ((month <= 0) || (12 < month)){
			throw new IllegalArgumentException("月の値が不正（日付＝" + date + "）。") ;
		}
		if (day <= 0){
			throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
		}
		if (month != 2){
			if (day > dayArray[month - 1]){
				throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
			}
		}else{
			if (isUruYear(year)){
				if (day > 29){
					throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
				}
			}else{
				if (day > 28){
					throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
				}
			}
		}
		return true ;
	}
	/**
	 * うるう年判定。<pre>
	 * 【説明】
	 * 指定された年のうるう年判定を行う。
	 * </pre>
	 * @param year　2000年以上とする
	 * @return　判定結果
	 *
	 */
	static private boolean isUruYear(int year){
		if (year < 2000){
			return false ;
		}
		if ((year%4) != 0){
			return false ;
		}
		if ((year%100) ==0){
			if ((year%400)!=0){
				return false;
			}
		}
		return true ;
	}
	/**
	 * 日付文字列形式変換（年2桁=>4桁）。<pre>
	 * 【説明】
	 * 日付文字列形式をYYMMDDから、YYYYMMDDに変換する。
	 * </pre>
	 * @param srcDate　日付文字列(形式は、"YYMMDD"で、年＝2000年以上、月＝01～12）　
	 * @return
	 *
	 */
	static public String cnvDateYYMMDDtoYYYYMMDD(String srcDate){
		if (srcDate.length() != 6){
			throw new IllegalArgumentException("日付文字列長が不正。") ;
		}
		String dstDate = "";

		int year = (new Integer(srcDate.substring(0, 2))).intValue();
		year += 2000 ;
		dstDate = String.valueOf(year) + srcDate.substring(2, srcDate.length());

		//妥当性チェック
		stringToDate(dstDate);

		return dstDate ;
	}
	/**
	   * 半角数値文字列を全角数値文字列に変換します。
	   * @param s 変換元文字列
	   * @return 変換後文字列
	   */
	  public static String hanNumToZen(String hantxt) {
		  final String hantbl = "0123456789";
		  final String zentbl = "０１２３４５６７８９";

		  StringBuffer sbf = new StringBuffer();

		  for(int i=0; i < hantxt.length();i++){
			  int idx = hantbl.indexOf(hantxt.substring(i, i+1));
			  sbf.append(zentbl.substring(idx, idx+1));
		  }
		  return sbf.toString();
	  }
	  /**
	   * 全角スペースのtrim　を行う
	   * @param txt
	   * @return
	   */
	  public static String trimzen(String txt){
			String regex1 = "^　*";		//先頭の全角スペースが一致
		    String regex2 = "　*$";		//末尾の全角スペースが一致

		    //一挙に
		    return txt.replaceAll(regex1, "").replaceAll(regex2, "");
	  }
	  public static boolean isWhiteSpace(String txt){
		  String template = " \t";

		  if (template.indexOf(txt) == -1){
			  return false ;
		  }
		  return true ;
	  }
}
