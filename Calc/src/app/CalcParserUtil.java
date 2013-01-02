package app;

import java.util.ArrayList;
import java.util.Iterator;

import common.JParserConst;
import common.exception.OtherAppException;
import common.util.LogTrace;

public class CalcParserUtil {
	/**
	 * オペレータ情報リスト(explist)に、指定されたオペレータ（optxt）が存在するか判定する
	 * @param explist　オペレータ情報リスト
	 * @param optxt　検索するオペレータ文字列　ex."+","-","*", etc
	 * @return true：存在する、false：存在しない
	 */
	static public boolean isExistOperator(ArrayList<OperatorInfo> explist, String optxt) {
		Token ope = null;

		Iterator<OperatorInfo> itr = explist.iterator();
		while (itr.hasNext()) {
			OperatorInfo opinfo = itr.next();
			ope = opinfo.getOpe();
			if ((ope != null) && (ope.getValue().equals(optxt))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * オペレータ情報リスト(explist)に、"+"オペレータが存在するか判定する
	 * @param explist オペレータ情報リスト
	 * @return true：存在する、false：存在しない
	 */
	static public boolean isExistPlus(ArrayList<OperatorInfo> explist) {
		Token ope = null;

		Iterator<OperatorInfo> itr = explist.iterator();
		while (itr.hasNext()) {
			OperatorInfo opinfo = itr.next();
			ope = opinfo.getOpe();
			if ((ope != null) && (ope.getValue().equals("+"))) {// 乗算の場合
				return true;
			}
		}
		return false;
	}
	/**
	 * オペレータ情報リスト(explist)に、"-"オペレータが存在するか判定する
	 * @param explist オペレータ情報リスト
	 * @return true：存在する、false：存在しない
	 */
	static public boolean isExistMinus(ArrayList<OperatorInfo> explist) {
		Token ope = null;

		Iterator<OperatorInfo> itr = explist.iterator();
		while (itr.hasNext()) {
			OperatorInfo opinfo = itr.next();
			ope = opinfo.getOpe();
			if ((ope != null) && (ope.getValue().equals("-"))) {// 減算の場合
				return true;
			}
		}
		return false;
	}
	/**
	 * オペレータ情報リスト(explist)に、"*"オペレータが存在するか判定する
	 * @param explist オペレータ情報リスト
	 * @return true：存在する、false：存在しない
	 */
	static public boolean isExistMultiply(ArrayList<OperatorInfo> explist) {
		Token ope = null;

		Iterator<OperatorInfo> itr = explist.iterator();
		while (itr.hasNext()) {
			OperatorInfo opinfo = itr.next();
			ope = opinfo.getOpe();
			if ((ope != null) && (ope.getValue().equals("*"))) {// 乗算の場合
				return true;
			}
		}
		return false;
	}

	static public boolean isExistDevision(ArrayList<OperatorInfo> explist) {
		Token ope = null;

		Iterator<OperatorInfo> itr = explist.iterator();
		while (itr.hasNext()) {
			OperatorInfo opinfo = itr.next();
			ope = opinfo.getOpe();
			if ((ope != null) && (ope.getValue().equals("/"))) {// 除算の場合
				return true;
			}
		}
		return false;
	}

	/**
	 * 乗算＆除算処理
	 *
	 * @param explist
	 * @param explist_new
	 * @throws OtherAppException
	 */
	static public void multiplyDevProc(ArrayList<OperatorInfo> explist, ArrayList<OperatorInfo> explist_new) throws OtherAppException {
		Token ope = null;
		OperatorInfo opiLeft = null;
		OperatorInfo opiRight = null;
		OperatorInfo opiLeftNext = null;
		OperatorInfo opiRightNext = null;

		String left = null;
		String right = "";

		for(int i=0; i < explist.size(); i++) {
			OperatorInfo opinfo_wk = explist.get(i);
			ope = opinfo_wk.getOpe();

//			LogTrace.logout(3, "opinfo_wk.getOpe() = "	+ ope.getValue());

			if (opiLeft == null) {
				opiLeft = opinfo_wk.getLeft();
			} else {
				opiRight = opinfo_wk.getLeft();
			}
			if ((opiRight != null) && (opiRight.getOpe() != null)) {
				left = opiRight.getOpe().getValue();
			} else {
				left = opiLeft.getOpe().getValue();
				LogTrace.logout(3, "opiLeft.getOpe().getValue() = "
						+ opiLeft.getOpe().getValue());
			}

			if (ope != null) {
				if (ope.getValue().equals("*") || ope.getValue().equals("/")){// 乗算 or 除算の場合
					i++;
					OperatorInfo opinfo_nxt = explist.get(i);

					opiLeftNext = opinfo_nxt.getLeft();
					right = "";
					if (opiLeftNext != null) {
						if (opiLeftNext.getOpe() != null){
							right = opiLeftNext.getOpe().getValue();
						}else{
							String msg =  "オペレータ情報が不正です。" + "opiLeftNext.getOpe() is null.";
							LogTrace.logout(3,msg);
							throw new OtherAppException(msg);
						}
					}

					int value = 0;
					if (ope.getValue().equals("*")) {// 乗算の場合
						if (left == null) {
							left = "0";
						}
						if (right == null) {
							right = "0";
						}
						value = multiply(left, right);
						LogTrace.logout(3, "value = " + left + " * " + right);
					} else if (ope.getValue().equals("/")) {// 除算の場合
						if (left == null) {
							left = "0";
						}
						if (right == null) {
							right = "0";
						}
						value = div(left, right);
						LogTrace.logout(3, "value = " + value);
					}

					OperatorInfo opinfo = makeOpinfo(value, opinfo_nxt.getOpe());

					explist_new.add(opinfo);
				} else {
					if (ope.getValue().equals("+")) {// 加算の場合
						if (left == null) {
							left = "0";
						}
						if (right == null) {
							right = "0";
						}
						// value = add(left, right);
						LogTrace.logout(3, "value = " + left + " + " + right);
					} else if (ope.getValue().equals("-")) {// 減算の場合
						if (left == null) {
							left = "0";
						}
						if (right == null) {
							right = "0";
						}
						// value = sub(left, right);
						LogTrace.logout(3, "value = " + left + " - " + right);
					}
					explist_new.add(opinfo_wk);
				}
			}else{
				explist_new.add(opinfo_wk);

			}
		}

	}
	/**
	 *
	 * @param explist
	 * @param explist_new
	 * @throws OtherAppException
	 */
	static public void plusMinusProc(ArrayList<OperatorInfo> explist, ArrayList<OperatorInfo> explist_new) throws OtherAppException {
		Token ope = null;
		OperatorInfo opiLeft = null;
		OperatorInfo opiRight = null;
		OperatorInfo opiLeftNext = null;
		OperatorInfo opiRightNext = null;

		String left = null;
		String right = "";

		for(int i=0; i < explist.size(); i++) {
			OperatorInfo opinfo_wk = explist.get(i);
			ope = opinfo_wk.getOpe();

			if (opiLeft == null) {
				opiLeft = opinfo_wk.getLeft();
			} else {
				opiRight = opinfo_wk.getLeft();
			}
			if ((opiRight != null) && (opiRight.getOpe() != null)) {
				left = opiRight.getOpe().getValue();
			} else {
				left = opiLeft.getOpe().getValue();
				LogTrace.logout(3, "opiLeft.getOpe().getValue() = "
						+ opiLeft.getOpe().getValue());
			}

			if (ope != null) {
				if (ope.getValue().equals("+") || ope.getValue().equals("-")){// 加算 or 減算の場合
					i++;
					OperatorInfo opinfo_nxt = explist.get(i);

					opiLeftNext = opinfo_nxt.getLeft();
					if (opiLeftNext != null) {
						right = opiLeftNext.getOpe().getValue();
					}else{
						String msg =  "オペレータ情報が不正です。" + "opiLeftNext.getOpe() is null.";
						LogTrace.logout(3,msg);
						throw new OtherAppException(msg);

					}

					int value = 0 ;
					if (ope.getValue().equals("+")) {// 加算の場合
						if (left == null) {
							left = "0";
						}
						if (right == null) {
							right = "0";
						}
						value = add(left, right);

					} else if (ope.getValue().equals("-")) {// 減算の場合
						if (left == null) {
							left = "0";
						}
						if (right == null) {
							right = "0";
						}
						value = sub(left, right);

					}
					OperatorInfo opinfo = makeOpinfo(value, opinfo_nxt.getOpe());
					explist_new.add(opinfo);
				}
			}else{
				explist_new.add(opinfo_wk);

			}
		}

	}

	/**
	 * 加算
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	static private int add(String left, String right) {
		int lv = (new Integer(left)).intValue();
		int rv = (new Integer(right)).intValue();

		return lv + rv;
	}

	/**
	 * 減算
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	static private int sub(String left, String right) {
		int lv = (new Integer(left)).intValue();
		int rv = (new Integer(right)).intValue();

		return lv - rv;
	}

	/**
	 * 乗算
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	static private int multiply(String left, String right) {
		int lv = (new Integer(left)).intValue();
		int rv = (new Integer(right)).intValue();

		return lv * rv;
	}

	/**
	 * 除算
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	static private int div(String left, String right) {
		int lv = (new Integer(left)).intValue();
		int rv = (new Integer(right)).intValue();

		return lv / rv;
	}

	/**
	 * オペレータ情報作成
	 *
	 * @param value
	 * @param nxtope
	 * @return
	 */
	static private OperatorInfo makeOpinfo(int value, Token nxtope) {
		OperatorInfo opinfo = new OperatorInfo();

		OperatorInfo opiLeft = new OperatorInfo();
		Token tkn = new Token(JParserConst.TKN_TYPE_NUMERIC, String
				.valueOf(value));
		opiLeft.setOpe(tkn);

		opinfo.setLeft(opiLeft);
		opinfo.setOpe(nxtope);

		return opinfo;

	}

	/**
	 * オペレータ情報リストの確認
	 *
	 * @param opinfo
	 * @return
	 */
	static public void showOperatorInfo(ArrayList<OperatorInfo> explist) {
		LogTrace.logout(3, "*** showOperatorInfo start ***");

		Iterator<OperatorInfo> itr = explist.iterator();
		while (itr.hasNext()) {
			OperatorInfo opinfo = itr.next();

			Token ope = opinfo.getOpe();
			OperatorInfo opiLeft = opinfo.getLeft();
			OperatorInfo opiRight = opinfo.getRight();

			if (ope != null) {
				LogTrace.logout(3, "オペレータ:" + ope.getValue());
			}
			if (opiLeft != null) {
				Token tkn = opiLeft.getOpe();
				LogTrace.logout(3, "左辺:" + tkn.getValue());
			}
			if (opiRight != null) {
				Token tkn = opiRight.getOpe();
				LogTrace.logout(3, "右辺: " + tkn.getValue());
			}
		}

		LogTrace.logout(3, "*** showOperatorInfo end ***");
	}

}
