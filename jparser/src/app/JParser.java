package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import common.JParserConst;
import common.JParserUtil;
import common.util.LogTrace;

import vo.ClassInfo;
import vo.Expression;
import vo.FieldInfo;
import vo.GetClassNameReturn;
import vo.JavaDataType;
import vo.JavaModifier;
import vo.MethodInfo;
import vo.OperatorInfo;
import vo.Package;
import vo.ParamInfo;
import vo.ThrowsInfo;
import vo.Token;
import vo.VariableDecl;

public class JParser
{
	final static int STS_NONE = 0;
	final static int STS_PARSE_START = STS_NONE + 1;
	final static int STS_CLASS_BODY = STS_PARSE_START + 1;
	final static int STS_METHOD_PARAM = STS_CLASS_BODY + 1;

	static private ArrayList<JavaModifier> modlist = new ArrayList<JavaModifier>();
	static private ArrayList<Token> activeTknList = new ArrayList<Token>();
	static private ArrayList<FieldInfo> fieldInfoList = null ;
	static private ArrayList<ParamInfo> paramInfolist = null ;

	static private Package pkg = null ;
	static private ClassInfo clsinfo = null ;
	static int sts = STS_PARSE_START ;

	static FieldInfo fif = null ;
	static MethodInfo mif = null ;

	/**
	 * Tokenインスタンスのリスト
	 * @param tknlist
	 */
	static public void parse(ArrayList<Token> tknlist){


		boolean collectionElement = false ;
		String targetName = "";
		JavaDataType datatype = null ;

		for(int i=0; i < tknlist.size(); i++){
			Token token = (Token)tknlist.get(i);

			int tkntype = token.getTknType();

			switch(tkntype){
			case JParserConst.TKN_TYPE_JAVA_WORD://
				if (token.getTknValue().compareTo("package")==0){
					i++ ;
					i = packageProc(i, tknlist);

				}else if (token.getTknValue().compareTo("import")==0){
					i++ ;
					i = importProc(i, tknlist);
				}else if (token.getTknValue().compareTo("class")==0){
					i++ ;
					i = classProc(i, tknlist );
					sts = STS_CLASS_BODY ;
					LogTrace.logout(3, pkg.toString());
				}else if (token.getTknValue().compareTo("interface")==0){//@@@@@@
					i++ ;
					i = classProc(i, tknlist);
				}
				break;

			case JParserConst.TKN_TYPE_JAVA_MODIFIER://
				JavaModifier mod = new JavaModifier(token);
				modlist.add(mod);
				activeTknList.add(token) ;

				break;

			case JParserConst.TKN_TYPE_JAVA_BASIC_DATA_TYPE://
			case JParserConst.TKN_TYPE_IDENTIFIER://
				if (JParserUtil.isJavaClass(pkg.getImportList(), token.getTknValue()) 	||
					(JParserUtil.isJavaBasicDataTYpe(token.getTknValue()))){
					fif = new FieldInfo();
//					fieldMethodProc(i, tknlist,  pkg.getImportList(),mif, fif );
					if (collectionElement){
						JavaDataType dt = new JavaDataType();
						dt.setDataTypeName(token.getTknValue());
						datatype.setElementDataType(dt);
					}else{
						datatype = new JavaDataType();
						datatype.setDataTypeName(token.getTknValue()) ;
					}
					token.setTknType(JParserConst.TKN_TYPE_JAVA_DATA_TYPE);
					token.setDatatype(datatype);

					activeTknList.add(token) ;

				}else{
					targetName = token.getTknValue();
					activeTknList.add(token) ;
				}
				break;

			case JParserConst.TKN_TYPE_OPERATOR://
				activeTknList.add(token) ;

				break;
			case JParserConst.TKN_TYPE_SEPA://
				String tkntext = token.getTknValue();

				if (tkntext.equals(";")){
					//フィールド処理
					semiColonProc();
					LogTrace.logout(3, clsinfo.toString());

				}else if (tkntext.equals(",")){
					//パラメータ処理
					if (sts == STS_METHOD_PARAM ){
						VariableDecl vdl = new VariableDecl();
						if (isVariableDecl(activeTknList, vdl)){
							//パラメータです
//							Token varTkn = activeTknList.get(activeTknList.size() - 1);
							Token varTkn = vdl.getVarToken();

							ParamInfo pif = makeParamInfo(datatype, varTkn.getTknValue());
							datatype = null ;
							targetName = "" ;
							if (paramInfolist == null){
								paramInfolist = new ArrayList<ParamInfo>();
							}
							paramInfolist.add(pif);

							activeTknList.clear();

						}
					}

				}else if (tkntext.equals("=")){
					activeTknList.add(token) ;
				}
//				else if (tkntext.equals("+")){
//					activeTknList.add(token) ;
//				}else if (tkntext.equals("-")){
//					activeTknList.add(token) ;
//				}
				else if (tkntext.equals("{")){

				}else if (tkntext.equals("}")){

				}else if (tkntext.equals("(")){
					//メソッド情報の作成など
					mif = new MethodInfo();
					maruKakkoStartProc();
				}else if (tkntext.equals(")")){
					//パラメータ処理
					if (sts == STS_METHOD_PARAM ){
						VariableDecl vdl = new VariableDecl();
						if (isVariableDecl(activeTknList, vdl)){
							//パラメータです
//							Token varTkn = activeTknList.get(activeTknList.size() - 1);
							Token varTkn = vdl.getVarToken();

							ParamInfo pif = makeParamInfo(datatype, varTkn.getTknValue());
							datatype = null ;
							targetName = "" ;
							if (paramInfolist == null){
								paramInfolist = new ArrayList<ParamInfo>();
							}
							paramInfolist.add(pif);

							activeTknList.clear();

						}
						mif.setParamInfolist(paramInfolist);
					}
					LogTrace.logout(3, mif.toString());

				}else if (tkntext.equals("[")){
					activeTknList.add(token) ;

				}else if (tkntext.equals("]")){
					activeTknList.add(token) ;

				}else if (tkntext.equals("<")){
					//Generic対応
					if ((sts == STS_METHOD_PARAM) && (datatype != null)){
						if (isCollection(datatype)){
							collectionElement = true ;
						}
					}
				}else if (tkntext.equals(">")){
					//Generic対応
					if ((sts == STS_METHOD_PARAM) && (collectionElement)){
						collectionElement = false ;
					}
				}else if (tkntext.equals(" ")){

				}else if (tkntext.equals("\t")){

				}
				break;
			case JParserConst.TKN_TYPE_BLOCK_COMMENT://ブロックコメント
				break;
			case JParserConst.TKN_TYPE_LINE_COMMENT://行コメント
				break;
			case JParserConst.TKN_TYPE_STRING://
				activeTknList.add(token) ;
				break;
			case JParserConst.TKN_TYPE_CHAR://
				activeTknList.add(token) ;
				break;
			case JParserConst.TKN_TYPE_NUMERIC://数値トークン
				datatype = new JavaDataType();
				//@@@@
//				makeNumericDatatype(token, datatype);
				activeTknList.add(token) ;
				break;

			default:
				break;

/*			case JParserConst.TKN_TYPE_IDENTIFIER://
				if (JParserUtil.isJavaClass(pkg.getImportList(),  token.getValue())){
					datatype = token.getValue();
				}else{
					fieldName = token.getValue();
				}
				break;
*/
			}
		}
	}
	static private void makeNumericDatatype(Token tkn, JavaDataType dt){
		String typeName = "" ;
		String txt = tkn.getTknValue();

		if (txt.indexOf(".") < 0){
			typeName = "long" ;
			long value = Long.parseLong(txt);
			if (value <= Integer.MAX_VALUE){
				typeName = "int" ;
			}
		}else{
			double value = Double.parseDouble(txt);
			typeName = "double" ;
			if (value <= Float.MAX_VALUE){
				typeName = "float" ;
			}
		}
		dt.setDataTypeName(typeName) ;
		dt.setArrayFlg(false);

	}

	/**
	 * 変数宣言判定　書式：[データ型] + [変数名]　+ "=" + [初期値]
	 * 初期値付きどうする？ @@@@
	 * @param activeTknList
	 * @param vdl 変数宣言情報
	 * @return
	 */
	static private boolean isVariableDecl(ArrayList<Token> activeTknList, VariableDecl vdl){
		boolean found = false ;
		int idx = 0 ;

		//"="トークンを含むか
		int i =0 ;
		for(i=0; i < activeTknList.size(); i++){
			Token tkn = activeTknList.get(i);
			if ((tkn.getTknType() == JParserConst.TKN_TYPE_SEPA) && (tkn.getTknValue().equals("="))){
				found = true ;
				break;
			}
		}
		if (found){
			if (i >= 2){
				idx = i ;
				//右辺のトークン個数が複数個ならば、式と判定し、解析を行う
				if (activeTknList.size() >= (i+2)){
					parseExp(i, activeTknList);
				}

			}else{
				return false ;
			}
		}else{
			idx = activeTknList.size() ;
		}
		Token typeTkn = activeTknList.get(idx - 2);
		Token varTkn = activeTknList.get(idx - 1);

		if ((typeTkn.getTknType() == JParserConst.TKN_TYPE_JAVA_DATA_TYPE) &&
				(varTkn.getTknType() == JParserConst.TKN_TYPE_IDENTIFIER)){

			//処理結果を設定
			vdl.setTypeToken(typeTkn);
			vdl.setVarToken(varTkn);

			if (found){
				vdl.setValueToken(activeTknList.get(idx+1));
			}
			//変数宣言です
			return true ;
		}
		return false ;

	}
	/**
	 * 式の解析　@@@@@@
	 */
	static private void parseExp(int curidx, ArrayList<Token> tknlist){
		if (JParserUtil.isTwoItemOpe(activeTknList.get(curidx + 2).getTknValue())){//二項演算子ならば
			OperatorInfo opi = new OperatorInfo();
			opi.setLeft(activeTknList.get(curidx + 1));
			opi.setRight(activeTknList.get(curidx + 3));
		}
	}
	/**
	 * 配列変数宣言判定 書式：[データ型] + "[]" + [変数名], または　[データ型] + [変数名]+ "[]"
	 * @param activeTknList
	 * @return
	 */
	static private boolean isArrayVariableDecl(ArrayList<Token> activeTknList, VariableDecl vdl){
		boolean found = false ;
		boolean rslt = false ;
		int idx = 0 ;

		int len = activeTknList.size();

		//"="トークンを含むか
		int i =0 ;
		for(i=0; i < activeTknList.size(); i++){
			Token tkn = activeTknList.get(i);
			if ((tkn.getTknType() == JParserConst.TKN_TYPE_SEPA) && (tkn.getTknValue().equals("="))){
				found = true ;
				break;
			}
		}
		if (found){
			if (i >= 2){
				idx = i ;
			}else{
				return false ;
			}
		}else{
			idx = activeTknList.size() ;
		}

		if (idx >= 4){
			Token[] token = new Token[4];
			token[0] = activeTknList.get(idx - 4);
			token[1] = activeTknList.get(idx - 3);
			token[2] = activeTknList.get(idx - 2);
			token[3] = activeTknList.get(idx - 1);

			if ((token[0].getTknType() == JParserConst.TKN_TYPE_JAVA_DATA_TYPE) &&
				(token[1].getTknValue().equals("[")) &&
				(token[2].getTknValue().equals("]")) &&
				(token[3].getTknType() == JParserConst.TKN_TYPE_IDENTIFIER)
				){
				//"[", "]"トークンを取り除く
				activeTknList.remove(idx - 3);
				activeTknList.remove(idx - 3);
//				Token typeTkn = activeTknList.get((idx-2) - 2);
//				Token varTkn  = activeTknList.get((idx-2) - 1);

				//配列フィールドです
				rslt = true ;
			}else if ((token[0].getTknType() == JParserConst.TKN_TYPE_JAVA_DATA_TYPE) &&
					(token[1].getTknType() == JParserConst.TKN_TYPE_IDENTIFIER) &&
					(token[2].getTknValue().equals("[")) &&
					(token[3].getTknValue().equals("]"))
					){
				//"[", "]"トークンを取り除く
				activeTknList.remove(idx - 2);
				activeTknList.remove(idx - 2);

				//配列フィールドです
				rslt = true ;
			}
			if (rslt){
				Token typeTkn = activeTknList.get(idx - 4);
				Token varTkn  = activeTknList.get(idx - 3);

				vdl.setTypeToken(typeTkn);
				vdl.setVarToken(varTkn);

				if (found){
					vdl.setValueToken(activeTknList.get(idx));
				}
			}
		}
		return rslt ;

	}
	/**
	 *
	 */
	static private void semiColonProc(){
		//フィールド判定
		boolean active = false ;
		boolean arrayFlg = false  ;

		VariableDecl vdl = new VariableDecl();

		if (sts == STS_CLASS_BODY){

			if (isArrayVariableDecl(activeTknList, vdl)){
				arrayFlg = true ;
				active = true ;
			}
			else if (isVariableDecl(activeTknList, vdl))  {
				active = true ;
			}
			if 	(active){
				//フィールドです
				Token typeTkn 		= vdl.getTypeToken();
				Token varTkn 		= vdl.getVarToken();
				Token valueToken 	= null ;
				if (vdl.getValueToken() != null){
					valueToken = vdl.getValueToken();
				}

				ArrayList<JavaModifier> wk_modlist = new ArrayList<JavaModifier>();
				wk_modlist.addAll(modlist);

				fif = new FieldInfo();
				fif.setModifierlist(wk_modlist);

				JavaDataType dt = typeTkn.getDatatype();
				if (dt == null){
					dt.setDataTypeName(typeTkn.getTknValue());
				}
				dt.setArrayFlg(arrayFlg);
				fif.setDataType(dt);
				fif.setFieldName(varTkn.getTknValue());
				fif.setValueToken(valueToken);

				if (fieldInfoList == null){
					fieldInfoList = new ArrayList<FieldInfo>();
					clsinfo.setFieldInfoList(fieldInfoList);
				}
				fieldInfoList.add(fif);
				fif = null ;

				if (modlist != null){
					modlist.clear();
				}
				activeTknList.clear();

			}
		}
	}
	/**
	 *
	 */
	static private void maruKakkoStartProc(){
		//メソッド判定
		if (sts == STS_CLASS_BODY){
			VariableDecl vdl = new VariableDecl();
			if (isVariableDecl(activeTknList, vdl)){
				//メソッドです
//				Token typeTkn = activeTknList.get(activeTknList.size() - 2);
//				Token varTkn = activeTknList.get(activeTknList.size() - 1);
				Token typeTkn = vdl.getTypeToken();
				Token varTkn = vdl.getVarToken();

				ArrayList<JavaModifier> wk_modlist = new ArrayList<JavaModifier>();
				wk_modlist.addAll(modlist);

				mif = new MethodInfo();
				mif.setMethodName(varTkn.getTknValue());
				mif.setModifierlist(wk_modlist);
				mif.setReturnType(typeTkn.getDatatype());

				if (modlist != null){
					modlist.clear();
				}
				activeTknList.clear();
			}
			sts = STS_METHOD_PARAM ;
		}


	}
	/**
	 *	@@@@@@@
	 * @param idx
	 * @param tknlist
	 * @return
	 */
//	static private int fieldProc(int idx, ArrayList<Token> tknlist, ArrayList<String> importList, MethodInfo mif, FieldInfo fif ){
//
//	}
	static private int fieldMethodProc(int idx, ArrayList<Token> tknlist, ArrayList<String> importList, MethodInfo mif, FieldInfo fif ){
		final int STS_NONE = 0;
		final int STS_PARSE_START = STS_NONE + 1;
		final int STS_PARAM_START = STS_PARSE_START + 1;
		final int STS_PARAM_END = STS_PARAM_START + 1;
		final int STS_THROWS_START = STS_PARAM_END + 1;
		final int STS_METHOD_BODY_START = STS_THROWS_START + 1;
		final int STS_FIELD_INIT_START = STS_METHOD_BODY_START + 1;

		boolean collectionElement = false ;

		String targetName = "";
		JavaDataType datatype = null ;
		ArrayList<ParamInfo> paramInfolist = null ;
		ArrayList<ClassInfo> classInfolist = null ;
		ThrowsInfo throwsInfo = null ;

		int sts = STS_PARSE_START ;
		int i ;
		int len = tknlist.size();
		int nestLevel = 0 ;

		for(i=idx; i < tknlist.size(); i++){
			Token token = (Token)tknlist.get(i);
			if (token.getTknType()== JParserConst.TKN_TYPE_SEPA){
				if ((token.getTknValue().compareTo(" ") == 0)	||
					(token.getTknValue().compareTo("\t") == 0)){
						continue ;
				}
			}
			if (token.getTknType()== JParserConst.TKN_TYPE_BLOCK_COMMENT){
				continue;
			}
			if (token.getTknType()== JParserConst.TKN_TYPE_JAVA_MODIFIER){
				JavaModifier mod = new JavaModifier(token);
				modlist.add(mod);
			}else if ((token.getTknType()== JParserConst.TKN_TYPE_IDENTIFIER)		||
					  (token.getTknType()== JParserConst.TKN_TYPE_JAVA_BASIC_DATA_TYPE)){

				if ((sts == STS_PARSE_START)  ||
					(sts == STS_PARAM_START))
				{
					if (JParserUtil.isJavaClass(importList,  token.getTknValue())	||
					    (token.getTknType()== JParserConst.TKN_TYPE_JAVA_BASIC_DATA_TYPE)){
						if (collectionElement){
							JavaDataType dt = new JavaDataType();
							dt.setDataTypeName(token.getTknValue());
							datatype.setElementDataType(dt);
						}else{
							datatype = new JavaDataType();
							datatype.setDataTypeName(token.getTknValue()) ;
						}
					}else{
						targetName = token.getTknValue();
					}
				}else if (sts == STS_PARAM_END){
					if 	(token.getTknValue().equals("throws")){
						throwsInfo = new ThrowsInfo();
						sts = STS_THROWS_START ;
					}
				}else if (sts == STS_THROWS_START){
					// throw情報を格納
					datatype = new JavaDataType();
					datatype.setDataTypeName(token.getTknValue()) ;

					//throwsInfo.setClassInfolist(classInfolist);

				}
			}else if (token.getTknType() == JParserConst.TKN_TYPE_SEPA){
				String tkntext = token.getTknValue();

				if (tkntext.equals(";")){
					//field
					ArrayList<JavaModifier> wk_modlist = new ArrayList<JavaModifier>();
					wk_modlist.addAll(modlist);

					fif.setModifierlist(wk_modlist);
					fif.setDataType(datatype);
					fif.setFieldName(targetName);
					modlist.clear();
					mif=null;
					break;

				}else if (tkntext.equals("=")){
					//field @@@@@@ 初期値どうする？？
					ArrayList<JavaModifier> wk_modlist = new ArrayList<JavaModifier>();
					wk_modlist.addAll(modlist);

					fif.setModifierlist(wk_modlist);
					fif.setDataType(datatype);
					fif.setFieldName(targetName);
					modlist.clear();
					mif=null;
					sts = STS_FIELD_INIT_START ;


				}else if (tkntext.equals("(")){
					//method
					ArrayList<JavaModifier> wk_modlist = new ArrayList<JavaModifier>();
					wk_modlist.addAll(modlist);

					mif.setModifierlist(wk_modlist);
					mif.setReturnType(datatype);
					mif.setMethodName(targetName);
					modlist.clear();
//					fif=null;
					sts = STS_PARAM_START ;

//					LogTrace.logout(3, mif.toString());

				}else if (tkntext.equals(",")){
					//
					if (sts == STS_PARAM_START){
						if ((datatype != null) && (targetName.length() > 0)){
							ParamInfo pif = makeParamInfo(datatype, targetName);
							datatype = null ;
							targetName = "" ;
							if (paramInfolist == null){
								paramInfolist = new ArrayList<ParamInfo>();
							}
							paramInfolist.add(pif);
						}
					}else if (sts == STS_THROWS_START){
						if (classInfolist == null){
							classInfolist = new ArrayList<ClassInfo>();
							ClassInfo clsinfo = new ClassInfo();
							clsinfo.setClassName(datatype.getDataTypeName());
							classInfolist.add(clsinfo);
							datatype = null ;
						}
					}

				}else if (tkntext.equals("<")){
					if ((sts == STS_PARAM_START) && (datatype != null)){
						if (isCollection(datatype)){
							collectionElement = true ;
						}
					}
				}else if (tkntext.equals(">")){
					if ((sts == STS_PARAM_START) && (collectionElement)){
						collectionElement = false ;
					}

				}else if (tkntext.equals(")")){
					//
					if (sts == STS_PARAM_START){
						if ((datatype != null) && (targetName.length() > 0)){
							ParamInfo pif = makeParamInfo(datatype, targetName);
							datatype = null ;
							targetName = "" ;

							if (paramInfolist == null){
								paramInfolist = new ArrayList<ParamInfo>();
							}
							paramInfolist.add(pif);
						}
						mif.setParamInfolist(paramInfolist);
						sts = STS_PARAM_END ;
					}
				}else if (tkntext.equals("{")){
					if (sts == STS_THROWS_START){
						if (classInfolist == null){
							classInfolist = new ArrayList<ClassInfo>();
							ClassInfo clsinfo = new ClassInfo();
							clsinfo.setClassName(datatype.getDataTypeName());
							classInfolist.add(clsinfo);
							datatype = null ;

							throwsInfo.setClassInfolist(classInfolist);
							mif.setThrowsInfo(throwsInfo);
						}
						sts = STS_METHOD_BODY_START;
					}
					LogTrace.logout(3, mif.toString());

//					fif=null;
					nestLevel++;

				}else if (tkntext.equals("}")){
					nestLevel--;

					if (nestLevel ==0){
						break ;
					}
				}
			}
		}
		return 0;
	}
	static private boolean isCollection(JavaDataType datatype){
		String[] collist = { "ArrayList", "LinkedList", "List","Set", "SortedSet", "NavigableSet", "Queue", "Deque",
				 "BlockingQueue", "BlockingDeque"};

		Arrays.sort(collist);

		if(Arrays.binarySearch(collist, datatype.getDataTypeName()) >= 0)
		{
			return true ;
		}
		return false ;
	}
	/**
	 *
	 * @param datatype
	 * @param targetName
	 * @return
	 */
	static private ParamInfo makeParamInfo(JavaDataType datatype, String targetName){
		ParamInfo pif = new ParamInfo();
		pif.setDataType(datatype);
		pif.setParamName(targetName);

		return pif ;
	}
	/**
	 *
	 * @param idx
	 * @param tknlist
	 * @param clsinfo
	 * @return
	 */
	static private int classProc(int idx, ArrayList<Token> tknlist){
		final int STS_NONE = 0;
		final int STS_CLASS_BODY = STS_NONE + 1;
		final int STS_CLASS_END = STS_CLASS_BODY + 1;
		final int STS_EXTENDS_NEXT = STS_CLASS_END + 1;
		final int STS_EXTENDS_END = STS_EXTENDS_NEXT + 1;
		final int STS_IMPLEMENTS_NEXT = STS_EXTENDS_END + 1;
//		final int STS_IMPLEMENTS = STS_EXTENDS_END + 1;

		clsinfo = new ClassInfo();

		ArrayList<String> extendsList = new ArrayList<String>();
		String clsname = "" ;
		String extendsName = "";
		int sts = STS_CLASS_BODY ;
		int i ;
		int len = tknlist.size();

		for(i=idx; i < tknlist.size(); i++){
			Token token = (Token)tknlist.get(i);
			if (token.getTknType()== JParserConst.TKN_TYPE_SEPA){
				if ((token.getTknValue().compareTo(" ") == 0)	||
					(token.getTknValue().compareTo("\t") == 0)){
						continue ;
				}
			}
			if (token.getTknType()== JParserConst.TKN_TYPE_BLOCK_COMMENT){
				continue;
			}
			if (token.getTknType()== JParserConst.TKN_TYPE_IDENTIFIER){
				if (sts == STS_CLASS_BODY){
					clsname += token.getTknValue();

					clsinfo.setClassName(clsname);
					if (modlist.size()>0){
						String modname = modlist.get(0).getTkn().getTknValue();
						clsinfo.setModifier(modname);
						//修飾子リストをクリア
						modlist.clear();
					}
					sts = STS_CLASS_END;
				}
				else if (sts == STS_EXTENDS_NEXT){
					clsname = token.getTknValue();

					GetClassNameReturn ret = getClassName(i, tknlist, clsname);
					clsname = ret.clsname;
					i = ret.i;

					ClassInfo baseClass = new ClassInfo();
					baseClass.setClassName(clsname);

					clsinfo.setBaseClass(baseClass);
					sts = STS_EXTENDS_END;
				}
				else if (sts == STS_IMPLEMENTS_NEXT){
					clsname = token.getTknValue();
					if (!clsname.equals(",")){
						//次のトークン取得
						GetClassNameReturn ret = getClassName(i, tknlist, clsname);
						clsname = ret.clsname;
						i = ret.i;

						ClassInfo impClass = new ClassInfo();
						impClass.setClassName(clsname);

						ArrayList<ClassInfo> implist = clsinfo.getImplementsList();
						implist.add(impClass);
						clsinfo.setImplementsList(implist);
					}
				}
			}
			if (token.getTknType()== JParserConst.TKN_TYPE_JAVA_WORD){
				if (token.getTknValue().compareTo("extends") == 0){
						sts = STS_EXTENDS_NEXT;
				}else if (token.getTknValue().compareTo("implements") == 0){
					sts = STS_IMPLEMENTS_NEXT;
				}
			}
			if (token.getTknValue().compareTo("{") == 0){
				break;
			}
		}

		return i ;

	}
	static private GetClassNameReturn getClassName(int i, ArrayList<Token> tknlist, String clsname){

		//次のトークン取得
		int offset = 1 ;
		Token clstkn = (Token)tknlist.get(i + offset);
		if (clstkn.getTknType()==JParserConst.TKN_TYPE_SEPA){
			if (clstkn.getTknValue().equals(".")){
			do{
				offset++;
				clstkn = (Token)tknlist.get(i + offset);
				if (clstkn.getTknType()== JParserConst.TKN_TYPE_IDENTIFIER){
					clsname += ".";
					clsname += clstkn.getTknValue();
				}
				offset++;
				clstkn = (Token)tknlist.get(i + offset);
			}while(clstkn.getTknValue().equals("."));
			i += (offset - 1);
			clstkn = (Token)tknlist.get(i);
			}
		}

		GetClassNameReturn ret = new GetClassNameReturn();
		ret.i = i ;
		ret.clsname = clsname ;

		return ret ;
	}
	/**
	 * package解析
	 * @param i　処理中のトークンインデックス　packageトークンの次を示す
	 * @param tknlist
	 */
	static private int packageProc(int idx, ArrayList<Token> tknlist){

		String pkgname = "" ;
		pkg = new Package();

		int i ;
		for(i=idx; i < tknlist.size(); i++){
			Token token = (Token)tknlist.get(i);
			if (token.getTknType()== JParserConst.TKN_TYPE_SEPA){
				if ((token.getTknValue().compareTo(" ") == 0)	||
					(token.getTknValue().compareTo("\t") == 0)){
						continue ;
				}
				if (token.getTknValue().compareTo(";") == 0){
					break;
				}
			}
			pkgname += token.getTknValue();
		}
		pkg.setPkgname(pkgname);

		return i ;
	}
	/**
	 * import解析
	 * @param idx　処理中のトークンインデックス　importトークンの次を示す
	 * @param tknlist
	 * @param pkg
	 * @return
	 */
	static private int importProc(int idx, ArrayList<Token> tknlist){

		String typename = "" ;

		int i ;
		for(i=idx; i < tknlist.size(); i++){
			Token token = (Token)tknlist.get(i);
			if (token.getTknType()== JParserConst.TKN_TYPE_SEPA){
				if ((token.getTknValue().compareTo(" ") == 0)	||
					(token.getTknValue().compareTo("\t") == 0)){
						continue ;
				}
				if (token.getTknValue().compareTo(";") == 0){
					break;
				}
			}
			typename += token.getTknValue();
		}
		ArrayList<String> importList = pkg.getImportList();
		importList.add(typename);
		pkg.setImportList(importList);

		return i ;
	}
}
