/*
 * 作成日: 2004/08/08
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author ken
 *
 *         この生成されたコメントの挿入されるテンプレートを変更するため ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */

public class FileUtil {
	public static void readCsv(String fpath, String encode, List<Object> rsltList) throws IOException {


			FileInputStream fis = new FileInputStream(fpath); // CSVデータファイル
//			InputStreamReader isr = new InputStreamReader(fis, "Shift_JIS");
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

	/**
	 *
	 * @param path
	 * @param buff
	 * @return
	 */
	static public void saveText(String path, String buff) {

		FileOutputStream fos = null;
		OutputStreamWriter osr = null;
		BufferedWriter bw = null;

		try {
			fos = new FileOutputStream(path);
			osr = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osr);

			bw.append(buff);

		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		} finally {
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
	static public void saveText(String path, String buff, boolean append, String encode) {

		FileOutputStream fos = null;
		OutputStreamWriter osr = null;
		BufferedWriter bw = null;

		try {
			fos = new FileOutputStream(path, append);
			osr = new OutputStreamWriter(fos,encode);
			bw = new BufferedWriter(osr);

			bw.append(buff);

		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		} finally {
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

	/**
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	static public String loadText(String path) throws IOException {
		String buff = null;

		FileInputStream fis = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader in = new BufferedReader(isr);

		StringBuffer sbf = new StringBuffer("");

		while ((buff = in.readLine()) != null) {
			sbf.append(buff);
			sbf.append("\r\n");
		}

		buff = sbf.toString();

		return buff;
	}

	/**
	 *
	 * @param path
	 * @param list
	 *            テキストファイルの内容（リスト形式）
	 * @throws IOException
	 */
	static public void loadTextToArray(String path, List<String> list)
			throws IOException {

		String line = "";

		FileInputStream fis = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader in = new BufferedReader(isr);

		StringBuffer sbf = new StringBuffer("");

		while ((line = in.readLine()) != null) {
			list.add(line);
		}
		if ((line != null) && (line.length() > 0)) {
			list.add(line);
		}
	}
	static public void loadTextToArray(String path, List<String> list, String encode)
			throws IOException {

		String line = "";

		FileInputStream fis = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fis, encode);
		BufferedReader in = new BufferedReader(isr);

		StringBuffer sbf = new StringBuffer("");

		while ((line = in.readLine()) != null) {
			list.add(line);
		}
		if ((line != null) && (line.length() > 0)) {
			list.add(line);
		}
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	static public byte[] loadData(String path) {
		byte[] bytes = null;

		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader in = new BufferedReader(isr);

			String buff = null;
			StringBuffer sbf = new StringBuffer("");

			while ((buff = in.readLine()) != null) {
				sbf.append(buff);
			}

			buff = sbf.toString();
			bytes = buff.getBytes();
		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

		return bytes;
	}

	/**
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	static public String loadText(String path, String encode) throws IOException {
		String buff = null;

		FileInputStream fis = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fis, encode);
		BufferedReader in = new BufferedReader(isr);

		StringBuffer sbf = new StringBuffer("");

		while ((buff = in.readLine()) != null) {
			sbf.append(buff);
			sbf.append("\r\n");
		}

		buff = sbf.toString();

		return buff;
	}

}
