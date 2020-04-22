package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	//Calls each function to add a new panel
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

	//Creates the panel displaying whose turn it currently is
	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel playerLabel = new JLabel("Whose turn?");
		panel.add(playerLabel);
		currentPlayer.setEditable(false);
		panel.add(currentPlayer);
		return panel;
	}

	//Creates the button to move to the next player
	private JPanel createNextPlayerPanel() {
		JButton nextTurn = new JButton("Next player");
		nextTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Board.getInstance().nextPlayer();
			}
		});
		JPanel panel = new JPanel();
		panel.add(nextTurn);
		return panel;
	}

	//Creates the button which allows the player to make an accusation
	private JPanel createAccusationPanel() {
		JButton makeAccusation = new JButton("Make an accusation");
		makeAccusation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Board.getInstance().turnOver) {
					HumanAccusation accusation = new HumanAccusation();
					accusation.setVisible(true);
				}
			}
		});
		JPanel panel = new JPanel();
		panel.add(makeAccusation);
		return panel;
	}

	//Creates the panel displaying the die roll
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

	//Creates the box allowing the player to input a guess
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel guessLabel = new JLabel("Guess");
		panel.add(guessLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}

	//Displays the result of the guess
	private JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JLabel guessResultLabel = new JLabel("Response");
		panel.add(guessResultLabel);
		panel.add(guessResult);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}

	public void updateTurn(String player) {
		this.currentPlayer.setText(player);
	}

	public void updateGuess(String guess) {
		this.guess.setText(guess);
	}

	public void updateGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}

	public void updateDieRoll(int dieRoll) {
		this.dieRoll.setText(Integer.toString(dieRoll));
	}

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(750, 200); //Total size of the game control panel
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Default parameters
		panel.updateTurn("Player1");
		panel.updateGuess("Guess") ;
		panel.updateGuessResult("Guess Result");
		frame.setVisible(true);

	}

}
