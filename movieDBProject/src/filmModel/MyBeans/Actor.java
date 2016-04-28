package filmModel.MyBeans;
import java.io.Serializable;

public class Actor implements Serializable{

	private static final long serialVersionUID = 2630005074933344778L;
	private int pkActorId;
	private String actorName;
	private int age;

    public Actor() {
  }
	public Actor(String actorName,int age) {
    	this.actorName = actorName;
    	this.age = age;
    	
    }
    public Actor(int pkActorId, String actorName, int age) {
    	this.pkActorId = pkActorId;
    	this.actorName = actorName;
    	this.age = age;
    }
    //getters
    public int getPkActorId(){
    	return pkActorId;
    }
    public String getActorName(){
    	return actorName;
    }
    public int getAge(){
    	return age;
    } 
    //setters
    
    public void setPkActorId(int pkActorId) {
         this.pkActorId = pkActorId;       	
    }
    public void setActorName(String actorName){
    	this.actorName = actorName;
    }
    public void setAge (int age){
    	this.age = age;
    }
    public String toString(){
    	return pkActorId +" " + actorName + " " + age;
    	
    }
}



