package isys1118.group1.shared.error;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {
	
	private String message;
	
	public DatabaseException() {
		super();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		if (message == null) {
			return super.getMessage();
		}
		else {
			return message;
		}
	}
	
}
