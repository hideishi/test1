package app;

import java.util.Iterator;

public class Token {
	private int type ;
	private String value ;
	private JavaDataType datatype ;

	public Token(){
	}
	public Token(int type, String value){
		this.type = type ;
		this.value = value ;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public JavaDataType getDatatype() {
		return datatype;
	}
	public void setDatatype(JavaDataType datatype) {
		this.datatype = datatype;
	}

	public String toString(){
		StringBuilder sbf = new StringBuilder();

		sbf.append("type = ").append(type).append(System.getProperty("line.separator"));
		sbf.append("value = ").append(value).append(System.getProperty("line.separator"));
		if (datatype != null){
			sbf.append("datatype = ").append(datatype.toString()).append(System.getProperty("line.separator"));
		}
		return sbf.toString() ;

	}

}
