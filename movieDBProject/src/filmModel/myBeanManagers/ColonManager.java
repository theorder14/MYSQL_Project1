package filmModel.myBeanManagers;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


import Connection.JDBCCloser;
import Connection.JDBConnection;




public class ColonManager {

	private static Connection conn = JDBConnection.getJDBCConnection();
	
	public static String[] getColonTitles(String sql) {
		String[] colonTitles = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			colonTitles = new String[rsmd.getColumnCount()];
			for(int i=0; i<colonTitles.length; i++) {
				colonTitles[i] = rsmd.getColumnName(i+1);
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		} finally {
			JDBCCloser.close(rs, stmt);
		}
		return colonTitles;
	}

}

