package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame {
	private static Board board;
	
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
	
	public static void main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayer", "ClueWeapons");
		board.initialize();
		
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("Clue Game");
		jf.setSize(900, 800);
		
		GameControlPanel controlPanel = new GameControlPanel();
		jf.add(controlPanel, BorderLayout.SOUTH);
		
		jf.add(board, BorderLayout.CENTER);
		jf.setJMenuBar(createMenuBar());
		
		jf.setVisible(true);
		board.repaint();
		

	}

}
