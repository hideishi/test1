package app;

import java.util.ArrayList;
import java.util.Arrays;

import common.JParserConst;
import common.JParserUtil;
import common.util.StringUtil;

import vo.Token;

public class JLexer {

	/* 解析処理状態の定数 */
	/** 初期状態 */
	static private final int LSTS_INIT =0 ;
	/** 単語処理中 */
	static private final int LSTS_WORD =LSTS_INIT + 1 ;
	/** ブロックコメント処理中 */
	static private final int LSTS_BLOCK_COMMENT = LSTS_WORD + 1;
	/** ラインコメント処理中 */
	static private final int LSTS_LINE_COMMENT = LSTS_BLOCK_COMMENT + 1;
	/** 文字列処理中 */
	static private final int LSTS_STRING = LSTS_LINE_COMMENT + 1;
	/** 文字処理中 */
	static private final int LSTS_CHAR = LSTS_STRING + 1;


	/**  トークンタイプ */
	static private int tokenType ;
	/**  トークン文字列 */
	static private String tokenText = "";
	/** 処理中文字 */
	static private String curchr  = "";
	/** ひとつ前の文字 */
	static private String prechr = "" ;

	/** 解析状態 */
	static private int lexsts = LSTS_INIT ;
	/** トークンリスト */
	static private ArrayList<Token> tknlist = new ArrayList<Token>() ;


	/**
	 *
	 * @param sourceList
	 */
	static public ArrayList<Token> lex(ArrayList<String> sourceList){
		//トークンタイプ初期化
		tokenType = JParserConst.TKN_TYPE_IDENTIFIER ;

		for(int i=0; i < sourceList.size();i++){
			String lineBuff = (String)sourceList.get(i);
			int lineNo = i+1;

			for(int j=0; j < lineBuff.length();j++){
				curchr = lineBuff.substring(j, j+1);

				if (curchr.compareTo(JParserConst.SLASH)==0){
					slashProc(j, lineBuff);

				}
				else if (curchr.compareTo(JParserConst.ASTERISK)==0){
					asteriskProc(j, lineBuff);

				}
				else if (curchr.compareTo(JParserConst.SINGLE_QUOTE)==0){
					singleQuoteProc(j, lineBuff);

				}
				else if (curchr.compareTo(JParserConst.DOUBLE_QUOTE)==0){
					doubleQuoteProc(j, lineBuff);

				}
				else if (curchr.compareTo(JParserConst.PLUS)==0){
					//オペレータ処理
					opeProc(JParserConst.OPE_PLUS, curchr);

				}
				else if (curchr.compareTo(JParserConst.MINUS)==0){
					//オペレータ処理
					opeProc(JParserConst.OPE_MINUS, curchr);

				}
				else if (isSepa(curchr)){//セパレータ判定
					sepaProc();
				}
				//トークン編集
				tokenText += curchr ;
				//ひとつ前の文字を退避
				prechr = curchr ;

			}
			if (lexsts == LSTS_LINE_COMMENT){
				//コメントトークンを登録
//				Token token = new Token(JParserConst.TKN_TYPE_LINE_COMMENT, tokenText);
//				tknlist.add(token);
				registToken(JParserConst.TKN_TYPE_LINE_COMMENT, tokenText);

				//文字バッファをクリア
				curchr = "" ;
				//トークン文字列をクリア
				tokenText = "";
				//トークンタイプをクリア
				tokenType = JParserConst.TKN_TYPE_IDENTIFIER ;

				// 行コメント処理中をクリア
				lexsts = LSTS_INIT ;
			}
			else if (lexsts == LSTS_BLOCK_COMMENT){
				//コメントトークンを登録
				registToken(JParserConst.TKN_TYPE_BLOCK_COMMENT, tokenText);
//				Token token = new Token(JParserConst.TKN_TYPE_BLOCK_COMMENT, tokenText);
//				tknlist.add(token);

				//文字バッファをクリア
				curchr = "" ;
				//トークン文字列をクリア
				tokenText = "";

			}
			//行末トークンを登録
			registToken(JParserConst.TKN_TYPE_LINEEND, "");
//			Token token = new Token(JParserConst.TKN_TYPE_LINEEND, "");
//			tknlist.add(token);


		}
		return tknlist ;
	}
	static private void isNumeric(String txt){
		StringUtil.isNumericText(txt);
		return ;
	}
	/**
	 * トークン登録
	 * @param tkn_type
	 * @param tokenText
	 */
	static private void registToken(int tkn_type, String tokenText){

		if (tokenText.length() ==0){
			return ;
		}
		//JAVA予約語判定
		if (tkn_type==JParserConst.TKN_TYPE_IDENTIFIER){
			if (JParserUtil.isJavaWord(tokenText)){
				//トークンタイプ変更 JAVA
				tkn_type = JParserConst.TKN_TYPE_JAVA_WORD ;
			}
			else
			if (JParserUtil.isJavaBasicDataTYpe(tokenText)){
				//トークンタイプ変更 JAVA基本データ型
				tkn_type = JParserConst.TKN_TYPE_JAVA_BASIC_DATA_TYPE ;
			}
			else
			if (JParserUtil.isJavaModifier(tokenText)){
				//トークンタイプ変更 JAVA修飾子
				tkn_type = JParserConst.TKN_TYPE_JAVA_MODIFIER ;
			}
			if (StringUtil.isNumericText(tokenText)){
				//トークンタイプ変更 JAVA修飾子
				tkn_type = JParserConst.TKN_TYPE_NUMERIC ;
			}
		}

		Token token = new Token(tkn_type, tokenText);
		tknlist.add(token);

	}
	static private void registOpeToken(int tkn_type, int opeType, String tokenText){

		if (tokenText.length() ==0){
			return ;
		}
		//JAVA予約語判定
		if (tkn_type==JParserConst.TKN_TYPE_IDENTIFIER){
			if (JParserUtil.isJavaWord(tokenText)){
				//トークンタイプ変更 JAVA
				tkn_type = JParserConst.TKN_TYPE_JAVA_WORD ;
			}
			else
			if (JParserUtil.isJavaBasicDataTYpe(tokenText)){
				//トークンタイプ変更 JAVA基本データ型
				tkn_type = JParserConst.TKN_TYPE_JAVA_BASIC_DATA_TYPE ;
			}
			else
			if (JParserUtil.isJavaModifier(tokenText)){
				//トークンタイプ変更 JAVA修飾子
				tkn_type = JParserConst.TKN_TYPE_JAVA_MODIFIER ;
			}
			if (StringUtil.isNumericText(tokenText)){
				//トークンタイプ変更 JAVA修飾子
				tkn_type = JParserConst.TKN_TYPE_NUMERIC ;
			}
		}

		Token token = new Token(tkn_type, opeType, tokenText);
		tknlist.add(token);

	}
	/**
	 * セパレータ処理
	 */
	static private void sepaProc(){
		//セパレータ前のトークンを登録
		registToken(tokenType, tokenText);

		int tkntype = JParserConst.TKN_TYPE_SEPA ;

//		if (curchr.equals("(")){
//			tkntype = JParserConst.TKN_KAKU_KAKKO_START ;
//		}else if(curchr.equals(")")){
//			tkntype = JParserConst.TKN_MARU_KAKKO_END ;
//		}else if(curchr.equals("{")){
//			tkntype = JParserConst.TKN_MIDDLE_KAKKO_START ;
//		}else if(curchr.equals("}")){
//			tkntype = JParserConst.TKN_MIDDLE_KAKKO_END ;
//		}else if(curchr.equals("[")){
//			tkntype = JParserConst.TKN_KAKU_KAKKO_START ;
//		}else if(curchr.equals("]")){
//			tkntype = JParserConst.TKN_KAKU_KAKKO_END ;
//		}
		//セパレータを登録
		registToken(tkntype, curchr);

		//ひとつ前の文字を退避
		prechr = curchr ;
		//文字バッファをクリア
		curchr = "" ;
		//トークン文字列をクリア
		tokenText = "";
		//トークンタイプをクリア
		tokenType = JParserConst.TKN_TYPE_IDENTIFIER ;

	}
	static private void opeProc(int opeType, String txt){
		//"＋"前のトークンを登録
		registToken(tokenType, tokenText);
		//"＋"を登録
		registOpeToken(JParserConst.TKN_TYPE_OPERATOR, opeType, txt);
		//ひとつ前の文字を退避
//		prechr = curchr ;
		prechr = txt ;
		//文字バッファをクリア
		curchr = "" ;
		//トークン文字列をクリア
		tokenText = "";
		//トークンタイプをクリア
		tokenType = JParserConst.TKN_TYPE_IDENTIFIER ;
	}
	/**
	 * スラッシュ処理
	 * @param idx　解析文字位置
	 * @param lineBuff ラインバッファ
	 */
	static private void slashProc(int idx, String lineBuff){
		if (lexsts ==LSTS_BLOCK_COMMENT){
			if (prechr.compareTo(JParserConst.ASTERISK)==0){//ひとつ前が "*" の場合
				// 状態をブロックコメント処理中から、初期状態に変更
				lexsts = LSTS_INIT ;

				//トークンテキストに"/"を加える
				tokenText += curchr ;

				//コメントトークンを登録
				registToken(tokenType, tokenText);

				//文字バッファをクリア
				curchr = "" ;
				//トークン文字列をクリア
				tokenText = "";
				//トークンタイプ初期化
				tokenType = JParserConst.TKN_TYPE_IDENTIFIER;

			}
		}
		else if (lexsts ==LSTS_LINE_COMMENT){

		}
		else if (lexsts ==LSTS_STRING){

		}
		else if (lexsts ==LSTS_CHAR){

		}else{
			if (prechr.compareTo(JParserConst.SLASH)==0){//ひとつ前が "/" の場合
				// 状態を行コメント処理中に変更
				lexsts = LSTS_LINE_COMMENT ;
				//トークンタイプ設定
				tokenType = JParserConst.TKN_TYPE_LINE_COMMENT ;

				//文字バッファをクリア
				curchr = "" ;
				//トークン文字列に、コメント先頭文字列を設定
				tokenText = "//";

			}
		}

	}
	static private void asteriskProc(int idx, String lineBuff){
		if (lexsts ==LSTS_BLOCK_COMMENT){

		}
		else if (lexsts ==LSTS_LINE_COMMENT){

		}
		else if (lexsts ==LSTS_STRING){

		}
		else if (lexsts ==LSTS_CHAR){

		}else{
			if (prechr.compareTo(JParserConst.SLASH)==0){//ひとつ前が "/" の場合
				//直前のトークンを登録
				if (tokenText.length() >= 2){
					// '/'を取り除く
					String txt = tokenText.substring(0, tokenText.length()-1);
					registToken(tokenType, txt);
				}

				// 状態をブロックコメント処理中に変更
				lexsts = LSTS_BLOCK_COMMENT ;
				//トークンタイプ設定
				tokenType = JParserConst.TKN_TYPE_BLOCK_COMMENT ;

				//ひとつ前の文字を退避
				prechr = curchr ;
				//文字バッファをクリア
				curchr = "" ;

				//トークン文字列に、"/*" を設定
				tokenText = "/*";
			}
		}

	}

	static private void doubleQuoteProc(int idx, String lineBuff){
		if (lexsts ==LSTS_BLOCK_COMMENT){

		}
		else if (lexsts ==LSTS_LINE_COMMENT){

		}
		else if (lexsts ==LSTS_STRING){
			// 状態を文字列処理中から、初期状態に変更
			lexsts = LSTS_INIT ;
			//トークンタイプ設定
			tokenType = JParserConst.TKN_TYPE_STRING ;

			//文字列トークンを登録
			Token token = new Token(tokenType, tokenText);
			tknlist.add(token);

			//doubleQuote トークンを登録
			token = new Token(JParserConst.TKN_TYPE_SEPA, "\"");
			tknlist.add(token);

			//ひとつ前の文字を退避
			prechr = curchr ;

			//文字バッファをクリア
			curchr = "" ;
			//トークン文字列をクリア
			tokenText = "";
			//トークンタイプをクリア
			tokenType = JParserConst.TKN_TYPE_IDENTIFIER ;


		}
		else if (lexsts ==LSTS_CHAR){

		}else{
			// 状態を文字列処理中に変更
			lexsts = LSTS_STRING ;
			//トークンタイプ設定
			tokenType = JParserConst.TKN_TYPE_STRING ;
		}

	}
	static private void singleQuoteProc(int idx, String lineBuff){
		if (lexsts ==LSTS_BLOCK_COMMENT){

		}
		else if (lexsts ==LSTS_LINE_COMMENT){

		}
		else if (lexsts ==LSTS_STRING){

		}
		else if (lexsts ==LSTS_CHAR){
			// 状態を文字処理中から、初期状態に変更
			lexsts = LSTS_INIT ;
			//トークンタイプ設定
			tokenType = JParserConst.TKN_TYPE_CHAR ;

			//文字トークンを登録
			Token token = new Token(tokenType, tokenText);
			tknlist.add(token);

			//ひとつ前の文字を退避
			prechr = curchr ;

			//文字バッファをクリア
			curchr = "" ;
			//トークン文字列をクリア
			tokenText = "";
			//トークンタイプをクリア
			tokenType = JParserConst.TKN_TYPE_IDENTIFIER ;

		}else{
			// 状態を文字処理中に変更
			lexsts = LSTS_CHAR ;
			//トークンタイプ設定
			tokenType = JParserConst.TKN_TYPE_CHAR ;
		}

	}

	/**
	 * セパレータ判定
	 * @param chr　対象文字
	 * @return
	 */
	static private boolean isSepa(String chr){
		final String sepaTbl = " \t(){}[]<>.,:;+-=";

		if (lexsts ==LSTS_BLOCK_COMMENT){
		}
		else if (lexsts ==LSTS_LINE_COMMENT){

		}
		else if (lexsts ==LSTS_STRING){

		}
		else if (lexsts ==LSTS_CHAR){

		}else if (sepaTbl.indexOf(chr) >=0){
			return true ;
		}

		return false ;
	}

}
