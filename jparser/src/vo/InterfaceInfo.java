package vo;

import java.util.ArrayList;


public class InterfaceInfo {
	private final String modifierTbl[] = {"public"};

	private String modifier ;
	private String className ;						//

	private ArrayList<InterfaceInfo>implementsList
						= new ArrayList<InterfaceInfo>() ;					//スーパークラス

	private InterfaceInfo baseClass ;										//スーパークラス

	private ArrayList<FieldInfo> fieldInfoList
						= new ArrayList<FieldInfo>();
	private ArrayList<MethodInfo> methodInfoList
						= new ArrayList<MethodInfo>();

	public InterfaceInfo getBaseClass() {
		return baseClass;
	}
	public void setBaseClass(InterfaceInfo baseClass) {
		this.baseClass = baseClass;
	}
	public ArrayList<InterfaceInfo> getImplementsList() {
		return implementsList;
	}
	public void setImplementsList(ArrayList<InterfaceInfo> implementsList) {
		this.implementsList = implementsList;
	}
	public String getModifier() {
		return modifier;
	}


	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}



	public ArrayList<FieldInfo> getFieldInfoList() {
		return fieldInfoList;
	}


	public void setFieldInfoList(ArrayList<FieldInfo> fieldInfoList) {
		this.fieldInfoList = fieldInfoList;
	}


	public ArrayList<MethodInfo> getMethodInfoList() {
		return methodInfoList;
	}


	public void setMethodInfoList(ArrayList<MethodInfo> methodInfoList) {
		this.methodInfoList = methodInfoList;
	}


	public String[] getModifierTbl() {
		return modifierTbl;
	}


	/**
	 * クラス修飾子判定
	 * @param txt
	 * @return
	 */
	public boolean isModifier(String txt){

		for(int i=0; i < modifierTbl.length; i++){
			if (txt.compareTo(modifierTbl[i])==0){
				return true ;
			}
		}
		return false ;
	}
}
