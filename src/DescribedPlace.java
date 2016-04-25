import java.awt.Color;
import java.awt.Graphics;

public class DescribedPlace extends Place {

	private String description;
	DescribedPlace(String name, Category category, Position position, String beskrivning){
		super(name, category, position);
		this.description = beskrivning;

	}
	public String getDescription() {
		return description;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(unFolded()){
			g.setColor(Color.yellow);
			g.fillRect(0,0, 100, 50);
			g.setColor(Color.BLACK);
			
			g.drawString(getPlaceName(), 10, 15);
			g.drawString(getDescription(), 10, 30);
		}

	}


}
