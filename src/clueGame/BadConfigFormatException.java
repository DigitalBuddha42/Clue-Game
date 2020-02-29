package clueGame;


public class BadConfigFormatException extends Exception {
	
	// General error message
	public BadConfigFormatException() {
		super("Config file format incorrect");
	}
	
	// Specific error message
	public BadConfigFormatException(String reason) {
		super(reason);
	}
}
