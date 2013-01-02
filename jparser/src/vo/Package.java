package vo;

import java.util.ArrayList;
import java.util.Iterator;

public class Package {
	private String pkgname ;
	private ArrayList<String> importList = new ArrayList<String>();

	public String getPkgname() {
		return pkgname;
	}
	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}
	public ArrayList<String> getImportList() {
		return importList;
	}
	public void setImportList(ArrayList<String> importList) {
		this.importList = importList;
	}

	public String toString(){
		StringBuilder sbf = new StringBuilder();

		sbf.append("pkgname = " + pkgname );
		sbf.append(System.getProperty("line.separator"));

		Iterator<String> itr = importList.iterator();
		while(itr.hasNext()){
			String item = itr.next();
			sbf.append("import = " + item);
			sbf.append(System.getProperty("line.separator"));

		}
		return sbf.toString();
	}
}
