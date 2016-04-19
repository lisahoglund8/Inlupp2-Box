import javax.swing.JPanel;

public class Position extends JPanel {

	private int x;
	private int y;

	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}