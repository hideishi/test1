package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class JParserUtil {

	static public boolean isJavaClass(ArrayList<String> importList, String clsname){
		try {
			Object obj = Class.forName(clsname);

		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
//			e.printStackTrace();

			Iterator<String> itr = importList.iterator();
			while(itr.hasNext()){
				String strImport = itr.next();
				String wkimport = strImport.replace(".", ",");
				String[] items = wkimport.split(",", 0);

				try{
					// java.lang で検索する
					Object obj = Class.forName("java.lang." + clsname);
					return true ;
				}
				catch(ClassNotFoundException ex){
					if(items[items.length -1 ].equals("*")){
						return searchAsterisk(clsname, items);
					}
					if (items[items.length -1 ].equals(clsname)){//クラス名が import 文に含まれている場合
						try{
							Object obj = Class.forName(strImport);
						}
						catch(ClassNotFoundException ex2){
							return false ;
						}
						return true ;
					}
				}
			}
		}
		return false ;
	}
	/**
	 * importの末尾が "*" の場合の検索
	 * @param clsname
	 * @param items
	 * @return
	 */
	static private boolean searchAsterisk(String clsname, String[] items){

			String completePath = "";
			for(int i=0; i < items.length -1 ; i++){
				completePath += items[i];
				completePath += ".";
			}
			completePath += clsname ;
			try{
				Object obj = Class.forName(completePath);
			}
			catch(ClassNotFoundException ex3){
				return false ;
			}
			return true ;

	}
	/**
	 * JAVAクラス修飾子判定
	 * @param txt
	 * @return
	 */
	static public boolean isClassModifier(String txt){
		final String[] accessModifierTbl = {"abstract", "final", "public"};

		for(int i=0; i <accessModifierTbl.length; i++ ){
			if (txt.compareTo(accessModifierTbl[i])==0){
				return true ;
			}
		}
		return false ;
	}
	/**
	 * JAVAアクセス修飾子判定
	 * @param txt
	 * @return
	 */
	static public boolean isAccessModifier(String txt){
		final String[] accessModifierTbl = {"public", "protected", "private"};

		for(int i=0; i <accessModifierTbl.length; i++ ){
			if (txt.compareTo(accessModifierTbl[i])==0){
				return true ;
			}
		}
		return false ;
	}
	/**
	 * JAVA予約語判定
	 * @param tokenText
	 * @return
	 */
	static public boolean isJavaWord(String tokenText){
		final String[] javaWordTbl = {"package", "import",  "class","interface",
				"extends", "implements", "return",
				"if", "else", "for", "while", "do", "continue",
				"switch", "case", "break"};


		//比較するために配列をソートしておく
		Arrays.sort(javaWordTbl);

		//配列に一致するものがあるか
		if(Arrays.binarySearch(javaWordTbl, tokenText) >= 0)
		{
			//一致したときの処理
			return true ;
		}
		return false ;
	}
	/**
	 * JAVA基本データ型判定
	 * @param tokenText
	 * @return
	 */
	static public boolean isJavaBasicDataTYpe(String tokenText){
		final String[] javaWordTbl = {"void", "boolean","char","byte","short","int","long","float","double"};

		//比較するために配列をソートしておく
		Arrays.sort(javaWordTbl);

		//配列に一致するものがあるか
		if(Arrays.binarySearch(javaWordTbl, tokenText) >= 0)
		{
			//一致したときの処理
			return true ;
		}
		return false ;
	}
	/**
	 * JAVA修飾子判定
	 * @param tokenText
	 * @return
	 */
	static public boolean isJavaModifier(String tokenText){
		final String[] javaWordTbl = {"abstract","static","final","strictfp","transient","volatile",
				"public", "protected", "private"};

		//比較するために配列をソートしておく
		Arrays.sort(javaWordTbl);

		//配列に一致するものがあるか
		if(Arrays.binarySearch(javaWordTbl, tokenText) >= 0)
		{
			//一致したときの処理
			return true ;
		}
		return false ;
	}

}
