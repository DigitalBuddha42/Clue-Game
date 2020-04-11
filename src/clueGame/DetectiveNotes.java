package clueGame;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class DetectiveNotes extends JDialog{
	
	// Constructor for entire JDialog box, adds all panels in order
	public DetectiveNotes() {
		setTitle("Detective Notes");
		setSize(800,700);
		setLayout(new GridLayout(3,2));
		JPanel panel = createPeople();
		add(panel);
		panel = createPersonGuess();
		add(panel);
		panel = createRooms();
		add(panel);
		panel = createRoomGuess();
		add(panel);
		panel = createWeapons();
		add(panel);
		panel = createWeaponGuess();
		add(panel);
	}
	
	// Panel for checking people off
	private JPanel createPeople() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		for( Player p: Board.getInstance().getPlayers() ) {
			JCheckBox box = new JCheckBox( p.getPlayerName() );
			panel.add(box);
		}
		return panel;
	}
	
	// Panel for checking rooms off
	private JPanel createRooms() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		for( String str: Board.getInstance().getLegend().values() ) {
			if( !str.equals("Walkway") && !str.equals("Storage") ) {
				JCheckBox box = new JCheckBox( str );
				panel.add(box);
			}
		}
		return panel;
	}
	
	// Panel for checking weapons off
	private JPanel createWeapons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		for( Card card: Board.getInstance().getDeck() ) {
			if( card.getCardType() == CardType.WEAPON ) {
				JCheckBox box = new JCheckBox( card.getCardName() );
				panel.add(box);
			}
		}
		
		return panel;
	}
	
	// Panel with dropdown menu for guessing a person
	private JPanel createPersonGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		JComboBox<String> comboBox = new JComboBox<String>();
		for( Player player: Board.getInstance().getPlayers() ) {
			comboBox.addItem(player.getPlayerName());
		}
		panel.add(comboBox);
		return panel;
	}
	
	// Panel with dropdown menu for guessing a room
	private JPanel createRoomGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		JComboBox<String> comboBox = new JComboBox<String>();
		for( String str: Board.getInstance().getLegend().values() ) {
			if( !str.equals("Walkway") && !str.equals("Closet")  ) {
				comboBox.addItem(str);
			}
		}
		panel.add(comboBox);
		return panel;
	}
	
	// Panel with dropdown menu for guessing a weapon
	private JPanel createWeaponGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		JComboBox<String> comboBox = new JComboBox<String>();
		for( Card card: Board.getInstance().getDeck() ) {
			if( card.getCardType() == CardType.WEAPON ) {
				comboBox.addItem(card.getCardName());
			}
		}
		
		panel.add(comboBox);
		return panel;
	}
}
