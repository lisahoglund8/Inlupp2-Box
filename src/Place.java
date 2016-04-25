import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class Place extends JComponent implements MouseListener { 
	static final int[] xes = {0, 25, 50},
			yes = {0, 50, 0};
	private String name;
	private Category category;
	private Position position;
	private boolean isMarked;
	private boolean unFolded;
	private boolean hidden;
	
	Place(String name, Category category, Position position){
		this.name = name;
		this.category = category;
		this.position = position;
		setBounds(position.getX()-25, position.getY()-50,50,50);
		setPreferredSize(new Dimension(50,50));
		setMaximumSize(new Dimension(50,50));
		setMinimumSize(new Dimension(50,50));
		setOpaque(false);
		addMouseListener(this);
	}
	public String getPlaceName() {
		return name;
	}

	public void setPlaceName(String name) {
		this.name = name;
	}
	public Position getPosition(){
		return position;
	}
	protected void paintComponent(Graphics g){
		if(!hidden){
			super.paintComponent(g);
			g.setColor(category.getColor());
			g.fillPolygon(xes, yes, 3);
			if(isMarked()){
				g.setColor(Color.red);
				g.drawRect(0,0, 49, 49);
			}
		}
	}
	public boolean isMarked() {
		return isMarked;
	}
	public boolean unFolded(){
		return unFolded;
	}
	public boolean isHidden(){
		return hidden;
	}
	public void setHidden(boolean b){
		hidden = b;
	}
	public Category getCategory(){
		return category;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			isMarked = !isMarked;
			repaint();
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			unFolded= !unFolded;
			if(unFolded){
				setBounds(position.getX()-25, position.getY()-50, 100, 100);
			}
			else{
				setBounds(position.getX()-25, position.getY()-50,50,50);
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
