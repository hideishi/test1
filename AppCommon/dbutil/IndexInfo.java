package common.dbutil;

/**
 * インデックス情報
 * @author hide
 *
 */
public class IndexInfo {
	private String idxName ;
	private boolean unique ;

	public String getIdxName() {
		return idxName;
	}
	public void setIdxName(String idxName) {
		this.idxName = idxName;
	}
	public boolean isUnique() {
		return unique;
	}
	public void setUnique(boolean unique) {
		this.unique = unique;
	}


}
