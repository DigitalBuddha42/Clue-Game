package clueGame;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution (String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	public Solution(Solution solution) {
		this.person = solution.person;
		this.room = solution.room;
		this.weapon = solution.weapon;
	}
	
}
