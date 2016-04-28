package filmModel.TopModel;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import filmModel.MyBeans.Movie;
import filmModel.MyBeans.Actor;
import filmModel.MyBeans.Genre;

import filmModel.myBeanManagers.MovieManager;
import filmModel.myBeanManagers.ActorManager;
import filmModel.myBeanManagers.ColonManager;
import filmModel.myBeanManagers.GenreManager;



public class MyTopModel {
	
	
	public DefaultTableModel fetchData(SqlQry sql, String search) {
		
		DefaultTableModel dm = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		String[] colonTitles = null;
		Object[] rowData = null;
		
		switch(sql) {
			case MOVIE:
				List<Movie> movieList = MovieManager.searchMovies(search);
				colonTitles = ColonManager.getColonTitles("SELECT * FROM movie");
				addColonTitlesToTable(dm,colonTitles);
				rowData = new Object[4];
				for(int i=0; i<movieList.size(); i++) {
					rowData[0] = movieList.get(i).getPkMovieId();
					rowData[1] = movieList.get(i).getMovieName();
					rowData[2] = movieList.get(i).getFkActorId();
					rowData[3] = movieList.get(i).getFkGenreId();	
					dm.insertRow(i, rowData);
				}
				return dm;
			case ACTOR:
				List<Actor> actList = ActorManager.searchActor(search);
				colonTitles = ColonManager.getColonTitles("SELECT * FROM actor");
				addColonTitlesToTable(dm,colonTitles);
				rowData = new Object[3];
				System.out.println(rowData.length);
				for(int i=0; i< actList.size(); i++) {
					rowData[0] = actList.get(i).getPkActorId();
					rowData[1] = actList.get(i).getActorName();
					rowData[2] = actList.get(i).getAge();
					dm.insertRow(i, rowData);
				}
				return dm;
		
			case GENRE:
				List<Genre> genList= GenreManager.searchGenres(search);
				colonTitles = ColonManager.getColonTitles("SELECT * FROM genre");
				addColonTitlesToTable(dm,colonTitles);
				rowData = new Object[2];
				for(int i=0; i<genList.size(); i++) {
					rowData[0] = genList.get(i).getPkGenreId();
					rowData[1] = genList.get(i).getGenreName();
					dm.insertRow(i, rowData);
				}
				return dm;
			default:
				return null;
		}
	}
	
	private void addColonTitlesToTable(DefaultTableModel dm, String[] colonTitles) {
		for(int i=0; i<colonTitles.length;i++)
			dm.addColumn(colonTitles[i]);
	}
		
	public boolean addData(SqlQry sql, String[] add) {
		
		switch(sql) {
			case MOVIE:
				return MovieManager.addMovie(new Movie(add[1],Integer.parseInt(add[2]),Integer.parseInt(add[3])));			
				case ACTOR:
				return ActorManager.addActor(new Actor(add[1],Integer.parseInt(add[2])));
			case GENRE:
				return GenreManager.addGenre(new Genre(add[1]));
			default:
				System.out.println("Not added");
				return false;
				
		}
	}

	public boolean updateData(SqlQry sql, String[] update) {
		switch(sql) {
		case MOVIE:
			return MovieManager.updateMovie(new Movie(Integer.parseInt(update[0]),update[1],Integer.parseInt(update[2]),Integer.parseInt(update[2])));
		case ACTOR:
			return ActorManager.updateActor(new Actor(Integer.parseInt(update[0]),update[1],Integer.parseInt(update[2])));
		case GENRE:
			return GenreManager.updateGenre(new Genre(Integer.parseInt(update[0]),update[1]));
		default:
			System.out.println("Not updated");
			return false;
		}
	}
	
	public boolean deleteData(SqlQry sql, String[] deleteStuff) {
		switch(sql) {
		case MOVIE:
			return MovieManager.deleteMovies(Integer.parseInt(deleteStuff[0])); 
		case ACTOR:
			return ActorManager.deleteActors(Integer.parseInt(deleteStuff[0]));
		case GENRE:
			return GenreManager.deleteGenres(Integer.parseInt(deleteStuff[0]));
		default:
			System.out.println("Not deleted");
			return false;
		}
	}

}
