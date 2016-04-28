package filmView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;




public class MyMainView extends JPanel {
	
	private static final long serialVersionUID = -6586691518396309146L;

	private JPanel cPanel,nPanel, sPanel, ePanel, wPanel;
	private JPanel ePanelMov, ePanelAct,  ePanelGen;
	private MyCustomTxtField[] txtFieldsMov, txtFieldsAct, txtFieldsGen;
	private JLabel[] labelsMov, labelsArt,  labelsGen;
	private MyCustomButton[] crudBtns;
	private JRadioButton[] rbs;
	private JTable mainTable;
	private JScrollPane mainScrollPane;
	private MyCustomButton searchB;
	private MyCustomTxtField searchField;
	private JFrame parentFrame;
	private JLabel rbLabel;
	
	public MyMainView(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		makeCenterPanel();
		makeNorthPanel();
		makeSouthPanel();
		makeEastPanel();
		makeWestPanel();

	}
	
	private void makeCenterPanel() {
		cPanel = new JPanel(new BorderLayout());	
		JScrollPane cPanelScrollPane = new JScrollPane(cPanel);
		String[] colonTitles = {"colon1", "colon2", "colon3", "colon4"};
		Object[][] rowData = {
				{"-","-","-","-"},
			
		};
		
		mainTable = new JTable(rowData, colonTitles);
		mainTable.setFillsViewportHeight(true);
		mainScrollPane = new JScrollPane(mainTable);
		cPanel.add(mainScrollPane, BorderLayout.CENTER);
	
		add(cPanelScrollPane, BorderLayout.CENTER);
	}
	
	private void makeNorthPanel() {
		nPanel = new JPanel();
		nPanel.setBackground(Color.BLACK); 
		crudBtns = new MyCustomButton[3];
		crudBtns[0] = new MyCustomButton("ADD");
		crudBtns[1] = new MyCustomButton("UPDATE");
		crudBtns[2] = new MyCustomButton("DELETE");	 
		for(int i=0;i<crudBtns.length;i++) 
				nPanel.add(crudBtns[i]);
		add(nPanel, BorderLayout.NORTH);
	}
	
	private void makeSouthPanel() {
		sPanel = new JPanel(new GridLayout(2,2));


		searchB = new MyCustomButton("Search");
		searchField = new MyCustomTxtField("");
		sPanel.add(searchB);
		sPanel.add(searchField);
		
		
		add(sPanel, BorderLayout.SOUTH);
	}
	
	private void makeEastPanel() {
		ePanel = new JPanel();
		ePanel.setBackground(Color.CYAN);
		ePanelMov = new JPanel(new GridLayout(8,1));
		ePanelMov.setBackground(Color.YELLOW); 
		txtFieldsMov = new MyCustomTxtField[4];
		txtFieldsMov[0] = new MyCustomTxtField("pk_movie_id");
		txtFieldsMov[0].setEditable(false);
		txtFieldsMov[1] = new MyCustomTxtField("movie_name");
		txtFieldsMov[2] = new MyCustomTxtField("fk_movie_id");
		txtFieldsMov[3] = new MyCustomTxtField("fk_genre_id");
		
		;
		
		labelsMov = new JLabel[4];
		labelsMov[0] = new JLabel("Pk_Movie_id");
		labelsMov[1] = new JLabel("Movie_name");
		labelsMov[2] = new JLabel("Fk_Actor_id");
		labelsMov[3] = new JLabel("Fk_Genre_id");
		
		
		for(int i=0; i<txtFieldsMov.length;i++) { 
			ePanelMov.add(labelsMov[i]);
			ePanelMov.add(txtFieldsMov[i]);
		}
		


		ePanelAct = new JPanel(new GridLayout(6,1));
		ePanelAct.setBackground(Color.YELLOW); 
		txtFieldsAct = new MyCustomTxtField[3];
		txtFieldsAct[0] = new MyCustomTxtField("pk_actor_id");
		txtFieldsAct[0].setEditable(false);
		txtFieldsAct[1] = new MyCustomTxtField("actor_name");
		txtFieldsAct[2] = new MyCustomTxtField("age");
		
		labelsArt = new JLabel[3];
		labelsArt[0] = new JLabel("Actor_ID ");
		labelsArt[1] = new JLabel("Actor_Name");
		labelsArt[2] = new JLabel("Age");
		
		for(int i=0; i<txtFieldsAct.length;i++) { 
			ePanelAct.add(labelsArt[i]);
			ePanelAct.add(txtFieldsAct[i]);
		}
		
		
		//genre
		ePanelGen = new JPanel(new GridLayout(4,1));
		ePanelGen.setBackground(Color.YELLOW);
		txtFieldsGen = new MyCustomTxtField[2];
		txtFieldsGen[0] = new MyCustomTxtField("pk_genre_id");
		txtFieldsGen[0].setEditable(false);
		txtFieldsGen[1] = new MyCustomTxtField("genre_name");
		
		labelsGen = new JLabel[2];
		labelsGen[0] = new JLabel("PK_ID");
		labelsGen[1] = new JLabel("Genre");
		
		for(int i=0; i<txtFieldsGen.length;i++) {
			ePanelGen.add(labelsGen[i]);
			ePanelGen.add(txtFieldsGen[i]);
		}
		add(ePanel, BorderLayout.EAST);
	}
	
	public void switchEastPanel(JRadioButton rb) {
		ePanel.removeAll();
		switch(rb.getText()) {
			case "Movie":
				ePanel.add(ePanelMov, BorderLayout.EAST);
				break;
			case "Actor":
				ePanel.add(ePanelAct, BorderLayout.EAST);
				break;
			case "Genres":
				ePanel.add(ePanelGen, BorderLayout.EAST);
				break;
		}
		refreshView();
	}

	private void makeWestPanel() {
		wPanel = new JPanel();
		wPanel.setBackground(Color.CYAN);
		wPanel.setLayout(new BoxLayout(wPanel,BoxLayout.Y_AXIS));
	
		rbLabel = new JLabel("Tables");
		wPanel.add(rbLabel);
		
		rbs = new JRadioButton[3];
		rbs[0] = new JRadioButton("Movie");
		rbs[1] = new JRadioButton("Actor");
		rbs[2] = new JRadioButton("Genres");
		
		
		ButtonGroup btnGrp = new ButtonGroup();
		for(int i=0; i<rbs.length; i++) {
			btnGrp.add(rbs[i]);
			wPanel.add(rbs[i]);
		}	
		
		add(wPanel, BorderLayout.WEST);
	}
	
	
	public void addRadioButtonListener(ActionListener actionListen) {
		for(int i=0; i<rbs.length; i++) 
			rbs[i].addActionListener(actionListen);
	}	
	public void addTableMouseListener(MouseListener moutseListen) {
		mainTable.addMouseListener(moutseListen);

	}	
	public void addCRUDButtonsListener(ActionListener actionListen) {
		for(int i=0; i<crudBtns.length;i++)
			crudBtns[i].addActionListener(actionListen);
	}
	public void addSearchButtonListener(ActionListener actionListen) {
		searchB.addActionListener(actionListen);
	}
	public JTable getMainTable() {
		return mainTable;
	}
	
	public JPanel getEastPanel() {
		return ePanel;
	}
	public JPanel getCenterPanel() {
		return cPanel;
	}
	public JPanel getNorthPanel() {
		return nPanel;
	}
	public JPanel getWestPanel() {
		return wPanel;
	}
	public JPanel getSouthPanel() {
		return sPanel;
	}
	
	public JRadioButton getSelectedRb() {
		
		if(rbs[0].isSelected())
			return rbs[0];
		else if(rbs[1].isSelected())
			return rbs[1];
		else 
			return rbs[2];
	}
	
	public JRadioButton getMovRb() {
		return rbs[0];
	}
	public JRadioButton getActRb() {
		return rbs[1];
	}
	public JRadioButton getGenRb() {
		return rbs[2];
	}


	public JTextField[] getTxtFieldsMov() {
		return txtFieldsMov;
	}
	public JTextField[] getTxtFieldsAct() {
		return txtFieldsAct;
	}

	public JTextField[] getTxtFieldsGen() {
		return txtFieldsGen;
	}
	
	public JTextField getSearchField() {
		return searchField;
	}
	public JButton getSearchButton() {
		return searchB;
	}
	
	
	
	public void refreshView() {
		this.revalidate();
		this.repaint();
	}
	
	
	private class MyCustomTxtField extends JTextField {
		private final int WIDTH = 100;
		private final int HEIGHT = 30;
		
		public MyCustomTxtField(String string) {
			super(string);
			setPreferredSize(new Dimension(WIDTH,HEIGHT));
		}
		
	}
	
	private class MyCustomButton extends JButton {
		private final int WIDTH = 150;
		private final int HEIGHT = 50;
		
		public MyCustomButton(String string) {
			super(string);
			setPreferredSize(new Dimension(WIDTH,HEIGHT));
			setBackground(Color.RED); 
		}

	}
}
