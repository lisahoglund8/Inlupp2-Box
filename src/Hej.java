import java.awt.*;
import javax.swing.*;


class Hej{
	public static void main(String[]args){
		JFrame win = new JFrame ("Hej");
		win.setLayout(new FlowLayout());
		JLabel lab= new JLabel ("Namn");
		win.add(lab);
		JTextField fält = new JTextField(8);
		win.add(fält);
		JButton but = new JButton("Hälsa");
		win.add(but);
		JLabel häls = new JLabel("Hej");
		win.add(häls);
		win.setSize(350,400);
		win.getBackground();
		win.setVisible(true);
		
	}
//public void Testa(){
//	
//   JPanel södra = new JPanel();
//    add(södra,BorderLayout.SOUTH);
//     JButton nyKnapp = new JButton( "Ny knapp");
//     södra.add(nyKnapp);
//    return;
    

	
//}
private void add(JPanel södra, String south) {
	// TODO Auto-generated method stub
	
}


}
