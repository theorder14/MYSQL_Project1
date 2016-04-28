package filmModel.TopModel;

public enum SqlQry {

	MOVIE("SELECT * FROM movie"),
	ACTOR("SELECT * FROM actor"),
	GENRE("SELECT * FROM genre"),
	sql(" ");
	
private String qry;
	
	private SqlQry(String qry) {
		this.qry = qry;
	}
	
	public String getQry() {
		return qry;
	}
	
	public void setQry(String qry) {
		this.qry = qry;
	}

}
