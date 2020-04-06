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

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class GameControlPanel extends JPanel {
	private JTextField currentPlayer = new JTextField(20);
	private JTextField dieRoll = new JTextField(3);
	private JTextField guess = new JTextField(50);
	private JTextField guessResult = new JTextField(50);
	
	public GameControlPanel () {
		setLayout(new GridLayout(2,0));
		JPanel panel = createTurnPanel();
		add(panel);
		panel = createNextPlayerPanel();
		add(panel);
		panel = createAccusationPanel();
		add(panel);
		panel = createDiePanel();
		add(panel);
		panel = createGuessPanel();
		add(panel);
		panel = createGuessResultPanel();
		add(panel);
	}
	
	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel playerLabel = new JLabel("Whose turn?");
		panel.add(playerLabel);
		panel.add(currentPlayer);
		return panel;
	}
	
	private JPanel createNextPlayerPanel() {
		JButton nextTurn = new JButton("Next player");
		JPanel panel = new JPanel();
		panel.add(nextTurn);
		return panel;
	}
	
	private JPanel createAccusationPanel() {
		JButton makeAccusation = new JButton("Make an accusation");
		JPanel panel = new JPanel();
		panel.add(makeAccusation);
		return panel;
	}
	
	private JPanel createDiePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
	 	JLabel dieLabel = new JLabel("Roll");
		panel.add(dieLabel);
		dieRoll.setEditable(false);
		panel.add(dieRoll);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}
	
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
	 	JLabel guessLabel = new JLabel("Guess");
		panel.add(guessLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}
	
	private JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
	 	JLabel guessResultLabel = new JLabel("Response");
		panel.add(guessResultLabel);
		panel.add(guessResult);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	private void updateTurn(String player) {
		this.currentPlayer.setText(player);
	}
	
	private void updateGuess(String guess) {
		this.guess.setText(guess);
	}
	
	private void updateGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}
	
	
	
	

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(750, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        panel.updateTurn("Player1");
        panel.updateGuess("Guess") ;
        panel.updateGuessResult("Guess Result");
        frame.setVisible(true);

	}

}
