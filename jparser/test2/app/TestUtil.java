package app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import base.TestUtilBase;

public class TestUtil extends base.TestUtilBase implements base.intf.TestUtilIf, base.intf.TestUtilIf2{

	private int sts  ;
	static private String[] sample ;
	static private String sample2[] ;
	static final int STS_AAAA = 1;
	static final int STS_BBB = STS_AAAA + 1;

	public static void readCsv(String fpath, String encode,
			List<Object> rsltList) throws IOException {

		FileInputStream fis = new FileInputStream(fpath); // CSVデータファイル
		// InputStreamReader isr = new InputStreamReader(fis, "Shift_JIS");
		InputStreamReader isr = new InputStreamReader(fis, encode);
		BufferedReader br = new BufferedReader(isr);

		// 最終行まで読み込む
		String line = "";
		while ((line = br.readLine()) != null) {

			// 1行をデータの要素に分割
			StringTokenizer st = new StringTokenizer(line, ",");

			ArrayList<String> columnList = new ArrayList();
			while (st.hasMoreTokens()) {
				// 1行の各要素をタブ区切りで表示
				String buff = st.nextToken();
				columnList.add(buff);
			}
			rsltList.add(columnList);
		}
		br.close();
		isr.close();
		fis.close();

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

}
