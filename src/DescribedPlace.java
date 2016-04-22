import java.awt.Color;
import java.awt.Graphics;

public class DescribedPlace extends Place {

	private String beskrivning;
	DescribedPlace(String namn, Category category, Position position, String beskrivning){
		super(namn, category, position);
		this.beskrivning = beskrivning;

	}
	public String getBeskrivning() {
		return beskrivning;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(unFolded()){
			g.setColor(Color.yellow);
			g.fillRect(0,0, 100, 50);
			g.setColor(Color.BLACK);
			
			g.drawString(getNamn(), 10, 15);
			g.drawString(getBeskrivning(), 10, 30);
		}

	}


}
