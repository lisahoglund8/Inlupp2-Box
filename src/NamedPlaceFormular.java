import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NamedPlaceFormular extends JPanel {
	private JTextField f�lt1;

	NamedPlaceFormular() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel f�rsta = new JPanel();
		f�lt1 = new JTextField(10);
		f�rsta.add(new JLabel("Namn:"));
		f�rsta.add(f�lt1);
		panel.add(f�rsta);
		this.add(panel);
	}

	public String getNamn() {
		return f�lt1.getText();
	}
}