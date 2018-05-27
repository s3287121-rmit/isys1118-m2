package isys1118.group1.server.user;

/**
 * A casual user, who is assigned to Activities by a course coordinator.
 */
public class Casual extends User {

	public Casual(String accountId, String name) {
		super(accountId, name);
	}

	@Override
	public String getUserType() {
		return TYPE_CASUAL;
	}

}
