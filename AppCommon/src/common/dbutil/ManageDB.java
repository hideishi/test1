package common.dbutil;

import java.sql.Connection;

/*
 * ManageDB.java
 */
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

//import update.Item;


public class ManageDB {
    private String driver 			;
    private String url 			;
    private String user			;
    private String password		;
	private boolean commit_mode 	= false ;
    private Connection connection = null ;
    private Statement statement = null ;
    private ResultSet resultset;
    private PreparedStatement pstm;

    /**
     * コンストラクタ
     *
     * @param driver ドライバー
     * @param url URL
     * @param user ユーザー名
     * @param password パスワード
     */
    public ManageDB(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * コンストラクタ
     *
     * @param url URL
     * @param user ユーザー名
     * @param password パスワード
     */
    public ManageDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
	/**
	 * コンストラクタ
	 *
	 * @param url URL
	 * @param user ユーザー名
	 * @param password パスワード
	 */
	public ManageDB(String user, String password) {
		this.user = user;
		this.password = password;
	}

    /**
     * 引数なしのコンストラクタ 既定値を使用する
     */
    public ManageDB() {
    }

    /**
     * データベースへの接続を行う
     */
    public synchronized Connection open()
        throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);

        // 自動コミット・モードを設定
        connection.setAutoCommit(commit_mode);

        return connection;
    }

    private Statement createStatement() throws SQLException {
        statement = connection.createStatement();

        return statement;
    }

//    private PreparedStatement prepareStatement(String sql)
//        throws SQLException {
//        pstm = connection.prepareStatement(sql);
//
//        return pstm;
//    }

    /**
     * SQL 文を実行した結果の ResultSet を返す
     *
     * @param sql SQL 文
     */
    public ResultSet getResultSet(String sql) throws SQLException {
        System.out.println("getResultSet: sql = " + sql);
		if (statement == null){
			statement = connection.createStatement();
		}

        return statement.executeQuery(sql);
    }

    /**
     * SQL 文の実行
     *
     * @param sql SQL 文
     */
    public void execute(String sql) throws SQLException {
        statement.execute(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
		createStatement();
        return statement.executeUpdate(sql);
    }

	public int insert(String table_name, String columns, String values)
		throws SQLException {

			String sql = "INSERT into " + table_name + " (" + columns + ")"
				+ " values (" + values + ")" ;

			return executeUpdate(sql);

	}
	public int update(String table_name, LinkedList<Item> itemList, String sql_where)
		throws SQLException {
		String sql = "UPDATE " + table_name + " SET " ;

		StringBuffer sbf = new StringBuffer("");

		Iterator<Item> itr = itemList.iterator();
		while(itr.hasNext()){
			Item item = (Item)itr.next();
			sbf.append(item.getName() + "=" + item.getValue() );
			if (itr.hasNext())
				sbf.append(",");
		}
		sql += (sbf.toString());

		if (sql_where != "") {
			sql += (" where " + sql_where);
		}

		return executeUpdate(sql);
	}
	public int updateBlob(String table_name, LinkedList<Item> itemList, String sql_where)
		throws SQLException {
		String sql = "UPDATE " + table_name + " SET " ;

		StringBuffer sbf = new StringBuffer("");

		Iterator<Item> itr = itemList.iterator();
		while(itr.hasNext()){
			Item item = (Item)itr.next();
			sbf.append(item.getName() + "=" + item.getValue() );
			if (itr.hasNext())
				sbf.append(",");
		}
		sql += (sbf.toString());

		if (sql_where != "") {
			sql += (" where " + sql_where);
		}

		return executeUpdate(sql);
	}

    public int delete(String table_name, String sql_where)
        throws SQLException {
        if (sql_where == "") {
            throw new IllegalArgumentException("DELTE文のwhere句を指定してください。");
        }

        String sql = "DELETE FROM " + table_name + " where " + sql_where;

        System.out.println("sql = " + sql);

        return executeUpdate(sql);
    }
    public Statement getStatement(){
    	return statement ;
    }

    // トランザクションをコミット
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     * データベースへのコネクションのクローズ
     */
    public synchronized void close() throws SQLException {
        if (resultset != null) {
            resultset.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (pstm != null) {
            pstm.close();
        }

        if (connection != null) {
            connection.close();
        }
    }
}
