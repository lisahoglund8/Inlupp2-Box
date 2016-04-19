import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

abstract class Bricka extends JComponent{
	private boolean visad =false;
	public Bricka(int x,int y){
		setBounds(x,y,50,50);
		setPreferredSize(new Dimension (50,50));
		setMaximumSize(new Dimension (50, 50));
		setMinimumSize( new Dimension (50,50));
		//		addMouseListener(new MusLyss());
	}

	abstract protected void visa(Graphics g);
	abstract public boolean liknar (Bricka b);
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if (visad){
			visa(g);

		}
		else{
			g.setColor(Color.BLUE);
			g.fillRect(0,0, 50,50);
		}
	}
	public void setVisad(boolean b){
		visad = b;
		repaint();
	}
}

class Bricka1 extends Bricka{
	public Bricka1 (int x, int y){
		super(x,y);
	}
	protected void visa(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(0, 0, 50, 50);
	}
	public boolean liknar (Bricka b){
		return b instanceof  Bricka1;
	}
}



class Bricka2 extends Bricka{
	static final int []xes ={0,25, 50},
			yes ={0,50,0};

	public Bricka2 (int x, int y){
		super(x,y);
	}
	protected void visa(Graphics g){
		g.setColor(Color.RED);
		g.fillPolygon(xes, yes, 3);
	}
	public boolean liknar (Bricka b){
		return b instanceof Bricka2;
	}

}

class Bricka3 extends Bricka{
	public Bricka3 (int x, int y){
		super(x,y);
	}
	protected void visa(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(10,10,30,30);
	}
	public boolean liknar (Bricka b){
		return b instanceof Bricka3;
	}
}


class BildBricka extends Bricka{
	private ImageIcon bild;
	private String filnamn;
	public BildBricka(int x, int y, String filnamn){
		super(x,y);

	}
	protected void visa(Graphics g){
		g.drawImage(bild.getImage(),0,0,getWidth(),getHeight(), this );
	}
	public boolean liknar (Bricka b){
		if (b instanceof BildBricka &&((BildBricka)b).filnamn.equals(filnamn));
		return false;
	}
}
class Spelplan extends JPanel{
	private ImageIcon bild;

	public Spelplan(){
		bild= new ImageIcon("kartaprog2.png");
		setLayout(null);
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bild.getImage(),0,0, getWidth(), getHeight(), this);
	}	
}

class Memory extends JFrame{
	Bricka b1= null, b2= null;
	Spelplan plan = new Spelplan();
	int antalFörsök = 0,antalTräffar = 0;
	Memory(){

		add(plan, BorderLayout.CENTER);

		ArrayList<Bricka> alla= new ArrayList<>();
		alla.add(new Bricka1(112,87));
		alla.add(new Bricka1(318,143));
		alla.add(new Bricka2(219,290));
		alla.add(new Bricka2(100,567));
		alla.add(new Bricka3(273,435));
		alla.add(new Bricka3(45,458));



		for(Bricka b: alla){
			plan.add(b);
			b.addMouseListener(new MusLyss());
		}
		JPanel södra = new JPanel();
		add(södra, BorderLayout.SOUTH);
		JButton testKnapp = new JButton ("Testa");
		södra.add(testKnapp);
		testKnapp.addActionListener(new TestaLyss());

		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	class MusLyss extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent mev){
			Bricka bricka =(Bricka)mev.getSource();
			if (b1==null){
				b1 =bricka;
				bricka.setVisad(true);
			}
			else if (b2==null && bricka != b1){
				b2 =bricka;
				bricka.setVisad(true);
			}
		}
	}
	class TestaLyss implements ActionListener{
		public void actionPerformed (ActionEvent ave){
			if (b1!=null && b2!=null){
				if (b1.liknar(b2)){
					antalTräffar++;				
					b2.setVisible(false);
					plan.remove(b1);
					plan.remove(b2);
					plan.repaint();
				}
				b1.setVisad(false);
				b2.setVisad(false);
				b1=b2 =null;
			}
		}
	}

	public static void main(String[] args){
		new Memory();
	}

}
