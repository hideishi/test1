package vo;

public class VariableDecl {
	private Token typeToken ;
	private Token varToken ;
	private Token valueToken = null ;

	public Token getTypeToken() {
		return typeToken;
	}
	public void setTypeToken(Token typeToken) {
		this.typeToken = typeToken;
	}
	public Token getVarToken() {
		return varToken;
	}
	public void setVarToken(Token varToken) {
		this.varToken = varToken;
	}
	public Token getValueToken() {
		return valueToken;
	}
	public void setValueToken(Token valueToken) {
		this.valueToken = valueToken;
	}


}
