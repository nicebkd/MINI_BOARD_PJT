package jdbc;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBCPUitl {
	Connection conn = null;

	public Connection getConnection() {
		try {
			Context init = new InitialContext();
			Context envContext = (Context) init.lookup("java:comp/env/");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
			System.out.println("연결 성공");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.print("연결에 실패하였습니다.");
		}
		return conn;
	}
}
