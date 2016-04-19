import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class IgelReg extends JFrame{

    private ArrayList<Igelkott> alla = new ArrayList<>();
    private JTextArea display;
    private JRadioButton allaKnapp, ivrigaKnapp, hejKnapp;
    
    public IgelReg(){
	JPanel södra = new JPanel();
	add(södra, BorderLayout.SOUTH);
	JButton nyKnapp = new JButton("Ny");
	södra.add(nyKnapp);
	nyKnapp.addActionListener(new NyLyss());
	
	JButton visaKnapp = new JButton("Visa");
	södra.add(visaKnapp);
	visaKnapp.addActionListener(new VisaLyss());
	
	JButton hejknapp = new JButton ("Hej");
	södra.add(hejknapp);
//	hejknapp.addActionListener (new HejLyss());

	JPanel högra = new JPanel();
	högra.setLayout(new BoxLayout(högra, BoxLayout.Y_AXIS));
	add(högra, BorderLayout.EAST);
	Font font = new Font("Dialog", Font.BOLD, 30);
	JLabel visaVilka = new JLabel("Visa vilka?");
	visaVilka.setFont(font);
	högra.add(visaVilka);
	allaKnapp = new JRadioButton("Alla", true);
	högra.add(allaKnapp);
	ivrigaKnapp = new JRadioButton("Ivriga");
	högra.add(ivrigaKnapp);
	
	ButtonGroup bg = new ButtonGroup();
	bg.add(allaKnapp);
	bg.add(ivrigaKnapp);
	
	display = new JTextArea();
	JScrollPane scroll = new JScrollPane(display);
	add(scroll, BorderLayout.CENTER);
	display.setEditable(false);

	setSize(400, 400);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setVisible(true);
    }

    
    class NyLyss implements ActionListener{
	public void actionPerformed(ActionEvent ave){
	    try{
		Form f = new Form();
		int svar = JOptionPane.showConfirmDialog(IgelReg.this, f,
							 "Indata",
					 JOptionPane.OK_CANCEL_OPTION);
		if (svar != JOptionPane.OK_OPTION)
		    return;
		String namn = f.getNamn();
		if (namn.equals("")) {
		    JOptionPane.showMessageDialog(IgelReg.this, "Inget namn!");
		    return;
		}
		int taggar = f.getTaggar();
		boolean ivrig = f.getIvrig();
	    
		Igelkott ig = new Igelkott(namn,taggar,ivrig);
		alla.add(ig);
		System.out.println(alla);
	    }catch(NumberFormatException e){
		JOptionPane.showMessageDialog(IgelReg.this,"Fel inmatning!");
	    }
	}
    }

    class VisaLyss implements ActionListener{
	public void actionPerformed(ActionEvent ave){
	    display.setText("");
	    if (allaKnapp.isSelected())
		for(Igelkott ig : alla)
		    display.append(ig.toString() + "\n");
	    else
		for(Igelkott ig : alla)
		    if (ig.isIvrig())
			display.append(ig.toString() + "\n");
		
	}
    }
//    class HejLyss implements ActionListener{
//    	public void actionPerformed(ActionEvent ave){
//    		display.setText("hejsan");
//    		
//    	}
//    }
    
    public static void main(String[] args){
    	new IgelReg();
   }

















    
    static   {
       Font f = new Font("Dialog", Font.BOLD, 20);
       String[] comps = {"Button", "Label", "RadioButton", "CheckBox",
			 "ToggleButton", "TextArea", "TextField",
			 "Menu", "MenuItem", "List","FileChooser","Dialog",
			 "OptionPane"};
       for(String s : comps)
	   UIManager.put(s+".font", f);       
    }


}
