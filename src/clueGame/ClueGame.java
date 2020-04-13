package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class ClueGame {
	private static Board board;
	
	// Creates the menu bar and file menu
	public static JMenuBar createMenuBar() {
		JMenuBar bar = new JMenuBar();
		JMenu file= new JMenu("File");
		
		
		JMenuItem exit = new JMenuItem("Exit");
		class ExitMenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		exit.addActionListener(new ExitMenuItemListener());
		file.add(exit);
		
		JMenuItem detectiveNotes = new JMenuItem("Detective Notes");
		JDialog detectiveNotesWindow = new DetectiveNotes();
		detectiveNotesWindow.setVisible(false);
		class dNotesMenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				detectiveNotesWindow.setVisible(true);
			}
		}
		detectiveNotes.addActionListener(new dNotesMenuItemListener());
		file.add(detectiveNotes);
		
		bar.add(file);
		
		return bar;
	}
	
	public static JPanel createCards(ArrayList<Card> humanCards) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		panel.setSize(new Dimension(500, 100));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(0,1));
		playerPanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0,1));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(0,1));
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		for(Card c: humanCards) {
			JTextField card = new JTextField(20);
			
			card.setText(c.getCardName());
			card.setHorizontalAlignment(JTextField.CENTER);
			card.setDisabledTextColor(Color.black);
			card.setEnabled(false);
			
			if(c.getCardType() == CardType.PERSON) {
				playerPanel.add(card);
			}
			else if(c.getCardType() == CardType.ROOM) {
				roomPanel.add(card);
			}
			else if(c.getCardType() == CardType.WEAPON) {
				weaponPanel.add(card);
			}
			
			
		}
		
		panel.add(playerPanel);
		panel.add(roomPanel);
		panel.add(weaponPanel);
		
		return panel;
	}
	
	// Sets up the game and board and creates and displays the gui
	public static void main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayer", "ClueWeapons");
		board.initialize();
		board.selectAnswer();
		board.dealCards();
		
		String humanName = "";
		ArrayList<Card> humanCards = new ArrayList<Card>();
		
		for(Player p : board.getPlayers()) {
			if(p instanceof HumanPlayer) {
				humanName = p.getPlayerName();
				for(Card c : p.getMyCards()) {
					humanCards.add(c);
				}
			}
		}
		
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("Clue Game");
		jf.setSize(800, 700);
		
		GameControlPanel controlPanel = new GameControlPanel();
		jf.add(controlPanel, BorderLayout.SOUTH);
		
		jf.add(board, BorderLayout.CENTER);
		jf.setJMenuBar(createMenuBar());
		
		jf.add(createCards(humanCards), BorderLayout.EAST);
		
		String title = "Welcome to Clue";
		String message = "You are " + humanName + ", press Next Player to begin play"; 
		JOptionPane.showMessageDialog(jf, message, title, JOptionPane.INFORMATION_MESSAGE);
		
		
		jf.setVisible(true);
		board.repaint();
		

	}

}
