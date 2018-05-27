package isys1118.group1.shared.error;

@SuppressWarnings("serial")
public class PermissionException extends Exception {
	
	private String userId;
	private String userName;
	private String option;
	
	public PermissionException() {
		super();
	}
	
	public void set(String userId, String userName, String option) {
		this.userId = userId;
		this.userName = userName;
		this.option = option;
	}
	
	@Override
	public String getMessage() {
		return "Error: User " + userId + " " + userName +
				" does not have permission to use the \"" + option +
				"\" option.";
	}
	
}
