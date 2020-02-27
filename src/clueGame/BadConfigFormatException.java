package clueGame;


public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Config file format incorrect");
	}
	
	public BadConfigFormatException(String reason) {
		super(reason);
	}
}
