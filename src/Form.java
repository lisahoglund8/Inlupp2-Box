import javax.swing.*;
import java.awt.*;

class Form extends JPanel{
	private JTextField namnFält = new JTextField(10);
	private JTextField taggFält = new JTextField(5);
	private JCheckBox ivrigBox = new JCheckBox("Ivrig");

	public Form(){
	    JPanel rad1 = new JPanel();
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    rad1.add(new JLabel("Namn:"));
	    rad1.add(namnFält);
	    add(rad1);
	    JPanel rad2 = new JPanel();
	    rad2.add(new JLabel("Taggar:"));
	    rad2.add(taggFält);
	    add(rad2);
	    add(ivrigBox);
	}
	public String getNamn(){
	    return namnFält.getText();
	}
	public int getTaggar(){
	    return Integer.parseInt(taggFält.getText());
	}
	public boolean getIvrig(){
	    return ivrigBox.isSelected();
	}
    }
