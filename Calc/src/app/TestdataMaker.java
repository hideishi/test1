package app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import common.JParserConst;
import common.util.LogTrace;

public class TestdataMaker {
	public ArrayList<Token> make(int testNo) {

//		switch(testNo){
//		case 1:test1(tknlist); 	break ;
//		case 2:test2(tknlist);	break ;
//		case 3:test3(tknlist);	break ;
//		case 4:test4(tknlist);	break ;
//
//		}
		String methodName = "test" + testNo ;

		Method method;
		try {
			method = this.getClass().getMethod(methodName, null);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		Object ret; //戻り値
		try {
			ret = method.invoke(this, null);
			ArrayList<Token>tknlist_ = (ArrayList<Token>)ret ;
			Token tkn = (Token)tknlist_.get(0);

			return tknlist_ ;
			//  = object.メソッド((int)1); と同じ
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * A+B*(C+1)
	 * @return
	 */
	public  ArrayList<Token> test4(){
		ArrayList<Token> tknlist = new ArrayList<Token>();

		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "6"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"+"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "5"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"*"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_SEPA, 		"("));
		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "4"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"+"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "3"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"*"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_NUMERIC, 	"2"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"*"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_NUMERIC, 	"5"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"+"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_NUMERIC, 	"1"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_SEPA, 		")"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"+"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_NUMERIC, 	"7"));

		return tknlist ;

	}

	/**
	 *
	 * @param tknlist
	 */
	public ArrayList<Token> test1(){
		ArrayList<Token> tknlist = new ArrayList<Token>();

		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "A"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"+"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "B"));

		return tknlist ;
	}
	public ArrayList<Token> test2(){

		ArrayList<Token> tknlist = new ArrayList<Token>();

		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "A"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"+"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_IDENTIFIER, "B"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_OPERATOR, 	"-"));
		tknlist.add(new Token(JParserConst.TKN_TYPE_NUMERIC, 	"1"));

		return tknlist ;

	}
	public ArrayList<Token> test3(){
		Token[] tkns = new Token[7];

		for (int i = 0; i < tkns.length; i++) {
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

		ArrayList<Token> tknlist = new ArrayList(Arrays.asList(tkns));

		return tknlist ;

	}
}
