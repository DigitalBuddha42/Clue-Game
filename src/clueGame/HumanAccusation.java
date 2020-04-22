package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HumanAccusation extends JDialog {
	JComboBox<String> personBox;
	JComboBox<String> weaponBox;
	JComboBox<String> roomBox;
	
	public HumanAccusation() {
		setTitle("Make an Accusation");
		setSize(300,400);
		setLayout(new GridLayout(4,2));
		JPanel room = createRoomSuggestion();
		add(room);
		JPanel roomOptions = createRoomBox();
		add(roomOptions);
		JPanel person = createPersonSuggestion();
		add(person);
		JPanel personOptions = createPersonBox();
		add(personOptions);
		JPanel weapon = createWeaponSuggestion();
		add(weapon);
		JPanel weaponOptions = createWeaponBox();
		add(weaponOptions);
		JPanel submit = createSubmitButton();
		add(submit);
		JPanel cancel = createCancelButton();
		add(cancel);
	}
	
	private JPanel createPersonSuggestion() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel person = new JLabel("Person");
		panel.add(person);
		return panel;
	}
	
	private JPanel createPersonBox() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		personBox = new JComboBox<String>();
		for( Player p: Board.getInstance().getPlayers()) {
			personBox.addItem(p.getPlayerName());
		}
		
		panel.add(personBox);
		return panel;
	}
	
	private JPanel createWeaponSuggestion() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel weapon = new JLabel("Weapon");
		panel.add(weapon);
		return panel;
	}
	
	private JPanel createWeaponBox() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		weaponBox = new JComboBox<String>();
		for( Card c : Board.getInstance().getDeck()) {
			if(c.getCardType() == CardType.WEAPON) {
				weaponBox.addItem(c.getCardName());
			}
		}
		
		panel.add(weaponBox);
		return panel;
	}
	
	private JPanel createRoomSuggestion() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel room = new JLabel("Room");
		panel.add(room);
		return panel;
	}
	
	private JPanel createRoomBox() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		roomBox = new JComboBox<String>();
		for( Card c : Board.getInstance().getDeck()) {
			if(c.getCardType() == CardType.ROOM) {
				roomBox.addItem(c.getCardName());
			}
		}
		
		panel.add(roomBox);
		return panel;
	}
	
	private JPanel createSubmitButton() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Solution accusation = new Solution((String)personBox.getSelectedItem(), (String)roomBox.getSelectedItem(), (String)weaponBox.getSelectedItem());
				boolean result = Board.getInstance().checkAccusation(accusation); 
				String title = "Accusation Result";
				String message;
				if(result) {
					message = "You have won! It was " + accusation.person + " in the " + accusation.room + " with the " + accusation.weapon;
				}
				else {
					message = "You guessed incorrectly. The guess: " + accusation.person + " in the " + accusation.room + " with the " + accusation.weapon + " is not correct.";
				}
				JOptionPane.showMessageDialog(Board.getInstance(), message, title, JOptionPane.INFORMATION_MESSAGE);
				if (result) {
					System.exit(0);
				}
				
				if(!result) Board.getInstance().humanIncorrectAccusation();
				dispose();
			}
		});
		panel.add(submitButton);
		return panel;
	}
	
	private JPanel createCancelButton() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(cancelButton);
		return panel;
	}
}
