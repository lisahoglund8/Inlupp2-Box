import java.awt.Color;

public class Category {

	private Color color;
	private String name;

	Category(String namn, Color color){
		this.name = namn;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public Color getColor(){
		return color;
	}

	// public void showPlaces(){
	//
	// }
	// public void hidePlaces(){
	//
	// }

	public String toString() {
		return getName();
	}

}