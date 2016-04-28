package filmModel.myBeanManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import filmModel.MyBeans.Movie;
import Connection.JDBCCloser;
import Connection.JDBConnection;

public class MovieManager {
	
	private static Connection conn = JDBConnection.getJDBCConnection();
	
	public static Movie getMovie(int pkMovieId) {
		
		String sql = "SELECT * FROM movie WHERE pk_Movie_Id=?";
		Movie mov = new Movie();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkMovieId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mov.setPkMovieId(pkMovieId);
				mov.setMovieName(rs.getString("movie_name"));
				mov.setFkActorId(rs.getInt("fk_actor_id"));
				mov.setFkGenreId(rs.getInt("fk_genre_id"));
			}
			return mov;
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return null;
		} finally {
			JDBCCloser.close(rs, pstmt);
		}
	}
	
	public static List<Movie> getAllMovies() {
		List<Movie> movieList = new ArrayList<>();
		String sql = "SELECT * FROM movie";
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Movie mov = new Movie();
				mov.setPkMovieId(rs.getInt("pk_movie_id"));
				mov.setMovieName(rs.getString("movie_name"));
				mov.setFkActorId(rs.getInt("fk_actor_id"));
				mov.setFkGenreId(rs.getInt("fk_genre_id"));
				
				movieList.add(mov);	
			}
			return movieList;
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return null;	
		}finally {
			JDBCCloser.close(rs, stmt);
		}
	}
		
	//pk_album_id, album_name, fk_artist_id
	public static boolean updateMovie(Movie mov) {
		boolean isUpdated = true;
		int affectedRows = 0;
		
		PreparedStatement pstmt = null;
		
		if(mov == null) {
			return !isUpdated;
		}
		else {
			String sql = "UPDATE movie SET movie_name=?, fk_actor_id=?, fk_genre_id=? WHERE pk_movie_id=?";
				
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mov.getMovieName());
				pstmt.setInt(2, mov.getFkActorId());
				pstmt.setInt(3, mov.getFkGenreId());
				pstmt.setInt(4, mov.getPkMovieId());
				affectedRows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isUpdated;
			} finally {
				JDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("Album incorrect updated ....");
				return !isUpdated;
			}else {
				System.out.println("Album correct updated . . .");
				return isUpdated;
			}
		}
	}
	
	
	public static boolean addMovie(Movie mov) {
		boolean isAdded = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(mov == null) {
			return !isAdded;
		}
		else {
			String sql = "INSERT INTO movie (movie_name, fk_actor_id, fk_genre_id) VALUES (?,?,?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mov.getMovieName());
				pstmt.setInt(2, mov.getFkActorId());
				pstmt.setInt(3, mov.getFkGenreId());
				affectedRows = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isAdded;
			}finally {
				JDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("No album added . . .");
				return !isAdded;
			}else {
				System.out.println("album added . . .");
				return isAdded;
			}
		}
	}
	
	public static boolean deleteMovie(Movie mov) {
		boolean isDeleted = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		
		if(mov==null) {
			return !isDeleted;
		}
		else {
			String sql = "DELETE FROM movie WHERE pk_movie_id=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mov.getPkMovieId());
				affectedRows = pstmt.executeUpdate();
			}catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isDeleted;
			}finally {
				JDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("No movie deleted . . .");
				return !isDeleted;
			}else {
				System.out.println("movie deleted . . .");
				return isDeleted;
			}
		}
	}
	

	public static List<Movie> findMovieByPrefixName(String movieName) {
		List<Movie> movieList = new ArrayList<>();
		String sql = "SELECT * FROM movie WHERE movie_name LIKE ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, movieName+"%"); 
			rs = pstmt.executeQuery();
			System.out.println("Album");
			while(rs.next()) {
				Movie mov = new Movie();
				mov.setPkMovieId(rs.getInt("pk_movie_id"));
				mov.setMovieName(rs.getString("movie_name"));
				mov.setFkActorId(rs.getInt("fk_actor_id"));
				mov.setFkGenreId(rs.getInt("fk_genre_id"));
				System.out.println(mov);
				movieList.add(mov);	
				System.out.println("Album");
			}
			return movieList;
			
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
		String sql = "SELECT * FROM movie";
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
		String sql = "SELECT * FROM movie";
		int numOfColons = 0;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		//pk_album_id, album_name, fk_artist_id
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
		String sql = "SELECT * FROM movie";
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


		public static List<Movie> searchMovies(String movieName) {
			List<Movie> movieList = new ArrayList<>();
			String sql = "SELECT * FROM movie WHERE movie_name LIKE ?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, movieName+"%"); 
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Movie mov = new Movie();
					mov.setPkMovieId(rs.getInt("pk_movie_id"));
					mov.setMovieName(rs.getString("movie_name"));
					mov.setFkActorId(rs.getInt("fk_actor_id"));	
					mov.setFkGenreId(rs.getInt("fk_genre_id"));	
					movieList.add(mov);	
				}	
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());	
			}finally {
				JDBCCloser.close(rs, pstmt);
			}
			return movieList;
		}
		public static boolean deleteMovies(int pkMovieId) {
			boolean isDeleted = true;
			int affectedRows = 0;
			PreparedStatement pstmt = null;
			String sql = "DELETE FROM movie WHERE pk_movie_id=?";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, pkMovieId);
					affectedRows = pstmt.executeUpdate();
				}catch (SQLException e) {
					System.err.println("Error message: " + e.getMessage());
					System.err.println("Error code: " +e.getErrorCode());
					System.err.println("SQL state: " +e.getSQLState());
				}finally {
					JDBCCloser.close(pstmt);
				}
				
				if(affectedRows==0) {
					System.out.println("No movie deleted . . .");
					return !isDeleted;
				}else {
					System.out.println("movie deleted . . .");
					return isDeleted;
			}
		}

	}