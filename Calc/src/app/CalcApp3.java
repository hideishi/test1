package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import common.JParserConst;
import common.util.LogTrace;

/**
 * 単純な加減算のみならばこれでよい
 * @author yuki
 *
 */
public class CalcApp3 {


	void exec(){
		LogTrace.logout(3, "*** exec start ***");

		CalcParser parser = new CalcParser();
		TestdataMaker tdm = new TestdataMaker();
//		ArrayList<Token> tknlist = new ArrayList();

//		{
//			ArrayList<Token> tknlist = tdm.make(1);
//			parser.parse(tknlist);
//		}
		{
			ArrayList<Token> tknlist = tdm.make(4);
			parser.parse(tknlist);
		}


		LogTrace.logout(3, "*** exec end ***");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		String logpath = "log" ;
		LogTrace.setLogpath(logpath);
		LogTrace.setModuleNameFlg(true);
		LogTrace.setTmstampFlg(false);

		CalcApp3 app = new CalcApp3();
		app.exec();


	}

}
