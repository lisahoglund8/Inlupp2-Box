import java.awt.*;
import javax.swing.*;


class Hej{
	public static void main(String[]args){
		JFrame win = new JFrame ("Hej");
		win.setLayout(new FlowLayout());
		JLabel lab= new JLabel ("Namn");
		win.add(lab);
		JTextField f�lt = new JTextField(8);
		win.add(f�lt);
		JButton but = new JButton("H�lsa");
		win.add(but);
		JLabel h�ls = new JLabel("Hej");
		win.add(h�ls);
		win.setSize(350,400);
		win.getBackground();
		win.setVisible(true);
		
	}
//public void Testa(){
//	
//   JPanel s�dra = new JPanel();
//    add(s�dra,BorderLayout.SOUTH);
//     JButton nyKnapp = new JButton( "Ny knapp");
//     s�dra.add(nyKnapp);
//    return;
    

	
//}
private void add(JPanel s�dra, String south) {
	// TODO Auto-generated method stub
	
}


}
