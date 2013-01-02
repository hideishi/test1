package common.dbutil;

import java.sql.Connection;
import java.sql.SQLException;

import common.dbutil.DbUtilCommon;
import common.dbutil.ManageDB;

public class ExecDDL {
	static public void exec(String sql){
		DbUtilCommon dbc = new DbUtilCommon(3, "conf/dbconfig.xml");
		ManageDB mdb = new ManageDB(dbc.getDriver(), dbc.getUrl(), dbc.getUser(), dbc.getPassword());

		// コネクションを接続（自動コミットモードＯＦＦ）
		Connection conn;
		try {
			conn = mdb.open();

			mdb.executeUpdate(sql);

			mdb.getStatement().close();

			mdb.close();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
