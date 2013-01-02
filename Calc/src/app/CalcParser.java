package app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

//import common.JParserConst;
import common.exception.OtherAppException;
import common.util.LogTrace;

public class CalcParser {
	private Stack<ArrayList<OperatorInfo>> expStack = new Stack<ArrayList<OperatorInfo>>();
	// private Stack idxStack = new Stack();
	int idx = 0;

	/**
	 * 式の解析処理
	 * @param tknlist
	 */
	public void parse(ArrayList<Token> tknlist) {
		LogTrace.logout(3, "*** parse start ***");
		LogTrace.logout(3, "tknlist.size() = " + tknlist.size());

		int[] idxArray = new int[1];
		ArrayList<OperatorInfo> explist = new ArrayList<OperatorInfo>();
		expStack.push(explist);

		OperatorInfo opinfo = new OperatorInfo();

		if (tknlist.size() > 1) {
			do {
				// LogTrace.logout(3, "idx = " + idxArray[0]);
				int ret = createOperatorList(tknlist, explist, idxArray, opinfo);
				if (ret == 9) {
					break;
				}
			} while (idxArray[0] < tknlist.size());
		}
		// 確認
		// ---------------------------------------------------------------------
		LogTrace.logout(3, "opinfo.getLeft().getOpe().getValue() = "
				+ opinfo.getLeft().getOpe().getValue());
		// ---------------------------------------------------------------------

		LogTrace.logout(3, "*** parse end ***");
	}

	/**
	 * オペレータ情報リスト作成
	 *
	 * @param tknlist 	in 		式を構成するトークンリスト
	 * @param explist 	in/out 	オペレータ情報リスト
	 * @param idxArray　in/out 	トークンリスト参照時の添え字　※出力パラメータにするために、配列とした
	 * @param opinfo	in 		ひとつ前に作成したオペレータ情報
	 */
	private int createOperatorList(ArrayList<Token> tknlist,
			ArrayList<OperatorInfo> explist, int[] idxArray, OperatorInfo opinfo) {

		int ret = 0;
		try {
			OperatorInfo nextOpinfo = new OperatorInfo();

			Token tkn = (Token) tknlist.get(idx);
			if (tkn.getValue().equals("(")) {
				idx++;
				explist = new ArrayList<OperatorInfo>();

				OperatorInfo opinfo2 = new OperatorInfo();
				do {
					ret = createOperatorList(tknlist, explist, idxArray,
							opinfo2);
				} while (ret == 0);

				// 退避していた１階層上のexplistを取得
				explist = (ArrayList) expStack.pop();

				nextOpinfo.setLeft(opinfo2.getLeft());

			} else {
				nextOpinfo.setLeft(new OperatorInfo(tknlist.get(idx++)));
			}
			if (tknlist.size() <= idx) {
				explist.add(nextOpinfo);
				idxArray[0] = idx;
				// 式の評価
				OperatorInfo opinfo_wk = evaluate(explist);// opeの設定は？　@@@@@@
				// 出力オペレータ情報を設定
				opinfo.setLeft(opinfo_wk.getLeft());

				return 9;
			}
			ret = 0;
			Token tkn2 = (Token) tknlist.get(idx);
			if (tkn2.getValue().equals(")")) {
				explist.add(nextOpinfo);
				// 式の評価
				OperatorInfo opinfo_wk = evaluate(explist);// opeの設定は？　@@@@@@
				// 出力オペレータ情報を設定
				opinfo.setLeft(opinfo_wk.getLeft());

				ret = 9;
				idx++;

			} else {
				nextOpinfo.setOpe(tknlist.get(idx++));
				explist.add(nextOpinfo);
			}
			idxArray[0] = idx;
			// return nextOpinfo ;
		} catch (OtherAppException ex) {
			//LogTrace.logout(3, ex.getMessage());
			ret = 9 ;
		}
		return ret;
	}

	/**
	 *
	 * @param explist
	 * @return
	 * @throws OtherAppException
	 */
	private OperatorInfo evaluate(ArrayList<OperatorInfo> explist)
			throws OtherAppException {
		OperatorInfo opinfo_rslt = null;

		while (CalcParserUtil.isExistOperator(explist, "*")) {
			ArrayList<OperatorInfo> explist_new = new ArrayList<OperatorInfo>();
			CalcParserUtil.multiplyDevProc(explist, explist_new);
			explist = explist_new;
		}
		while (CalcParserUtil.isExistOperator(explist, "/")) {
			ArrayList<OperatorInfo> explist_new = new ArrayList<OperatorInfo>();
			CalcParserUtil.multiplyDevProc(explist, explist_new);
			explist = explist_new;
		}
		while (CalcParserUtil.isExistOperator(explist, "+")) {
			// 加算を行う
			ArrayList<OperatorInfo> explist_new = new ArrayList<OperatorInfo>();
			CalcParserUtil.plusMinusProc(explist, explist_new);
			explist = explist_new;
		}
		while (CalcParserUtil.isExistOperator(explist, "-")) {
			// 減算を行う
			ArrayList<OperatorInfo> explist_new = new ArrayList<OperatorInfo>();
			CalcParserUtil.plusMinusProc(explist, explist_new);
			explist = explist_new;
		}

		return explist.get(0);
	}

}
