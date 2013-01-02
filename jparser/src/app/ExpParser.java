package app;

import java.util.ArrayList;
import java.util.Iterator;

import vo.OperatorInfo;
import vo.Token;

import common.JParserConst;
import common.util.LogTrace;

public class ExpParser {
	static private OperatorInfo opif = new OperatorInfo();
	/**
	 *
	 * @param exp
	 */
	static public void parse(String exp){
		ArrayList<String> sourceList = new ArrayList<String>();
		sourceList.add(exp);

		//字句解析
		ArrayList<Token> tknlist = JLexer.lex(sourceList);

		ArrayList<Token> activeTknList = parseActive(tknlist);

		//activeTknList　確認
		StringBuilder sbl = new StringBuilder();
		Iterator<Token> itr = activeTknList.iterator();
		while(itr.hasNext()){
			Token tkn = itr.next();
			sbl.append(tkn.getTknValue()) ;
		}
		LogTrace.logout(3, sbl.toString());

		int idx = 0;
		parseExp(idx, activeTknList);
	}
	/**
	 * ホワイトスペースを取り除いた有効トークンリストを作成する
	 * @param tknlist
	 * @return
	 */
	static private ArrayList<Token>  parseActive(ArrayList<Token> tknlist){
		ArrayList<Token> activeTknList = new ArrayList<Token>();

		for(int i=0; i < tknlist.size(); i++){
			Token token = (Token)tknlist.get(i);

			String tkntext = token.getTknValue();

			if (token.getTknType() == JParserConst.TKN_TYPE_BLOCK_COMMENT){
				continue ;
			}
			if (tkntext.equals(" ")){
				continue;
			}
			else if (tkntext.equals("\t")){
				continue;
			}
			activeTknList.add(token);
		}
		return activeTknList ;
	}
	/**
	 *　式の解析
	 * @param tknlist
	 */
	static private void  parseExp(int idx, ArrayList<Token> tknlist){
		int a = 1 ;
		int b = 1+ a++ ;
		int c = a +(b + b++) ;

		for(int i=idx; i < tknlist.size(); i++){
			Token token = (Token)tknlist.get(i);

			String tkntext = token.getTknValue();
			int tkntype = token.getTknType();

			switch(tkntype){
			case JParserConst.TKN_TYPE_IDENTIFIER://
				if (opif.getLeft() == null){
					opif.setLeft(token);
				}
				break;
			case JParserConst.TKN_TYPE_NUMERIC://
				break;
			case JParserConst.TKN_TYPE_STRING://
				break;
			case JParserConst.TKN_TYPE_CHAR://
				break;
			case JParserConst.TKN_TYPE_SEPA://
				if (token.getTknValue().equals("(")){
					kakkoProc(i, tknlist);

				}
				else if (token.getTknValue().equals(")")){

				}
				else if (token.getTknValue().equals("+")){
					Token pretoken = null ;
					if (i > 0){
						pretoken = (Token)tknlist.get(i-1);
						if ((pretoken.getTknType() == JParserConst.TKN_TYPE_OPERATOR) &&
						   (pretoken.getOpeType()==JParserConst.OPE_PLUS))
						{
							//ひとつ前が、"+"の場合
							token.setOpeType(JParserConst.OPE_PLUSPLUS);
							token.setTknValue("++");
							opif.setOperator(token);
						}
					}

				}
				else if (token.getTknValue().equals("-")){

				}
				else if (token.getTknValue().equals("*")){

				}
				else if (token.getTknValue().equals("/")){

				}
				break;

			}
		}
		return  ;
	}
	/**
	 * idxは、"("を示す
	 * @param i
	 * @param tknlist
	 */
	static private void kakkoProc( int i, ArrayList<Token> tknlist){

	}
}
