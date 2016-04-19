
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
import java.io.File;
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

class Box extends JFrame {
	private JButton searchButton, hideButton, removeButton, whatIsHereButton, hideCategoryButton;
	private JTextField textField;
	private String[] alternativ = { "Namn", "Beskrivning" };
	private JComboBox<String> alternativBox = new JComboBox<String>(alternativ);
	private JMenuBar menuRow;
	private JMenu menu;
	private JMenuItem newMapMenu, loadPlacesMenu, saveMenu, exitMenu;
	private DefaultListModel<Category> model;
	private JList<Category> list;
	private JScrollPane scroll;
	private	JFileChooser fileChooser = new JFileChooser(".");
	private PicturePanel picturePanel = null;
	private JScrollPane scrolla = null;
	private MusLyss musLyss = new MusLyss();
	private Map<String, ArrayList<Place>> map = new HashMap<>();

	public Box(){
		super("Inlupp 2 Prog2");
		FileFilter bildFilter = new FileNameExtensionFilter("Bilder","jpg",
				"gif","png");
		fileChooser.setFileFilter(bildFilter);
		menuRow = new JMenuBar();

		menu = new JMenu("Archive");	
		menu.setMnemonic(KeyEvent.VK_A);
		//		menu.getAccessibleContext().setAccessibleDescription(
		//				"The only menu in this program that has menu items");
		menuRow.add(menu);

		newMapMenu = new JMenuItem("New map");
		newMapMenu.addActionListener(new OpenPicture());
		loadPlacesMenu = new JMenuItem("Load places");
		saveMenu = new JMenuItem("Save");
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

		model = new DefaultListModel<>();
		list = new JList<>(model); 
		model.addElement(new Category("Buss", Color.red));
		model.addElement(new Category("Tunnelbana", Color.blue));
		model.addElement(new Category("Tåg", Color.green));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		EtchedBorder e = new EtchedBorder();
		list.setBorder(e);
		scroll = new JScrollPane(list);
		right.add(scroll);
		list.addListSelectionListener(new ListLyss());

		hideCategoryButton = new JButton("Hide category");
		right.add(hideCategoryButton);
		right.setBorder(e);

		JPanel up = new JPanel();
		add(up, BorderLayout.NORTH);
		up.add(new JLabel("New:"));
		up.add(alternativBox);
		textField = new JTextField("Sök", 10);
		up.add(textField);

		searchButton = new JButton("Search");
		up.add(searchButton);
		hideButton = new JButton("Hide");
		up.add(hideButton);
		removeButton = new JButton("Remove");
		up.add(removeButton);
		whatIsHereButton = new JButton("What is here?");
		up.add(whatIsHereButton);

		alternativBox.addActionListener(new CreatePlace());

		setLocation(200,200);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Box();
	}

	class ListLyss implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
		}
	}

	class PicturePanel extends JPanel {
		private ImageIcon picture;

		public PicturePanel(String fileName) {
			picture = new ImageIcon(fileName);
			int width = picture.getIconWidth();
			int height = picture.getIconHeight();
			setPreferredSize(new Dimension(width, height));
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(picture.getImage(), 0, 0, this);
		}
	}

	class OpenPicture implements ActionListener {
		public void actionPerformed(ActionEvent ave){
			int answer = fileChooser.showOpenDialog(Box.this);
			if (answer != JFileChooser.APPROVE_OPTION)
				return;
			File file = fileChooser.getSelectedFile();
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

	class SearchLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			String ord = textField.getText();

			// Koppla sökfältet med inskrivna ordet, koppla sedan lyssnare till
			// sökknappen
			// Hämta sedan ord från HashMap? if/else om null
			// bildpanel.visaValdaORd
		}
	}

	class CreatePlace implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			picturePanel.addMouseListener(musLyss);
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}

	class ExitLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			// Varningsruta om man ej sparat, se under

			System.exit(0);

		}
	}

	class showLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

		}
	}

	class MusLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {

			int x = mev.getX();
			int y = mev.getY();
			Position position = new Position(x, y);
			picturePanel.setLayout(null);
			Category category;
			if (list.getSelectedValue() == null) {
				category = new Category("Kategorilös", Color.black);
			} else {
				category = list.getSelectedValue();
			}
			String abc = (String) alternativBox.getSelectedItem();
			switch (abc) {
			case "Namn":
				NamedPlaceFormular npf = new NamedPlaceFormular();
				int resultat = JOptionPane.showConfirmDialog(null, npf, "Nytt namn ", JOptionPane.OK_CANCEL_OPTION);
				String name = npf.getPlaceName();
				NamedPlace place = new NamedPlace(name, category, position);
				// place.mouseClicked(mev);
				picturePanel.add(place);
				addPlace(name, place);
				break;
			case "Beskrivning":
				DescribedPlaceFormular dpf = new DescribedPlaceFormular();
				int resultat2 = JOptionPane.showConfirmDialog(null, dpf, "Ny beskrivning",
						JOptionPane.OK_CANCEL_OPTION);
				String nameDescription = dpf.getPlaceName();
				String description = dpf.getDescription();
				DescribedPlace place1 = new DescribedPlace(nameDescription, category, position, description);
				picturePanel.add(place1);
				addPlace(nameDescription, place1);
				break;
			}
			list.clearSelection();
			picturePanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			picturePanel.removeMouseListener(musLyss);
			picturePanel.setCursor(Cursor.getDefaultCursor());
			alternativBox.setEnabled(true);
			repaint();
			picturePanel.validate();
		}
	}

	public void addPlace(String name, Place place) {
		if (map.get(name) == null) {
			map.put(name, new ArrayList<Place>());
		}
		map.get(name).add(place);

	}
}