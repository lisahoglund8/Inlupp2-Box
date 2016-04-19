import javax.swing.*;
import java.awt.*;

class Form extends JPanel{
	private JTextField namnF�lt = new JTextField(10);
	private JTextField taggF�lt = new JTextField(5);
	private JCheckBox ivrigBox = new JCheckBox("Ivrig");

	public Form(){
	    JPanel rad1 = new JPanel();
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    rad1.add(new JLabel("Namn:"));
	    rad1.add(namnF�lt);
	    add(rad1);
	    JPanel rad2 = new JPanel();
	    rad2.add(new JLabel("Taggar:"));
	    rad2.add(taggF�lt);
	    add(rad2);
	    add(ivrigBox);
	}
	public String getNamn(){
	    return namnF�lt.getText();
	}
	public int getTaggar(){
	    return Integer.parseInt(taggF�lt.getText());
	}
	public boolean getIvrig(){
	    return ivrigBox.isSelected();
	}
    }
