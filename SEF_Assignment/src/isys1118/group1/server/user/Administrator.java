package isys1118.group1.server.user;

/**
 * An administrator - the highest level of permission. Can do everything!
 */
public class Administrator extends User {

	public Administrator(String accountId, String name) {
		super(accountId, name);
	}

	@Override
	public String getUserType() {
		return User.TYPE_ADMIN;
	}

}
