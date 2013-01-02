package vo;

import java.util.ArrayList;
import java.util.Iterator;



public class MethodInfo {
	static private String[] modifierTbl = {"abstract", "static", "final", "synchronized", "native"};

	private ArrayList<JavaModifier> modifierlist = null ; //new ArrayList<JavaModifier>();

	private JavaDataType returnType ;
	private String methodName ;
	private ArrayList<ParamInfo> paramInfolist = null; 	//new ArrayList<ParamInfo>();
	private ThrowsInfo throwsInfo = null ;


	public static String[] getModifierTbl() {
		return modifierTbl;
	}
	public static void setModifierTbl(String[] modifierTbl) {
		MethodInfo.modifierTbl = modifierTbl;
	}
	public ArrayList<JavaModifier> getModifierlist() {
		return modifierlist;
	}
	public void setModifierlist(ArrayList<JavaModifier> modifierlist) {
		this.modifierlist = modifierlist;
	}

	public JavaDataType getReturnType() {
		return returnType;
	}


	public void setReturnType(JavaDataType resultType) {
		this.returnType = resultType;
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public ArrayList<ParamInfo> getParamInfolist() {
		return paramInfolist;
	}


	public void setParamInfolist(ArrayList<ParamInfo> paramInfolist) {
		this.paramInfolist = paramInfolist;
	}


	public ThrowsInfo getThrowsInfo() {
		return throwsInfo;
	}


	public void setThrowsInfo(ThrowsInfo throwsInfo) {
		this.throwsInfo = throwsInfo;
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
	/**
	 *
	 */
	public String toString(){
		StringBuilder sbf = new StringBuilder();

		if (methodName != null){
			sbf.append("methodName = ").append(methodName).append(System.getProperty("line.separator"));
		}
		if (modifierlist != null){
			if (!modifierlist.isEmpty()){
				sbf.append("modifier = ");
				Iterator<JavaModifier> itr = modifierlist.iterator();
				while(itr.hasNext()){
					JavaModifier jmod = itr.next();
					sbf.append(jmod.getTkn().getTknValue()).append(" ");
				}
				sbf.append(System.getProperty("line.separator"));
			}
		}
		if (returnType != null){
			sbf.append("returnType = ").append(returnType.getDataTypeName()).append(System.getProperty("line.separator"));
		}
		//パラメータ情報の展開
		if (paramInfolist != null){
			if (!paramInfolist.isEmpty()){
				Iterator<ParamInfo> paramitr = paramInfolist.iterator();
				while(paramitr.hasNext()){
					ParamInfo pif = paramitr.next();
					sbf.append(pif.getDataType().getDataTypeName()).append(" ");
					if (pif.getDataType().getElementDataType() != null){
						sbf.append("<");
						sbf.append(pif.getDataType().getElementDataType().getDataTypeName());
						sbf.append(">");
					}
					sbf.append(pif.getParamName()).append(", ");
				}
			}
		}
		// throws情報の展開
		if (throwsInfo != null){
			sbf.append(System.getProperty("line.separator"));
			sbf.append("throws ").append(" ");

			ArrayList<ClassInfo> ciflist = throwsInfo.getClassInfolist();

			Iterator<ClassInfo> cifitr = ciflist.iterator();
			while(cifitr.hasNext()){
				ClassInfo cif = cifitr.next();
				sbf.append(cif.getClassName()).append(System.getProperty("line.separator"));
			}
		}
		return sbf.toString();
	}

}
