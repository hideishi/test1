package app;

import java.util.ArrayList;
import java.util.Iterator;

import common.JParserConst;
import common.util.LogTrace;

import vo.Token;

public class ExpParserApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		ExpParserApp app = new ExpParserApp();

		String logpath = "C:\\eclipse\\pleiades-e3.5-1\\workspace\\jparser\\log" ;
		LogTrace.setLogpath(logpath);
		LogTrace.setModuleNameFlg(false);
		LogTrace.setTmstampFlg(false);
		LogTrace.setLogFilename("exptrace.log");

		String exp = "A + B * (C+1)";

		ExpParser.parse(exp);
	}

}
