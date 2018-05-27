package isys1118.group1.server.user;

/**
 * An approver, who is responsible for approving courses (after a course
 * coordinator sends it for approval).
 */
public class Approver extends User {

	public Approver(String accountId, String name) {
		super(accountId, name);
	}

	@Override
	public String getUserType() {
		return TYPE_APPROVER;
	}

}
