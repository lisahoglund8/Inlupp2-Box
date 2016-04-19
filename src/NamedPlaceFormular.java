import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NamedPlaceFormular extends JPanel {
	private JTextField field1;

	NamedPlaceFormular() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel first = new JPanel();
		field1 = new JTextField(10);
		first.add(new JLabel("Namn:"));
		first.add(field1);
		panel.add(first);
		this.add(panel);
	}

	public String getPlaceName() {
		return field1.getText();
	}
}