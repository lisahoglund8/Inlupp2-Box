import java.awt.Color;
import java.awt.Graphics;

public class NamedPlace extends Place{
	NamedPlace(String name, Category category, Position position){
		super(name, category, position);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(unFolded()){
			g.setColor(Color.yellow);
			g.fillRect(0,0, 100, 50);
			g.setColor(Color.BLACK);
			
			g.drawString(getPlaceName(), 10, 15);
		}
	}

}