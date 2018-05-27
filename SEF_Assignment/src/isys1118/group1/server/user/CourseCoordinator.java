package isys1118.group1.server.user;

/**
 * A course coordinator, who handles courses and assigns casuals to courses.
 */
public class CourseCoordinator extends User {

	public CourseCoordinator(String accountId, String name) {
		super(accountId, name);
	}

	@Override
	public String getUserType() {
		return TYPE_COORDINATOR;
	}

}
