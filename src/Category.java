import java.awt.Color;

public class Category {

	private Color färg;
	private String namn;

	Category(String namn, Color färg){
		this.namn = namn;
		this.färg = färg;
	}
	public String getNamn(){
		return namn;
	}
	public Color getFärg(){
		return färg;
	}

	//	public void showPlaces(){
	//		
	//	}
	//	public void hidePlaces(){
	//		
	//	}

	public String toString(){
		return getNamn();
	}

}