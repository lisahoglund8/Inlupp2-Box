import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class Place extends JComponent implements MouseListener {
	static final int[] xes = { 0, 25, 50 }, yes = { 0, 50, 0 };
	private String placeName;
	private Category category;
	private Position position;
	private boolean isSelected;
	private boolean isVisible;

	Place(String name, Category category, Position position) {
		this.placeName = name;
		this.category = category;
		this.position = position;
		setBounds(position.getX() - 25, position.getY() - 50, 50, 50);
		setPreferredSize(new Dimension(50, 50));
		setMaximumSize(new Dimension(50, 50));
		setMinimumSize(new Dimension(50, 50));
		setOpaque(false);
		addMouseListener(this);
	}

	public Position getPosition() {
		return position;
	}
	
	public String getPlaceName() {
		return placeName;
	}

	// public void showPlace(){
	// setVisible(true);
	// }
	// public void hidePlace(){
	// setVisible(false);
	// }
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(category.getColor());
		g.fillPolygon(xes, yes, 3);
		if(isSelected()){
			g.setColor(Color.red);
			g.drawRect(0,0, 49, 49);
		}
	}

	public boolean isSelected() {
		return isSelected;
	}

	public boolean unFolded() {
		return isVisible;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isSelected = !isSelected;
			repaint();
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			isVisible = !isVisible;
			if (isVisible) {
				setBounds(position.getX() - 25, position.getY() - 50, 100, 100);
			} else {
				setBounds(position.getX() - 25, position.getY() - 50, 50, 50);
			}
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}