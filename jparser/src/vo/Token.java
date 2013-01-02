package vo;

import java.util.Iterator;

public class Token {
	private int tknType ;
	private int opeType ;
	private String tknValue ;
	private JavaDataType datatype ;

	public Token(){
	}
	public Token(int tknType, String value){
		this.tknType = tknType ;
		this.tknValue = value ;
	}
	public Token(int tknType, int opeType,String value){
		this.tknType = tknType ;
		this.opeType = opeType ;
		this.tknValue = value ;
	}
	public int getTknType() {
		return tknType;
	}
	public void setTknType(int tknType) {
		this.tknType = tknType;
	}

	public int getOpeType() {
		return opeType;
	}
	public void setOpeType(int opeType) {
		this.opeType = opeType;
	}
	public String getTknValue() {
		return tknValue;
	}
	public void setTknValue(String tknValue) {
		this.tknValue = tknValue;
	}
	public JavaDataType getDatatype() {
		return datatype;
	}
	public void setDatatype(JavaDataType datatype) {
		this.datatype = datatype;
	}

	public String toString(){
		StringBuilder sbf = new StringBuilder();

		sbf.append("tknType = ").append(tknType).append(System.getProperty("line.separator"));
		sbf.append("opeType = ").append(opeType).append(System.getProperty("line.separator"));
		sbf.append("tknValue = ").append(tknValue).append(System.getProperty("line.separator"));
		if (datatype != null){
			sbf.append("datatype = ").append(datatype.toString()).append(System.getProperty("line.separator"));
		}
		return sbf.toString() ;

	}

}
