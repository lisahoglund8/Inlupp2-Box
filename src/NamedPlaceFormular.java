import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NamedPlaceFormular extends JPanel {
	private JTextField fält1;

	NamedPlaceFormular() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel första = new JPanel();
		fält1 = new JTextField(10);
		första.add(new JLabel("Namn:"));
		första.add(fält1);
		panel.add(första);
		this.add(panel);
	}

	public String getNamn() {
		return fält1.getText();
	}
}