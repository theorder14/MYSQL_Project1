package filmModel.myBeanManagers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Connection.JDBCCloser;
import Connection.JDBConnection;
import filmModel.MyBeans.Actor;



public class ActorManager {
	
	private static Connection conn = JDBConnection.getJDBCConnection();
	
	public static Actor getActor(int pkActorId) {
		
		String sql = "SELECT * FROM actor WHERE pk_Actor_Id=?";
		Actor act = new Actor(sql, pkActorId );
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkActorId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				act.setPkActorId(pkActorId);
				act.setActorName(rs.getString("actor_name"));
				act.setAge(rs.getInt("age"));
			}
			return act;
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return null;
		}
	}
	
	
	public static List<Actor> getAllActor() {
		
		List<Actor> actorList = new ArrayList<>();
		String sql = "SELECT * FROM actor" ;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Actor act = new Actor();
				act.setPkActorId(rs.getInt("pk_actor_id"));
				act.setActorName(rs.getString("actor_name"));
				act.setAge(rs.getInt("age"));
				
				actorList.add(act);	
			}
			return actorList;
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return null;	
		}finally {
			JDBCCloser.close(rs, stmt);
		}
	}
	public static boolean updateActor(Actor act) {
		boolean isUpdated = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(act == null) {
			return !isUpdated;
		}
		else {
			String sql = "UPDATE actor SET actor_name=?, age=?" + " WHERE pk_actor_id=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, act.getActorName());
				pstmt.setInt(2, act.getAge());
				pstmt.setInt(3, act.getPkActorId());
				affectedRows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isUpdated;
			}
			
			if(affectedRows==0) {
				System.out.println("Actor incorrect updated . . .");
				return !isUpdated;
			}else {
				System.out.println("Actor correct updated . . .");
				return isUpdated;
			}
		}
	}
	
	public static boolean addActor(Actor act) {
		boolean isAdded = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(act == null) {
			return !isAdded;
		}
		else {
			String sql = "INSERT INTO actor (actor_name, age) VALUES (?,?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, act.getActorName());
				pstmt.setInt(2, act.getAge());
				affectedRows = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isAdded;
			}
			
			if(affectedRows==0) {
				System.out.println("No actor added . . .");
				return !isAdded;
			}else {
				System.out.println("actor added . . .");
				return isAdded;
			}
		}
	}
	
	public static boolean deleteActor(Actor act) {
		
	
		boolean isDeleted = true;
		int affectedRows = 0;
		
		PreparedStatement pstmt = null;
		if(act==null) {
			return !isDeleted;
		}
		else {
			String sql = "DELETE FROM actor WHERE pk_actor_id=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, act.getPkActorId());
				affectedRows = pstmt.executeUpdate();
			}catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isDeleted;
			}
			
			if(affectedRows==0) {
				System.out.println("No actor deleted . . .");
				return !isDeleted;
			}else {
				System.out.println("ctor deleted . . .");
				return isDeleted;
			}
		}
	}
	
	
		public static List<Actor> findActorsByPrefixName(String actorName) {
			List<Actor> actorList = new ArrayList<>();
			String sql = "SELECT * FROM actor WHERE actor_name LIKE ?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, actorName+"%"); 
				rs = pstmt.executeQuery();
				System.out.println("Actor");
				while(rs.next()) {
					Actor act = new Actor();
					act.setPkActorId(rs.getInt("pk_actor_id"));
					act.setActorName(rs.getString("actor_name"));
					act.setAge(rs.getInt("age"));	
					System.out.println(act);
					actorList.add(act);	
					System.out.println("Act");
				}
				return actorList;
				
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return null;	
			}finally {
				JDBCCloser.close(rs, pstmt);
			}
		}
		
	public static int getNumOfRows() {
		String sql = "SELECT * FROM actor";
		int numOfRows = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rs.last();
			numOfRows = rs.getRow();
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return numOfRows;
		} finally {
			JDBCCloser.close(rs, stmt);
		}
		return numOfRows;
	}
	
	public static int getNumOfColons() {
		String sql = "SELECT * FROM actor";
		int numOfColons = 0;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			numOfColons = rsmd.getColumnCount();
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return numOfColons;
		} finally {
			JDBCCloser.close(rs, stmt);
		}
		return numOfColons;
	}
	
	public static String[] getColonTitles() {
		String sql = "SELECT * FROM actor";
		String[] colonTitles;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			colonTitles = new String[getNumOfColons()];
			for(int i=0; i<colonTitles.length; i++) {
				colonTitles[i] = rsmd.getColumnName(i+1);
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return null;
		} finally {
			JDBCCloser.close(rs, stmt);
		}
		return colonTitles;
	}
	public static List<Actor> searchActor(String actorName) {
		List<Actor> actorList = new ArrayList<>();
		String sql = "SELECT * FROM actor WHERE actor_name LIKE ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, actorName+"%"); 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Actor act = new Actor();
				act.setPkActorId(rs.getInt("pk_actor_id"));
				act.setActorName(rs.getString("actor_name"));
				act.setAge(rs.getInt("age"));	
				actorList.add(act);	
			}	
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());	
		}finally {
			JDBCCloser.close(rs, pstmt);
		}
		return actorList;
	}
	public static boolean deleteActors(int pkActorId) {
		boolean isDeleted = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM actor WHERE pk_actor_id=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pkActorId);
				affectedRows = pstmt.executeUpdate();
			}catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
			}finally {
				JDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("No actor deleted . . .");
				return !isDeleted;
			}else {
				System.out.println("actor deleted . . .");
				return isDeleted;
			}
	}

}

	

