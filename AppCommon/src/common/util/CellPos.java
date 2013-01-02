package common.util;

public class CellPos {
	private int row ;		//行位置　		１～
	private int col ;		//カラム位置　	１～
	
	public CellPos(){
		
	}
	public CellPos(int row, int col){
		this.row = row ;
		this.col = col ;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	
}
