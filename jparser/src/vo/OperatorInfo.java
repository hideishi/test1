package vo;

public class OperatorInfo {
	private Token operator ;
	private Token left ;
	private Token right ;

	public Token getOperator() {
		return operator;
	}
	public void setOperator(Token operator) {
		this.operator = operator;
	}
	public Token getLeft() {
		return left;
	}
	public void setLeft(Token left) {
		this.left = left;
	}
	public Token getRight() {
		return right;
	}
	public void setRight(Token right) {
		this.right = right;
	}


}
