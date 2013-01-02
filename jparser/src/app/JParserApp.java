package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import vo.Token;

import common.JParserConst;
import common.util.FileUtil;
import common.util.LogTrace;

public class JParserApp {
	private boolean exec(String srcpath){
		//ファイル選択
		JFileChooser fc = new JFileChooser(srcpath);
		fc.setFileFilter(new FileNameExtensionFilter("*.java", "java"));    //

		if (fc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
			return false ;
		}
		File file = fc.getSelectedFile();

		ArrayList<String> sourceList = new ArrayList<String>();
		try {
//			String readBuff = FileUtil.loadText(file.getAbsolutePath()) ;
			FileUtil.loadTextToArray(file.getAbsolutePath(), sourceList, "UTF-8");

			//字句解析
			ArrayList<Token> tknlist = JLexer.lex(sourceList);

			String lineBuff = "" ;

			Iterator itr = tknlist.iterator();
			while(itr.hasNext()){
				Token tkn = (Token)itr.next();

				if (JParserConst.TKN_TYPE_LINEEND == tkn.getTknType()){
					LogTrace.logout(5, lineBuff);
					lineBuff = "";
				}else{
					lineBuff += tkn.getTknValue();
				}
			}

			//意味解析
			JParser.parse(tknlist);



		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return true ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		JParserApp app = new JParserApp();

		String srcpath = "C:\\eclipse\\pleiades-e3.5-1\\workspace\\jparser\\test2\\app";

		String logpath = "C:\\eclipse\\pleiades-e3.5-1\\workspace\\jparser\\log" ;
		LogTrace.setLogpath(logpath);
		LogTrace.setModuleNameFlg(false);
		LogTrace.setTmstampFlg(false);

		app.exec(srcpath);
	}

}
