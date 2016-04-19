import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DescribedPlaceFormular extends JPanel {
	private JTextField f�lt1;
	private JTextField f�lt2;

	DescribedPlaceFormular() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel f�rsta = new JPanel();
		f�lt1 = new JTextField(10);
		f�rsta.add(new JLabel("Namn:"));
		f�rsta.add(f�lt1);
		JPanel andra = new JPanel();
		f�lt2 = new JTextField(10);
		andra.add(new JLabel("Beskrivning:"));
		andra.add(f�lt2);
		panel.add(f�rsta);
		panel.add(andra);
		this.add(panel);
	}

	public String getNamn() {
		return f�lt1.getText();
	}
	public String getBeskrivning(){
		return f�lt2.getText();
	}

}