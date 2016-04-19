import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DescribedPlaceFormular extends JPanel {
	private JTextField fält1;
	private JTextField fält2;

	DescribedPlaceFormular() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel första = new JPanel();
		fält1 = new JTextField(10);
		första.add(new JLabel("Namn:"));
		första.add(fält1);
		JPanel andra = new JPanel();
		fält2 = new JTextField(10);
		andra.add(new JLabel("Beskrivning:"));
		andra.add(fält2);
		panel.add(första);
		panel.add(andra);
		this.add(panel);
	}

	public String getNamn() {
		return fält1.getText();
	}
	public String getBeskrivning(){
		return fält2.getText();
	}

}