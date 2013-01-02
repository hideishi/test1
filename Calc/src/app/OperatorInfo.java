package app;

public class OperatorInfo {
//	private String CRLF = System.getProperty("line.separator");
//	private String indent = "    ";
	private Token ope ;
	private OperatorInfo left ;
	private OperatorInfo right ;

	public OperatorInfo(){

	}
	public OperatorInfo(Token value){
		this.ope = value ;
	}

	public Token getOpe() {
		return ope;
	}
	public void setOpe(Token value) {
		this.ope = value;
	}
	public OperatorInfo getLeft() {
		return left;
	}
	public void setLeft(OperatorInfo left) {
		this.left = left;
	}
	public OperatorInfo getRight() {
		return right;
	}
	public void setRight(OperatorInfo right) {
		this.right = right;
	}
//	public String toString(){
//		StringBuilder sbf = new StringBuilder();
//
//		if (ope != null){
//			sbf.append("value = ").append(ope.getValue()).append(CRLF);
//			sbf.append("type = ").append(ope.getType()).append(CRLF);
//		}else{
//			sbf.append("ope = null").append(CRLF);
//		}
//		if (left != null){
//			sbf.append("left : ").append(CRLF);
//			sbf.append("  ope : ");
//			sbf.append("    value = ").append(left.getOpe().getValue()).append(CRLF);
//			sbf.append("    type  = ").append(left.getOpe().getType()).append(CRLF);
//			sbf.append("  left : ");
//			sbf.append("    ope : ");
//			sbf.append("      value = ").append(left.getLeft().getOpe().getValue()).append(CRLF);
//			sbf.append("      type  = ").append(left.getLeft().getOpe().getType()).append(CRLF);
//			sbf.append("  right : ");
//			sbf.append("    ope : ");
//			sbf.append("      value = ").append(left.getRight().getOpe().getValue()).append(CRLF);
//			sbf.append("      type  = ").append(left.getRight().getOpe().getType()).append(CRLF);
//
//		}else{
//			sbf.append("left = null").append(CRLF);
//		}
//		if (right != null){
//			sbf.append("right :" + toString()).append(CRLF);
//			sbf.append("  ope :");
//			sbf.append("    value = ").append(right.getOpe().getValue()).append(CRLF);
//			sbf.append("    type  = ").append(right.getOpe().getType()).append(CRLF);
//			sbf.append("  left : ");
//			sbf.append("    ope : ");
//			sbf.append("      value = ").append(right.getLeft().getOpe().getValue()).append(CRLF);
//			sbf.append("      type  = ").append(right.getLeft().getOpe().getType()).append(CRLF);
//		}else{
//			sbf.append("right = null").append(CRLF);
//		}
//		return sbf.toString();
//	}
}
