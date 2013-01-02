package vo;

import java.util.ArrayList;
import java.util.Iterator;


public class FieldInfo {
	static private String[] modifierTbl = {"final", "static", "transient", "volatile"};

	private ArrayList<JavaModifier> modifierlist
			= new ArrayList<JavaModifier>();

	private JavaDataType dataType ;
	private String fieldName ;
	private Token valueToken = null;


	public static String[] getModifierTbl() {
		return modifierTbl;
	}


	public static void setModifierTbl(String[] modifierTbl) {
		FieldInfo.modifierTbl = modifierTbl;
	}


	public ArrayList<JavaModifier> getModifierlist() {
		return modifierlist;
	}


	public void setModifierlist(ArrayList<JavaModifier> modifierlist) {
		this.modifierlist = modifierlist;
	}


	public JavaDataType getDataType() {
		return dataType;
	}


	public void setDataType(JavaDataType dataType) {
		this.dataType = dataType;
	}


	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Token getValueToken() {
		return valueToken;
	}
	public void setValueToken(Token valueToken) {
		this.valueToken = valueToken;
	}


	/**
	 *
	 * @param txt
	 * @return
	 */
	static public boolean isModifier(String txt){

		for(int i=0; i < modifierTbl.length; i++ ){
			if (txt.compareTo(modifierTbl[i])==0){
				return true ;
			}
		}
		return false ;
	}
	public String toString(){
		StringBuilder sbf = new StringBuilder();

		Iterator<JavaModifier> itr = modifierlist.iterator();
		while(itr.hasNext()){
			JavaModifier jmod = itr.next();
			sbf.append(jmod.getTkn().getTknValue()).append(" ");
		}
		sbf.append(dataType.getDataTypeName()).append(" ").append(fieldName);
		sbf.append(System.getProperty("line.separator"));

		if (valueToken != null){
			sbf.append("valueToken : ").append(valueToken.toString());
			sbf.append(System.getProperty("line.separator"));
		}
		return sbf.toString() ;
	}
}
