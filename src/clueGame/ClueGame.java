package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame {
	private static Board board;
	
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
		
		jf.setVisible(true);
		board.repaint();
		

	}

}
