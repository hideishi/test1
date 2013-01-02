package common;

public class JParserConst {
	/* 文字の定数 */
	static public final String SLASH = "/";
	static public final String ASTERISK = "*";
	static public final String COMMA = ",";
	static public final String PERIOD = ".";
	static public final String COLON = ":";
	static public final String SEMI_COLON = ";";
	static public final String SPACE = " ";
	static public final String TAB = "\t";
	static public final String SINGLE_QUOTE = "'";
	static public final String DOUBLE_QUOTE = "\"";
	static public final String EN_MARK = "\\";
	static public final String MARU_KAKKO_START 	= "(";
	static public final String MARU_KAKKO_END 		= ")";
	static public final String MIDDLE_KAKKO_START 	= "{";
	static public final String MIDDLE_KAKKO_END 	= "}";
	static public final String KAKU_KAKKO_START 	= "[";
	static public final String KAKU_KAKKO_END 		= "]";
	static public final String PLUS 				= "+";
	static public final String MINUS 				= "-";
	static public final String EQUAL 				= "=";

	/* トークンタイプ定義 */
	/** */
	static public final int TKN_TYPE_IDENTIFIER = 1 ;
	static public final int TKN_TYPE_BLOCK_COMMENT = TKN_TYPE_IDENTIFIER + 1 ;
	static public final int TKN_TYPE_LINE_COMMENT = TKN_TYPE_BLOCK_COMMENT + 1 ;
	static public final int TKN_TYPE_STRING 		= TKN_TYPE_LINE_COMMENT + 1 ;
	static public final int TKN_TYPE_CHAR 			= TKN_TYPE_STRING + 1 ;
	static public final int TKN_TYPE_NUMERIC 		= TKN_TYPE_CHAR + 1 ;
	static public final int TKN_TYPE_SEPA 			= TKN_TYPE_NUMERIC + 1 ;
	static public final int TKN_TYPE_LINEEND 		= TKN_TYPE_SEPA + 1 ;
	/** JAVA予約語 */
	static public final int TKN_TYPE_JAVA_WORD 		= TKN_TYPE_LINEEND + 1 ;
	/** JAVA基本データ型 */
	static public final int TKN_TYPE_JAVA_BASIC_DATA_TYPE 	= TKN_TYPE_JAVA_WORD + 1 ;
	/** JAVAデータ型 */
	static public final int TKN_TYPE_JAVA_DATA_TYPE 	= TKN_TYPE_JAVA_BASIC_DATA_TYPE + 1 ;
	/** JAVA修飾子 */
	static public final int TKN_TYPE_JAVA_MODIFIER 	= TKN_TYPE_JAVA_DATA_TYPE + 1 ;
	/**  演算子*/
	static public final int TKN_TYPE_OPERATOR		= TKN_TYPE_JAVA_MODIFIER + 1 ;
	static public final int TKN_MARU_KAKKO_START 	= TKN_TYPE_OPERATOR + 1;
	static public final int TKN_MARU_KAKKO_END 		= TKN_MARU_KAKKO_START + 1;
	static public final int TKN_MIDDLE_KAKKO_START 	= TKN_MARU_KAKKO_END + 1;
	static public final int TKN_MIDDLE_KAKKO_END 	= TKN_MIDDLE_KAKKO_START + 1;
	static public final int TKN_KAKU_KAKKO_START 	= TKN_MIDDLE_KAKKO_END + 1;
	static public final int TKN_KAKU_KAKKO_END 		= TKN_KAKU_KAKKO_START + 1 ;

	/*  オペレータタイプ定義*/
	static public final int OPE_PLUS 		= 1 ;									// "+"
	static public final int OPE_MINUS 		= OPE_PLUS  + 1 ;						// "-"
	static public final int OPE_MULTI 		= OPE_MINUS  + 1 ;						// "*"
	static public final int OPE_DIV 		= OPE_MULTI  + 1 ;						// "/"
	static public final int OPE_PLUSPLUS 	= OPE_DIV + 1 ;							// "++"
	static public final int OPE_MINUSMINUS 	= OPE_PLUSPLUS + 1 ;					// "--"
	//再帰代入
	static public final int OPE_PLUS_RECURSIVE 	= OPE_MINUSMINUS + 1 ;				// "+="
	static public final int OPE_MINUS_RECURSIVE = OPE_PLUS_RECURSIVE + 1 ;			// "-="
	static public final int OPE_MULTI_RECURSIVE = OPE_MINUS_RECURSIVE + 1 ;			// "*="
	static public final int OPE_DIV_RECURSIVE 	= OPE_MULTI_RECURSIVE + 1 ;			// "/="

	//論理演算子
	static public final int OPE_LOGIC_AND 	= OPE_DIV_RECURSIVE + 1 ;				// "&" 論理積
	static public final int OPE_LOGIC_OR 	= OPE_LOGIC_AND + 1 ;					// "|" 論理和
	static public final int OPE_LOGIC_EXOR 	= OPE_LOGIC_OR + 1 ;					// "~" 排他的論理和
	static public final int OPE_LOGIC_NOT 	= OPE_LOGIC_EXOR + 1 ;					// "!" 否定

	static public final int OPE_EQUAL = OPE_LOGIC_NOT  + 1 ;						// "="
	static public final int OPE_COMPARE_EQUAL 		= OPE_EQUAL  + 1 ;				// "=="
	static public final int OPE_COMPARE_NOTEQUAL 	= OPE_COMPARE_EQUAL  + 1 ;		// "!="
	static public final int OPE_LETH_THAN 			= OPE_COMPARE_NOTEQUAL  + 1 ;	// "<"
	static public final int OPE_GREATER_THAN 		= OPE_LETH_THAN  + 1 ;			// ">"
	static public final int OPE_LETH_THAN_EQUAL 	= OPE_GREATER_THAN  + 1 ;		// "<="
	static public final int OPE_GREATER_THAN_EQUAL 	= OPE_LETH_THAN_EQUAL  + 1 ;	// ">="
	//ビット演算子
	static public final int OPE_LEFT_SHIFT 			= OPE_GREATER_THAN_EQUAL  + 1 ;		// "<< n"  n ビット左シフト
	static public final int OPE_RIGHT_SHIFT 		= OPE_LEFT_SHIFT  + 1 ;				// ">> n"  符号付 n ビット右シフト
	static public final int OPE_RIGHT_SHIFT_ZERO 	= OPE_RIGHT_SHIFT  + 1 ;			// ">>>n"  0 埋め n ビット右シフト
	static public final int OPE_BIT_REVERSE 		= OPE_RIGHT_SHIFT_ZERO  + 1 ;		// "~"     反転



	/**
	 *
	 * @param tkntype
	 * @return
	 */
	static public boolean isLineEndToken(int tkntype){
		if (tkntype == TKN_TYPE_LINEEND){
			return true ;
		}
		return false ;
	}

	static public boolean isBlockCommentToken(int tkntype){
		if (tkntype == TKN_TYPE_BLOCK_COMMENT){
			return true ;
		}
		return false ;
	}
	static public boolean isLineCommentToken(int tkntype){
		if (tkntype == TKN_TYPE_LINE_COMMENT){
			return true ;
		}
		return false ;
	}

}
