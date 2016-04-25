import javax.swing.*;

public class NamedPlaceFormular extends JPanel {
	private JTextField field;

	NamedPlaceFormular() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel first = new JPanel();
		field = new JTextField(10);
		first.add(new JLabel("Namn:"));
		first.add(field);
		panel.add(first);
		this.add(panel);
	}

	public String getNamn() {
		return field.getText();
	}
}
