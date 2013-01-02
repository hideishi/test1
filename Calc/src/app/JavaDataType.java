package app;

import java.util.Iterator;

public class JavaDataType {
	private String dataTypeName ;
	private boolean arrayFlg = false ;
	private int length ;
	private JavaDataType elementDataType = null ;

	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public JavaDataType getElementDataType() {
		return elementDataType;
	}
	public void setElementDataType(JavaDataType elementDataType) {
		this.elementDataType = elementDataType;
	}
	public boolean isArrayFlg() {
		return arrayFlg;
	}
	public void setArrayFlg(boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public String toString(){
		StringBuilder sbf = new StringBuilder();

		sbf.append("dataTypeName = ").append(dataTypeName).append(" ");
		if (arrayFlg){
			sbf.append("Array ON").append(System.getProperty("line.separator"));
		}
		sbf.append("length = ").append(length).append(System.getProperty("line.separator"));
		sbf.append("elementDataType = ").append(elementDataType.toString()).append(System.getProperty("line.separator"));

		return sbf.toString() ;

	}
}
