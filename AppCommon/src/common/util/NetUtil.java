package common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFileChooser;
import javax.swing.plaf.basic.BasicFileChooserUI;

import common.msg.MsgUtil;
import common.util.LogTrace;

public class NetUtil {
	static private int readBlocksize = 1024 ;
	static private String logtxt = "";

	/**
	 * 読み込み単位バイトサイズを設定
	 * @param blockSize 読み込み単位バイトサイズ
	 */
	static public void setReadBlocksize(int blockSize){
		readBlocksize = blockSize ;
	}
	/**
	 * 読み込み単位バイトサイズを取得
	 * @return 読み込み単位バイトサイズ
	 */
	static public int getReadBlocksize(){
		return readBlocksize ;
	}
	/**
	 *
	 */
	static public void queryResponse(String inputUrl, String dstpath){
		// TODO 自動生成されたメソッド・スタブ
		HttpURLConnection urlconn=null;
		BufferedReader br = null;
		try{
			//指定したURLのレスポンスを取得

//			String filename = "index.php";
//			String path = "http://www.isdsys.biz/" + filename ;
//			URL url = new URL(path);

			URL url = new URL(inputUrl);
			urlconn = (HttpURLConnection)url.openConnection();
			urlconn.connect();

			//encode指定を取得　例．text/html;charset=EUC-JP
			// 文字コードを得る
			String encode = urlconn.getContentType();
			if (encode == null){
				encode="Shift_JIS";
			}else{
				int idx = encode.indexOf("charset");

				if (idx == -1){
					encode="Shift_JIS";
				}else{
					String[] tmp1 = encode.split("=");
					String rslt ;

					rslt= tmp1[1];

					if (rslt.indexOf("\"") >= 0){
						String[] tmp2 = rslt.split("\"");
						rslt = tmp2[1];
					}else
						if (encode.indexOf("\'") >= 0){
							String[] tmp2 = rslt.split("\'");
							rslt = tmp2[1];
						}
					encode = rslt ;


				}
			}
			InputStreamReader ir = new InputStreamReader(urlconn.getInputStream(), encode);
			br = new BufferedReader(ir);
			String line;
			StringBuilder sbf = new StringBuilder();

			while((line = br.readLine()) != null){
				//レスポンス出力
				System.out.println(line);
				sbf.append(line.trim());
				sbf.append("\r\n");
			}
			//ファイル保存
			String rsppath = dstpath ;
			FileUtil.saveText(rsppath, sbf.toString(), false, encode);



		}
		catch(MalformedURLException e){
			e.printStackTrace();
			return ;
		}
		catch(IOException ex){
			ex.printStackTrace();
			return;
		}
		finally{
			try{
				if (br != null){
					br.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();

			}
			if (urlconn != null){
				urlconn.disconnect();
			}
		}
	}

	/**
	 * srcDirUrl、srcFilenameで指定されたネット上のファイルをダウンロードする
	 * @param srcDirUrl ダウンロード対象ファイルのディレクトリURL　　末尾にファイルセパレータは含まない
	 * @param srcFilename ダウンロード対象のファイル名
	 * @param saveDir 保存するディレクトリ
	 *            　
	 */
	static public void downloadMng(String srcDirUrl,
			String srcFilename,
			String saveDir)
	{

		String srcFilepath = srcDirUrl + "/" + srcFilename;

		// 保存するファイルを指定
		JFileChooser fc = null ;
		if ((saveDir != null) && (saveDir.length() > 0)){
			//保存ディレクトリ指定がある場合
			fc = new JFileChooser(saveDir);
		}else{
			fc = new JFileChooser();
		}
		BasicFileChooserUI ui = (BasicFileChooserUI) fc.getUI();
		ui.setFileName(srcFilename);

		int selected = fc.showSaveDialog(null);

		if (selected == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			String dstFilepath = file.getAbsolutePath(); // "c:\\download\\star_1.png";

			download(srcFilepath, dstFilepath);
		}
	}
	/**
	 *
	 * @param srcFilepath
	 * @param dstFilepath
	 */
	static private void download(String srcFilepath, String dstFilepath) {
		URL url = null;
		URLConnection conn = null;
		InputStream in = null;
		FileOutputStream out = null;

		try {
			url = new URL(srcFilepath);
			conn = url.openConnection();

			in = conn.getInputStream();
			File file = new File(dstFilepath); // 保存先

			out = new FileOutputStream(file, false);
			byte[] bytes = new byte[readBlocksize];
			// int writeLen = 0 ;
			while (true) {
				// int ret = in.read(bytes);
				int readLen = in.read(bytes, 0, bytes.length);
				if (readLen <= 0) {
					break;
				}
				out.write(bytes, 0, readLen);
				// writeLen += readLen ;
			}
		} catch (MalformedURLException e) {
			// TODO URLの書式が無効です。
			String[] addmsg = new String[1];
			addmsg[0] = srcFilepath;
			logtxt = MsgUtil.getMsg("ILLEGAL_URL_FORM", addmsg);

			LogTrace.logout(3, logtxt, e);


		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			logtxt = MsgUtil.getMsg("ILLEGAL_FILE_IO", null);
			LogTrace.logout(3, logtxt, e);


		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
