import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Position extends JPanel{

	private int x; 
	private int y;

	Position(int x, int y){
		this.x = x;
		this.y = y;
	}	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

}