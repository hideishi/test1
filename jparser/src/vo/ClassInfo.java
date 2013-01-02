package vo;

import java.util.ArrayList;
import java.util.Iterator;


public class ClassInfo {
	private final String modifierTbl[] = {"public", "abstract", "final"};

	private String modifier ;
	private String className ;						//

	private ArrayList<ClassInfo>implementsList
						= new ArrayList<ClassInfo>() ;					//スーパークラス

	private ClassInfo baseClass ;										//スーパークラス

	private ArrayList<FieldInfo> fieldInfoList
						= new ArrayList<FieldInfo>();
	private ArrayList<MethodInfo> methodInfoList
						= new ArrayList<MethodInfo>();

	public ClassInfo getBaseClass() {
		return baseClass;
	}
	public void setBaseClass(ClassInfo baseClass) {
		this.baseClass = baseClass;
	}
	public ArrayList<ClassInfo> getImplementsList() {
		return implementsList;
	}
	public void setImplementsList(ArrayList<ClassInfo> implementsList) {
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
	/**
	 *
	 */
	public String toString(){
		StringBuilder sbf = new StringBuilder();

		sbf.append("modifier = ").append(modifier).append(System.getProperty("line.separator"));
		sbf.append("className = ").append(className).append(System.getProperty("line.separator"));
		sbf.append("baseClass = ").append(baseClass.getClassName()).append(System.getProperty("line.separator"));

		Iterator<FieldInfo> itr = fieldInfoList.iterator();
		while(itr.hasNext()){
			FieldInfo fif = itr.next();
			sbf.append(fif.toString()).append(System.getProperty("line.separator"));
		}


		return sbf.toString() ;
	}
}
