import javax.swing.*;

public class DescribedPlaceFormular extends JPanel {
	private JTextField field1;
	private JTextField field2;

	DescribedPlaceFormular() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel first = new JPanel();
		field1 = new JTextField(10);
		first.add(new JLabel("Namn:"));
		first.add(field1);
		JPanel second = new JPanel();
		field2 = new JTextField(10);
		second.add(new JLabel("Beskrivning:"));
		second.add(field2);
		panel.add(first);
		panel.add(second);
		this.add(panel);
	}

	public String getNamn() {
		return field1.getText();
	}
	public String getBeskrivning(){
		return field2.getText();
	}

}

