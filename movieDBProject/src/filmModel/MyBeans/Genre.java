package filmModel.MyBeans;

import java.io.Serializable;

public class Genre implements Serializable{


	private static final long serialVersionUID = -8777451778660402399L;
	private int pkGenreId;
	private String genreName;
	
	public Genre() {
		
	}
    public Genre(String genreName) {
    	this.genreName = genreName;
    }
    public Genre(int pkGenreId, String genreName) {
    	this.pkGenreId = pkGenreId;
    	this.genreName = genreName;
    }
	
	//getters
	public int getPkGenreId() {
		return pkGenreId;
	}
	public String getGenreName() {
		return genreName;
	}
	
	//setters
	public void setPkGenreId(int pkGenreId) {
		this.pkGenreId = pkGenreId;
	}
	public void setGenreName(String genre) {
		this.genreName = genre;
	}
}
