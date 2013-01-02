package vo;

public class Expression {
	private Expression leftexp ;
	private Expression rightexp ;
	private int operator ;
	private Token valueTkn ;

	public Expression getLeftexp() {
		return leftexp;
	}
	public void setLeftexp(Expression leftexp) {
		this.leftexp = leftexp;
	}
	public Expression getRightexp() {
		return rightexp;
	}
	public void setRightexp(Expression rightexp) {
		this.rightexp = rightexp;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public Token getValueTkn() {
		return valueTkn;
	}
	public void setValueTkn(Token valueTkn) {
		this.valueTkn = valueTkn;
	}


}
