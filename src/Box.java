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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	private String[] namedOrDescribed = {"Namn","Beskrivning"};
	private JComboBox<String> namedOrDescribedBox = new JComboBox<String>(namedOrDescribed);
	private JMenuBar menuRow;
	private JMenu menu;
	private JMenuItem newMapMenu, loadPlacesMenu, saveMenu, exitMenu;
	private DefaultListModel <Category> model;
	private JList<Category> list;
	private JScrollPane scroll;
	private	JFileChooser chooseFile = new JFileChooser(".");
	private PicturePanel picturePanel = null;
	private JScrollPane scrollPicturePanel = null;
	private CreatePlaceByMouseClicked createPlaceByMouseClicked = new CreatePlaceByMouseClicked();
	private Map<String, ArrayList<Place>> nameMap = new HashMap<>();
	private ArrayList<Place> marked = new ArrayList<>();
	private Map<Category, ArrayList<Place>> categoryMap = new HashMap<>();
	private Map<Position, Place> positionMap = new HashMap<>();
	private boolean isSaved = true;
	private Category[] categoryArray;
	private Place place = null;

	public Box(){
		super("Inlupp 2 Prog2");
		menuRow = new JMenuBar();
		menu = new JMenu("Archive");	
		menu.setMnemonic(KeyEvent.VK_A);
		menuRow.add(menu);

		newMapMenu = new JMenuItem("New map");
		newMapMenu.addActionListener(new OpenPicture());
		loadPlacesMenu = new JMenuItem("Load places");
		loadPlacesMenu.addActionListener(new OpenFileListener());
		saveMenu = new JMenuItem("Save");
		saveMenu.addActionListener(new SaveListener());
		exitMenu = new JMenuItem("Exit");
		exitMenu.addActionListener(new ExitListener());

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
		list.addListSelectionListener(new ListListener());

		hideCategoryButton = new JButton("Hide category");
		hideCategoryButton.addActionListener(new HideCategory());
		right.add(hideCategoryButton);
		right.setBorder(e);

		JPanel up = new JPanel();
		add(up, BorderLayout.NORTH);
		up.add(new JLabel("New:"));
		up.add(namedOrDescribedBox);
		textField = new JTextField("Search", 10);
		textField.addActionListener(new SearchListener());
		up.add(textField);

		searchButton = new JButton("Search");
		searchButton.addActionListener(new SearchListener());
		up.add(searchButton);
		hideButton = new JButton("Hide");
		hideButton.addActionListener(new HideListener());
		up.add(hideButton);

		removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveListener());
		up.add(removeButton);
		whatIsHereButton = new JButton("What is here?");
		whatIsHereButton.addActionListener(new WhatIsHere());
		up.add(whatIsHereButton);

		namedOrDescribedBox.addActionListener(new CreatePlace());

		setLocation(200,200);
		pack();
		setVisible(true);
		setDefaultCloseOperation(Box.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitListener());

	}

	class ListListener implements ListSelectionListener{
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

	class HideCategory implements ActionListener {

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
	class WhatIsHere implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			picturePanel.addMouseListener(new WhatIsHereClickListener());
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		}

	}
	class WhatIsHereClickListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			Position p = null;
			for(int x = e.getX()-75; x < e.getX()+75; x++){
				System.out.println("Xloop");
				for(int y = e.getY()-100; y < e.getY()+100; y++){
					System.out.println("Yloop");
					if(positionMap.containsKey(p = new Position(x,y))){
						positionMap.get(p).setHidden(false);
						System.out.println("ifsatsen");
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
			if(!isSaved){
				if(!confirm()){
					return;
				}

			}
			FileFilter pictureFilter = new FileNameExtensionFilter("Bilder","jpg",
					"gif","png");
			chooseFile.setFileFilter(pictureFilter);
			int answer = chooseFile.showOpenDialog(Box.this);
			if (answer != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooseFile.getSelectedFile();
			String fileName = file.getAbsolutePath();
			if (scrollPicturePanel != null)
				remove(scrollPicturePanel);
			picturePanel = new PicturePanel(fileName);
			scrollPicturePanel = new JScrollPane(picturePanel);
			add(scrollPicturePanel);
			setSize(800, 600);
			validate();
			repaint();
		}
	}
	private boolean confirm(){
		int a = JOptionPane.showConfirmDialog(Box.this, "Finns osparade förändringar, vill du fortsätta ändå?", "Fel", JOptionPane.WARNING_MESSAGE);
		if(a != JOptionPane.OK_OPTION){
			return false;
		}
		else{
			picturePanel.removeAll();
			nameMap.clear();
			marked.clear();
			categoryMap.clear();
			positionMap.clear();
			repaint();
			revalidate();
			return true;

		}
	}
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			String name = textField.getText();
			ArrayList<Place> mappen = nameMap.get(name);
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

	class RemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			for(Place p : marked){
				categoryMap.get(p.getCategory()).remove(p);
				if(categoryMap.get(p.getCategory()).isEmpty())
					categoryMap.remove(p.getCategory());
				nameMap.get(p.getPlaceName()).remove(p);
				if(nameMap.get(p.getPlaceName()).isEmpty());
				nameMap.remove(p.getPlaceName());
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
			picturePanel.addMouseListener(createPlaceByMouseClicked);
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
	class ExitListener extends WindowAdapter implements ActionListener {
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
			if (shouldWindowBeClosed()) {
				dispose();
			}
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			System.exit(0);
		}

	}
	class HideListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			for(Place p : marked){
				p.setHidden(true);
			}
			repaint();
		}
	}	
	class HideMouseListener extends MouseAdapter{
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

	class SaveListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			try{
				int answer = chooseFile.showOpenDialog(Box.this);
				if (answer != JFileChooser.APPROVE_OPTION)        
					return;
				FileWriter fileWriter = new FileWriter(chooseFile.getSelectedFile().getAbsolutePath());
				PrintWriter out = new PrintWriter(fileWriter);
				for(Place p : positionMap.values()){
					if(p instanceof NamedPlace){
						out.println("Named"+"," + p.getCategory() +"," + p.getPosition().getX() + "," +
								p.getPosition().getY() + "," + p.getPlaceName());
					}
					if(p instanceof DescribedPlace){
						out.println("Described"+"," + p.getCategory() +"," + p.getPosition().getX() + "," +
								p.getPosition().getY() + "," + p.getPlaceName() +"," + ((DescribedPlace) p).getDescription());
					}
					repaint();
				}
				isSaved = true;
				out.close();
				fileWriter.close();

			}
			catch(FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "Kan inte öppna " + e);
			}
			catch(IOException e1){
				JOptionPane.showMessageDialog(null, "Fel " + e1.getMessage());
			}
		}
	}


	class OpenFileListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			try{
				if(!isSaved){
					if(!confirm()){
						return;
					}
				}
				FileFilter textFilter = new FileNameExtensionFilter("Textdokument","txt");
				chooseFile.setFileFilter(textFilter);
				int answer = chooseFile.showOpenDialog(Box.this);
				if (answer != JFileChooser.APPROVE_OPTION)        
					return;
				FileReader fileReader = new FileReader(chooseFile.getSelectedFile().getAbsolutePath());
				BufferedReader in = new BufferedReader(fileReader);
				String line;

				while((line = in.readLine()) != null){
					String [] tokens = line.split(","); 
					String place = tokens[0]; 
					String category = tokens [1];
					int x = Integer.parseInt(tokens[2]);
					int y = Integer.parseInt(tokens[3]);
					Position position = new Position(x,y);
					String name = tokens[4];


					Category newCategory = null;
					for(Category c : categoryArray){
						if(c.getName().equals(category)){
							newCategory = c;
						}

					}
					if(newCategory == null){
						newCategory = new Category("None", Color.black);
					}


					if(place.equalsIgnoreCase("named")){
						NamedPlace namedPlace = new NamedPlace(name, newCategory, position);
						addToCollections(position, namedPlace);
						picturePanel.setLayout(null);
						picturePanel.add(namedPlace);

					}
					else{
						String description = tokens [5];	
						DescribedPlace describedPlace = new DescribedPlace(name, newCategory, position, description);
						addToCollections(position, describedPlace);
						picturePanel.setLayout(null);
						picturePanel.add(describedPlace);
					}	
				}
				isSaved = false;
				repaint();
				in.close();
				fileReader.close();

			}catch(FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "Kan inte öppna  " + e);
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, "Fel  " + e.getMessage());
			}

		}
	}
	class CreatePlaceByMouseClicked extends MouseAdapter{
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
			String namedOrDescibedPlace = (String) namedOrDescribedBox.getSelectedItem();
			switch (namedOrDescibedPlace){
			case "Namn":
				NamedPlaceFormular npf = new NamedPlaceFormular();
				JOptionPane.showConfirmDialog(null, npf, "Nytt namn ", JOptionPane.OK_CANCEL_OPTION);
				String name = npf.getNamn();
				if(name.isEmpty() || name == null){
					JOptionPane.showMessageDialog(Box.this, "Names can't be empty! Try again", "Wrong", JOptionPane.ERROR_MESSAGE);
					return;
				}
				place = new NamedPlace(name, category, position);

				break;
			case "Beskrivning":
				DescribedPlaceFormular dpf = new DescribedPlaceFormular();
				JOptionPane.showConfirmDialog(null, dpf, "Ny beskrivning", JOptionPane.OK_CANCEL_OPTION);
				String nameDescription = dpf.getNamn();
				String description = dpf.getBeskrivning();
				if(nameDescription.isEmpty() || nameDescription == null || description.isEmpty() || description == null){
					JOptionPane.showMessageDialog(Box.this, "No empty rows! Try again", "Wrong", JOptionPane.ERROR_MESSAGE);
					return;
				}
				place = new DescribedPlace(nameDescription, category, position, description);
				break;
			}

			isSaved = false;
			picturePanel.add(place);
			addToCollections(position, place);
			list.clearSelection();
			picturePanel.removeMouseListener(createPlaceByMouseClicked);
			picturePanel.setCursor(Cursor.getDefaultCursor());
			namedOrDescribedBox.setEnabled(true);
			repaint();
			picturePanel.validate();
		}
	}

	private void addPlaceToPlaceMap(String name, Place place){
		if(nameMap.get(name) == null){
			nameMap.put(name, new ArrayList<Place>());
		}
		nameMap.get(name).add(place);

	}
	private void addPlaceToCategory(Category category, Place place){
		if(categoryMap.get(category) == null){
			categoryMap.put(category, new ArrayList<Place>());
		}
		categoryMap.get(category).add(place);


	}
	private void addToCollections(Position position, Place place) {
		addPlaceToPlaceMap(place.getPlaceName(), place);
		addPlaceToCategory(place.getCategory(), place);
		positionMap.putIfAbsent(position, place);
		place.addMouseListener(new HideMouseListener());

	}
	public static void main(String[] args){
		new Box();	
	}
}