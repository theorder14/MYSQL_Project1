package filmModel.MyBeans;
import java.io.Serializable;

public class Movie implements Serializable{

	private static final long serialVersionUID = 7175831865817427430L;
	private int pkMovieId;
	private String MovieName;
	private int fkActorId;
	private int fkGenreId;
	
	
    public Movie() {
  }
    
    public Movie(String movieName, int fkActorId, int fkGenreId) {
    	this.MovieName = movieName;
    	this.fkActorId = fkActorId;
    	this.fkGenreId = fkGenreId;
    
    }
    
   
    public Movie(int pkMovieId, String movieName,int fkActorId, int fkGenreId) {
    	this.pkMovieId = pkMovieId;
    	this.MovieName = movieName;
    	this.fkActorId = fkActorId;
    	this.fkGenreId = fkGenreId;
    }
    
    //getters
    public int getPkMovieId(){
    	return pkMovieId;
    }
    public String getMovieName(){
    	return MovieName;
    }
    public int getFkActorId(){
    	return fkActorId;
    }
    public int getFkGenreId(){
    	return fkGenreId;
    	
    } 
    //setters
    
    public void setPkMovieId(int pkMovieId) {
         this.pkMovieId = pkMovieId;       	
    }
    public void setMovieName(String MovieName){
    	this.MovieName = MovieName;
    }
    public void setFkActorId (int fkActorId){
    	this.fkActorId = fkActorId;
    }
    public void setFkGenreId (int fkGenreId){
        this.fkGenreId = fkGenreId;
  }

    public String toString(){
    	return pkMovieId +" " + MovieName + " " + fkActorId + " " + fkGenreId;
    }
    
}


