import java.awt.Color;

public class Category {

	private Color f�rg;
	private String namn;

	Category(String namn, Color f�rg){
		this.namn = namn;
		this.f�rg = f�rg;
	}
	public String getNamn(){
		return namn;
	}
	public Color getF�rg(){
		return f�rg;
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