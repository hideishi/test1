package common.util;
/**
 * タイトル: 範囲クラス。<pre>
 * 説明　　: 範囲を保持し範囲チェックを行う。
 *
 * 作成情報: DATE: 2004.04.09  NAME: 石原英幸
 * </pre>
 */

public class Range {
	long	min ;
	long	max ;

	public Range(long aMin, long aMax){
		this.min = aMin ;
		this.max = aMax ;
	}
	/**
	 * 範囲チェック処理。<pre>
	 * 【説明】
	 * 範囲チェック処理。
	 * </pre>
	 * @param value　チェック値
	 * @return　チェック結果
	 *
	 */
	public boolean isValid(int value){
		boolean ret = true ;

		long n = value ;
		if ((n < min) || (max < n)){
			ret = false ;
		}

		return ret ;
	}
	/**
	 * 範囲チェック処理。<pre>
	 * 【説明】
	 * 範囲チェック処理。
	 * </pre>
	 * @param value　チェック値
	 * @return　チェック結果
	 *
	 */
	public boolean isValid(String value){
		boolean ret = true ;

		try{
			long n = (new Long(value)).longValue();
			if ((n < min) || (max < n)){
				ret = false ;
			}
		}
		catch(NumberFormatException e){
			ret = false ;
		}
		return ret ;
	}
	/**
	 * 最大値取得処理。<pre>
	 * 【説明】
	 * 最大値取得処理。
	 * </pre>
	 * @return　最大値　
	 *
	 */
	public long getMax() {
		return max;
	}
	/**
	 * 最小値取得処理。<pre>
	 * 【説明】
	 * 最小値取得処理。
	 * </pre>
	 * @return　最小値
	 *
	 */

	public long getMin() {
		return min;
	}
	/**
	 * 最大値設定処理。<pre>
	 * 【説明】
	 * 最大値設定処理。
	 * </pre>
	 * @param l　最大値
	 *
	 */

	public void setMax(long l) {
		max = l;
	}

	/**
	 * 最小値設定処理。<pre>
	 * 【説明】
	 * 最小値設定処理。
	 * </pre>
	 * @param l　最小値
	 *
	 */
	public void setMin(long l) {
		min = l;
	}

}
