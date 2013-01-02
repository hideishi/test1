package app;

import java.util.ArrayList;
import java.util.Arrays;

import common.JParserConst;
import common.util.LogTrace;

public class CalcApp {

	int idx = 0;
	ArrayList<OperatorInfo> explist = new ArrayList<OperatorInfo>();
	
	void exec(ArrayList<Token> tknlist){
		LogTrace.logout(3, "*** exec start ***");

		OperatorInfo opinfoFirst = new OperatorInfo();

		int[] idxArray = new int[1];
		createFirstOperator(tknlist, idxArray, opinfoFirst);

		OperatorInfo opinfo = opinfoFirst ;
		if (tknlist.size() > 3){
			do{
				OperatorInfo opinfowk = createNextOperator(tknlist, idxArray, opinfo);
				if (opinfowk == null){
					break;
				}
				opinfo = opinfowk ;
			}while(idxArray[0] < tknlist.size() );
		}

		//確認
		Token ope = opinfo.getOpe();
		OperatorInfo opiRight = opinfo.getRight();
		OperatorInfo opiLeft = opinfo.getLeft();

		LogTrace.logout(3, "オペレータ:"+ ope.getValue());
		Token wktkn = opiRight.getOpe();
		LogTrace.logout(3, "右辺:"+ wktkn.getValue());

		//---------------------------------------------------------------------
		{
			OperatorInfo leftInfo = opiLeft ;
			do{
				 leftInfo = showOperatorInfo(leftInfo);
			}while(leftInfo != null);
		}
		//---------------------------------------------------------------------
		{
			OperatorInfo leftInfo = opiLeft ;
			do{
				 leftInfo = calc(leftInfo);
			}while(leftInfo != null);
		}


		LogTrace.logout(3, "*** exec end ***");
	}
	/**
	 * 最初のOperatorInfoを作成
	 * @param tknlist　in 式を構成するトークンリスト
	 * @param idxArray in/out トークンリスト参照時の添え字
	 * @param opinfo out 作成したオペレータ情報
	 */
	private void createFirstOperator(ArrayList<Token> tknlist, int[] idxArray, OperatorInfo opinfo){
		idx = 0 ;
		opinfo.setLeft (new OperatorInfo(tknlist.get(idx++)));
		opinfo.setOpe(tknlist.get(idx++));
		opinfo.setRight(new OperatorInfo(tknlist.get(idx++)));

		idxArray[0] = idx ;
		return  ;
	}
	/**
	 * 2番目以降のOperatorInfoを作成
	 * @param tknlist　in 式を構成するトークンリスト
	 * @param idxArray in/out トークンリスト参照時の添え字
	 * @param opinfo in ひとつ前に作成したオペレータ情報
	 */
	private OperatorInfo createNextOperator(ArrayList<Token> tknlist, int[] idxArray, OperatorInfo opinfoPre){
		Token wktkn = (Token)tknlist.get(idx);

		OperatorInfo nextOpe = null ;
		if (wktkn.getType()==JParserConst.TKN_TYPE_OPERATOR){
			nextOpe = new OperatorInfo();

			OperatorInfo right = new OperatorInfo();
			right.setOpe((Token)tknlist.get(idx + 1));

			nextOpe.setOpe((Token)tknlist.get(idx));
			nextOpe.setLeft(opinfoPre);
			nextOpe.setRight(right);
		}
		idx += 2 ;
		idxArray[0] = idx ;
		return nextOpe ;
	}
	/**
	 *
	 * @param opinfo
	 * @return
	 */
	private OperatorInfo showOperatorInfo(OperatorInfo opinfo){
		LogTrace.logout(3, "*** showOperatorInfo start ***");

		Token ope = opinfo.getOpe();
		OperatorInfo opiLeft = opinfo.getLeft();
		OperatorInfo opiRight = opinfo.getRight();

		if (opiLeft != null){
			if (ope != null){
				
				LogTrace.logout(3, "オペレータ:"+ ope.getValue());
			}
		}else{
			if (ope != null){
				LogTrace.logout(3, "左辺:"+ ope.getValue());
			}
		}
		if (opiRight != null){

			Token tkn = opiRight.getOpe();
			LogTrace.logout(3, "右辺: "+ tkn.getValue());
		}else{
			LogTrace.logout(3, "右辺: null");
		}

		LogTrace.logout(3, "*** showOperatorInfo end ***");
		return opiLeft ;
	}
	private OperatorInfo calc(OperatorInfo opinfo){
		Token ope = opinfo.getOpe();
		OperatorInfo opiLeft = opinfo.getLeft();
		OperatorInfo opiRight = opinfo.getRight();

		if (opiLeft != null){
			if (ope != null){
				LogTrace.logout(3, "オペレータ:"+ ope.getValue());
			}
		}else{
			//opiLeftがnullの場合
			if (ope != null){
				LogTrace.logout(3, "左辺:"+ ope.getValue());
			}
		}
		if (opiRight != null){

			Token tkn = opiRight.getOpe();
			LogTrace.logout(3, "右辺: "+ tkn.getValue());
		}else{
			LogTrace.logout(3, "右辺: null");
		}
		return opiLeft ;

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		String logpath = "log" ;
		LogTrace.setLogpath(logpath);
		LogTrace.setModuleNameFlg(false);
		LogTrace.setTmstampFlg(false);

		CalcApp app = new CalcApp();

		boolean[] execFlg = {true, true, true} ;
		int idx= 0 ;
		if (execFlg[idx++])
		{
			LogTrace.logout(3, "***** TestNo = "+ idx + "*****");

			Token[] tkns = new Token[3];

			for(int i=0; i < tkns.length; i++){
				tkns[i] = new Token();
			}

			tkns[0].setValue("A");
			tkns[0].setType(JParserConst.TKN_TYPE_IDENTIFIER);

			tkns[1].setValue("+");
			tkns[1].setType(JParserConst.TKN_TYPE_OPERATOR);

			tkns[2].setValue("B");
			tkns[2].setType(JParserConst.TKN_TYPE_IDENTIFIER);

			ArrayList<Token> tknlist 	= new ArrayList(Arrays.asList(tkns));

			app.exec(tknlist);
		}
		LogTrace.logout(3, "");

		if (execFlg[idx++])
		{
			LogTrace.logout(3, "***** TestNo = "+ idx + "*****");
			Token[] tkns = new Token[5];

			for(int i=0; i < tkns.length; i++){
				tkns[i] = new Token();
			}

			tkns[0].setValue("A");
			tkns[0].setType(JParserConst.TKN_TYPE_IDENTIFIER);

			tkns[1].setValue("+");
			tkns[1].setType(JParserConst.TKN_TYPE_OPERATOR);

			tkns[2].setValue("B");
			tkns[2].setType(JParserConst.TKN_TYPE_IDENTIFIER);

			tkns[3].setValue("-");
			tkns[3].setType(JParserConst.TKN_TYPE_OPERATOR);

			tkns[4].setValue("1");
			tkns[4].setType(JParserConst.TKN_TYPE_NUMERIC);

			ArrayList<Token> tknlist 	= new ArrayList(Arrays.asList(tkns));

			app.exec(tknlist);
		}

		LogTrace.logout(3, "");
		if (execFlg[idx])
		{
			LogTrace.logout(3, "***** TestNo = "+ idx + "*****");

			Token[] tkns = new Token[7];

			for(int i=0; i < tkns.length; i++){
				tkns[i] = new Token();
			}

			tkns[0].setValue("A");
			tkns[0].setType(JParserConst.TKN_TYPE_IDENTIFIER);

			tkns[1].setValue("+");
			tkns[1].setType(JParserConst.TKN_TYPE_OPERATOR);

			tkns[2].setValue("B");
			tkns[2].setType(JParserConst.TKN_TYPE_IDENTIFIER);

			tkns[3].setValue("+");
			tkns[3].setType(JParserConst.TKN_TYPE_OPERATOR);

			tkns[4].setValue("C");
			tkns[4].setType(JParserConst.TKN_TYPE_IDENTIFIER);

			tkns[5].setValue("-");
			tkns[5].setType(JParserConst.TKN_TYPE_OPERATOR);

			tkns[6].setValue("1");
			tkns[6].setType(JParserConst.TKN_TYPE_NUMERIC);

			ArrayList<Token> tknlist 	= new ArrayList(Arrays.asList(tkns));

			app.exec(tknlist);
		}


	}

}
