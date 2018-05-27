package isys1118.group1.server.user;

/**
 * Parent Class for all users.
 */
public abstract class User {
	
	public static final String TYPE_COORDINATOR = "Coordinator";
	public static final String TYPE_CASUAL = "Casual";
	public static final String TYPE_APPROVER = "Approver";
	public static final String TYPE_ADMIN = "Admin";
	
	public final String accountId;
	public final String name;
	
	public User(String accountId, String name) {
		this.accountId = accountId;
		this.name = name;
	}
	
	public abstract String getUserType();
	
}
