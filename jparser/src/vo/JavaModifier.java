package vo;

public class JavaModifier {
	private Token tkn ;

	public JavaModifier(){

	}
	public JavaModifier(Token tkn){
		this.tkn = tkn ;
	}
	public Token getTkn() {
		return tkn;
	}

	public void setTkn(Token tkn) {
		this.tkn = tkn;
	}


}
