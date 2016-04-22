import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

class Box extends JFrame{

	private JButton searchButton, hideButton, removeButton, whatIsHereButton, hideCategoryButton;
	private JTextField textField;
	private String[] alternativ = {"Namn","Beskrivning"};
	private JComboBox<String> alternativBox = new JComboBox<String>(alternativ);
	private JMenuBar menuRow;
	private JMenu menu;
	private JMenuItem newMapMenu, loadPlacesMenu, saveMenu, exitMenu;
	private DefaultListModel <Category> model;
	private JList<Category> list;
	private JScrollPane scroll;
	private	JFileChooser chooseFile = new JFileChooser(".");
	private PicturePanel picturePanel = null;
	private JScrollPane scrolla = null;
	private MusLyss musLyss = new MusLyss();
	private Map<String, ArrayList<Place>> map = new HashMap<>();
	private ArrayList<Place> marked = new ArrayList<>();
	private Map<Category, ArrayList<Place>> categoryMap = new HashMap<>();
	private Map<Position, Place> positionMap = new HashMap<>();
	private boolean isSaved = true;
	private Category[] categoryArray;

	public Box(){
		super("Inlupp 2 Prog2");
		FileFilter bildFilter = new FileNameExtensionFilter("Bilder","jpg",
				"gif","png");
		chooseFile.setFileFilter(bildFilter);
		menuRow = new JMenuBar();

		menu = new JMenu("Archive");	
		menu.setMnemonic(KeyEvent.VK_A);
		//		menu.getAccessibleContext().setAccessibleDescription(
		//				"The only menu in this program that has menu items");
		menuRow.add(menu);

		newMapMenu = new JMenuItem("New map");
		newMapMenu.addActionListener(new OpenPicture());
		loadPlacesMenu = new JMenuItem("Load places");
		loadPlacesMenu.addActionListener(new openLyss());
		saveMenu = new JMenuItem("Save");
		saveMenu.addActionListener(new saveLyss());
		exitMenu = new JMenuItem("Exit");
		exitMenu.addActionListener(new ExitLyss());

		menu.add(newMapMenu);
		menu.add(loadPlacesMenu);
		menu.add(saveMenu);
		menu.add(exitMenu);

		setJMenuBar(menuRow);

		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		add(right, BorderLayout.EAST);
		right.add(new JLabel("Category"));
		
		categoryArray = new Category []{ new Category("Buss", Color.red), new Category("Tunnelbana", Color.blue), new Category("Tåg", Color.green) };

		model = new DefaultListModel<>();
	
		list = new JList<>(model); 
		model.addElement(categoryArray[0]);
		model.addElement(categoryArray[1]);
		model.addElement(categoryArray[2]);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		EtchedBorder e = new EtchedBorder();
		list.setBorder(e);
		scroll = new JScrollPane(list);
		right.add(scroll);
		list.addListSelectionListener(new ListLyss());

		hideCategoryButton = new JButton("Hide category");
		hideCategoryButton.addActionListener(new hideCategory());
		right.add(hideCategoryButton);
		right.setBorder(e);

		JPanel up = new JPanel();
		add(up, BorderLayout.NORTH);
		up.add(new JLabel("New:"));
		up.add(alternativBox);
		textField = new JTextField("Sök", 10);
		textField.addActionListener(new SearchLyss());
		up.add(textField);

		searchButton = new JButton("Search");
		up.add(searchButton);
		hideButton = new JButton("Hide");
		hideButton.addActionListener(new hideLyss());
		up.add(hideButton);
	
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveLyss());
		up.add(removeButton);
		whatIsHereButton = new JButton("What is here?");
		whatIsHereButton.addActionListener(new whatIsHere());
		up.add(whatIsHereButton);

		alternativBox.addActionListener(new CreatePlace());

		setLocation(200,200);
		pack();
		setVisible(true);
		setDefaultCloseOperation(Box.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitLyss());

	}

	public static void main(String[] args){
		new Box();	
	}
	class ListLyss implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent lse){	
			
			Category category = list.getSelectedValue();
			
			if(category != null){
				if(categoryMap.containsKey(category)){
				for(Place p : categoryMap.get(category)){
					p.setHidden(false);
				}
				}
				repaint();
			}
			
		}	
	}
	class PicturePanel extends JPanel{
		private ImageIcon picture;
		public PicturePanel(String fileName){
			picture = new ImageIcon(fileName);
			int width = picture.getIconWidth();
			int height = picture.getIconHeight();
			setPreferredSize(new Dimension(width,height));
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(picture.getImage(), 0, 0, this);
		}
	}
	
	public class hideCategory implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Category category = list.getSelectedValue();
			if(category != null){
				for(Place p : categoryMap.get(category)){
					p.setHidden(true);
				}
				repaint();
			}

		}

	}
	public class whatIsHere implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			picturePanel.addMouseListener(new whatIsHereClickListener());
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		}

	}
	public class whatIsHereClickListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			Position p = null;
			for(int x = e.getX()-75; x < e.getX()+75; x++){
				for(int y = e.getY()-100; y < e.getY()+100; y++){
					if(positionMap.containsKey(p = new Position(x,y))){
						positionMap.get(p).setHidden(false);
					}
				}
			}
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			repaint();
			picturePanel.removeMouseListener(this);
		}

	}
	class OpenPicture implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			int answer = chooseFile.showOpenDialog(Box.this);
			if (answer != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooseFile.getSelectedFile();
			String fileName = file.getAbsolutePath();
			if (scrolla != null)
				remove(scrolla);
			picturePanel = new PicturePanel(fileName);
			scrolla = new JScrollPane(picturePanel);
			add(scrolla);
			setSize(800, 600);
			validate();
			repaint();
		}
	}
	class SearchLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			String name = textField.getText();
			ArrayList<Place> mappen = map.get(name);
			if(mappen == null){
				JOptionPane.showMessageDialog(Box.this, "Kan inte hitta " + name);
			}
			else{
				for(Place p : mappen){
					p.setHidden(false);
					repaint();
				}
			}
		}
	}
	
	class RemoveLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
		for(Place p : marked){
			categoryMap.get(p.getCategory()).remove(p);
			if(categoryMap.get(p.getCategory()).isEmpty())
				categoryMap.remove(p.getCategory());
			map.get(p.getNamn()).remove(p);
			if(map.get(p.getNamn()).isEmpty())
				map.remove(p.getNamn());
			positionMap.get(p.getPosition()).remove(p);
			positionMap.remove(p.getPosition());
			picturePanel.remove(p);	
		}
			isSaved = false;
		marked.clear();
		repaint();
			
		}
	}
	class CreatePlace implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			picturePanel.addMouseListener(musLyss);
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
	class ExitLyss extends WindowAdapter implements ActionListener {
		private boolean shouldWindowBeClosed() {

			if (isSaved == false){
				int a = JOptionPane.showConfirmDialog(null, "Det finns osparade förändringar, vill du avsluta ändå?");
				if (a == JOptionPane.OK_OPTION){
					return true;
				} else {
					return false;
				}
			} else{
				return true;
			}
		}

		public void actionPerformed(ActionEvent ave){
			 boolean close = shouldWindowBeClosed();
			 if (close){
				 System.exit(0);
			 }
		}

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			System.out.println("Closing window!");
			if (shouldWindowBeClosed()) {
				dispose();
			}
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			System.out.println("Window closed!");
			System.exit(0);
		}

		}
	class hideLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			for(Place p : marked){
				p.setHidden(true);
			}
			repaint();
		}
	}	
	class hideMouseLyss extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent mev){
			if(mev.getButton() == MouseEvent.BUTTON1){
				Place p = (Place)mev.getSource();
				if(!p.isMarked()){
					marked.remove(p);
				}else{
					marked.add(p);
			}
		}
	}
}
	
	class saveLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
				try{
					int answer = chooseFile.showOpenDialog(Box.this);
					if (answer != JFileChooser.APPROVE_OPTION)        
						return;
						FileWriter utfil = new FileWriter(chooseFile.getSelectedFile().getAbsolutePath()); //namn på filen
						PrintWriter ut = new PrintWriter(utfil);
				//Typ, kategori, x-koordinat, y-koordinat, namn, (beskrivning)	
						for(Place p : positionMap.values()){
							if(p instanceof NamedPlace){
								ut.println("Named"+"," + p.getCategory() +"," + p.getPosition().getX() + "," +
							p.getPosition().getY() + "," + p.getNamn());
							}
							if(p instanceof DescribedPlace){
								ut.println("Described"+"," + p.getCategory() +"," + p.getPosition().getX() + "," +
										p.getPosition().getY() + "," + p.getNamn() +"," + ((DescribedPlace) p).getBeskrivning());
							}
							repaint();
						}
				isSaved = true;
						ut.close();
						utfil.close();
						
					}
					catch(FileNotFoundException e){
						JOptionPane.showMessageDialog(null, "Kan inte öppna " + e);
					}
					catch(IOException e1){
						JOptionPane.showMessageDialog(null, "Fel " + e1.getMessage());
					}
				}
			}


	class openLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			try{
				
				int answer = chooseFile.showOpenDialog(Box.this);
				if (answer != JFileChooser.APPROVE_OPTION)        
					return;
				FileReader infil = new FileReader(chooseFile.getSelectedFile().getAbsolutePath()); //namn på fil
				BufferedReader in = new BufferedReader(infil);
				String line;
							
				while((line = in.readLine()) != null){
					String [] tokens = line.split(","); 
					String place = tokens[0]; 
					String kategori = tokens [1];
					int x = Integer.parseInt(tokens[2]);
					int y = Integer.parseInt(tokens[3]);
					Position position = new Position(x,y);
					String namn = tokens[4];
					
					
					Category newCategory = null;
					for(Category c : categoryArray){
						if(c.getNamn().equals(kategori)){
							newCategory = c;
						}
						
					}
					if(newCategory == null){
						newCategory = new Category("None", Color.black);
					}
					

					if(place.equalsIgnoreCase("named")){
					NamedPlace namedPlace = new NamedPlace(namn, newCategory, position);
						addToCollections(position, namedPlace);
						picturePanel.setLayout(null);
					picturePanel.setLayout(null); //Konstig lösning
					picturePanel.add(namedPlace);

					}
					else{
					String beskrivning = tokens [5];
					DescribedPlace describedPlace = new DescribedPlace(namn, newCategory, position, beskrivning);
						addToCollections(position, describedPlace);
						picturePanel.setLayout(null);
					picturePanel.setLayout(null); //Konstig lösning
					picturePanel.add(describedPlace);
					}	
				}
				isSaved = false;
				repaint();
				in.close();
				infil.close();
				 
			}catch(FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "Kan inte öppna  " + e);
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, "Fel  " + e.getMessage());
			}

		}
	}
	class MusLyss extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent mev){

			int x = mev.getX();
			int y = mev.getY();
			Position position = new Position(x,y);
			picturePanel.setLayout(null);
			Category category;  
			if(list.getSelectedValue() == null){
				category = new Category("None", Color.black);
			}
			else{
				category = list.getSelectedValue();
			}
			Place place = null;
			String abc = (String) alternativBox.getSelectedItem();
			switch (abc){
			case "Namn":
				NamedPlaceFormular npf = new NamedPlaceFormular();
				int resultat = JOptionPane.showConfirmDialog(null, npf, "Nytt namn ", JOptionPane.OK_CANCEL_OPTION);
				String name = npf.getNamn();
				place = new NamedPlace(name, category, position);

				break;
			case "Beskrivning":
				DescribedPlaceFormular dpf = new DescribedPlaceFormular();
				int resultat2 = JOptionPane.showConfirmDialog(null, dpf, "Ny beskrivning", JOptionPane.OK_CANCEL_OPTION);
				String nameDescription = dpf.getNamn();
				String description = dpf.getBeskrivning();
				place = new DescribedPlace(nameDescription, category, position, description);
				break;
			}
			isSaved = false;
			picturePanel.add(place);
			place.addMouseListener(new hideMouseLyss());
			addToCollections(position, place);
			list.clearSelection();
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			picturePanel.removeMouseListener(musLyss);
			picturePanel.setCursor(Cursor.getDefaultCursor());
			alternativBox.setEnabled(true);
			repaint();
			picturePanel.validate();
		}
	}
	
	public void addPlaceToPlaceMap(String name, Place place){
		if(map.get(name) == null){
			map.put(name, new ArrayList<Place>());
		}
		map.get(name).add(place);

	}
	public void addPlaceToCategory(Category category, Place place){
		if(categoryMap.get(category) == null){
			categoryMap.put(category, new ArrayList<Place>());
		}
		categoryMap.get(category).add(place);

	}

	/**
	 * Adds new Place to all collections needed for search, category toggling and the position map.
	 * @param position Position of Place (used as key)
	 * @param place Place to add to all collections.
	 */
	private void addToCollections(Position position, Place place) {
		addPlaceToPlaceMap(place.getNamn(), place);
		addPlaceToCategory(place.getCategory(), place);
		positionMap.putIfAbsent(position, place);
	}
}