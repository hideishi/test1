package common.util;

import java.util.ArrayList;
import java.util.List;

public class ClassNameConvertor {

	static ArrayList<String> keytblList ;

	static ArrayList<String> classNameTblList ;

	static String[] keytbl = {"String", "Integer", "Timestamp", "Date",
			"BigDecimal","Float", "Double"};

	static String[] classNameTbl =
			{
					"java.lang.String", "java.lang.Integer",
					"java.sql.Timestamp", "java.sql.Date", "java.math.BigDecimal",
					"java.lang.Float", "java.lang.Double"
			};

	static public List getKeytblList(){
		return keytblList ;
	}
	static public List getClassNameTblList(){
		return classNameTblList ;
	}

	static {
		keytblList = new ArrayList<String>();

		for(int i=0; i < keytbl.length; i++){
			keytblList.add(keytbl[i]);
		}
		classNameTblList = new ArrayList<String>();
		for(int i=0; i < classNameTbl.length; i++){
			classNameTblList.add(classNameTbl[i]);
		}
	}
	/**
	 * クラス名を完全修飾クラス名に変換
	 *
	 * @param keyname
	 * @return
	 */
	static public String db2ToJava(String keyname) {

		for (int i = 0; i < keytblList.size(); i++) {
			if (keyname.compareTo((String)keytblList.get(i)) == 0) {
				return (String)classNameTblList.get(i);
			}
		}
		return "";
	}

}
