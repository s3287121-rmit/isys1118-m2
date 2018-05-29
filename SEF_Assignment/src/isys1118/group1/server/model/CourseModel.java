package isys1118.group1.server.model;

import java.io.IOException;
import java.util.ArrayList;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.helpers.Activity;
import isys1118.group1.server.helpers.CasualPriceCalculator;
import isys1118.group1.server.helpers.Cost;
import isys1118.group1.server.helpers.Course;
import isys1118.group1.server.session.Session;
import isys1118.group1.shared.Constants;
import isys1118.group1.shared.error.DatabaseException;
import isys1118.group1.shared.view.CourseView;

public class CourseModel extends Model {
	
	private static final int ACTIVITY_LEN = 5;
	
	private final String courseId;
	private Course course;
	private ArrayList<Activity> activities;
	private boolean activityLoadError = false;
	private boolean addActivity = false;
	private boolean approvalButtons = false;
	private boolean approvalView = false;
	
	private boolean error = false;
	
	public CourseModel(String courseId) {
		this.courseId = courseId;
		this.course = null;
		activities = new ArrayList<Activity>();
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseFromRow(Row r) {
		course = new Course();
		course.setFromRow(r);
		if (course.getError()) {
			error = true;
			return;
		}
		try {
			Table allActivities
				= Database.getDatabase().getFullTable("activities");
			ArrayList<Row> actList
				= allActivities.getRowsEqual("courseid", course.getCourseId());
			for (Row act : actList) {
				if (Session.getPermissions().allowActivityView(
						act.get("activityid"))) {
					addActivity(act);
				}
			}
		} catch (DatabaseException | IOException e) {
			activityLoadError = true;
			e.printStackTrace();
		}

		// set cost only if allowed
		if (
				Session.getPermissions().allowCourseApprove(courseId) ||
				Session.getPermissions().allowCourseEdit(courseId)) {
			Cost cost = CasualPriceCalculator.costOfAllActivities(courseId);
			course.setCost(cost.getPriceStr());
			if (
					cost.dollars > course.getTotalBudgetDollars() ||
					(cost.dollars == course.getTotalBudgetDollars() &
							cost.cents > course.getTotalBudgetCents())) {
				course.setOverpriced(true);
			}
		}
		
		// check if adding activities is allowed
		if (Session.getPermissions().allowCourseEdit(courseId)) {
			addActivity = true;
		}
		
		// check if view status is allowed
		if (
				Session.getPermissions().allowCourseEdit(courseId) ||
				Session.getPermissions().allowCourseApprove(courseId)) {
			approvalView = true;
		}
		
		// check if the approval buttons should be displayed.
		if (
				(course.getStatus().equals(Constants.COURSE_STATUS_EDITING) ||
				course.getStatus().equals(Constants.COURSE_STATUS_REJECTED)) &&
				Session.getPermissions().allowCourseEdit(courseId)) {
			approvalButtons = true;
		}
		else if (
				course.getStatus().equals(Constants.COURSE_STATUS_PENDING) &&
				Session.getPermissions().allowCourseApprove(courseId)) {
			approvalButtons = true;
		}
		
	}
	
	public Course getCourse() {
		return course;
	}
	
	private void addActivity(Row row) {
		Activity act = new Activity();
		act.setFromRow(row);
		activities.add(act);
	}

	public boolean isActivityLoadError() {
		return activityLoadError;
	}
	
	public ArrayList<Activity> getActivities() {
		return activities;
	}
	
	public String getViewTitle() {
		return course.getCourseName();
	}
	
	public String getViewDescription() {
		return course.getDescription();
	}
	
	public String[][] getViewActivities() {
		String[][] actArray = new String[activities.size()][ACTIVITY_LEN];
		
		for (int i = 0; i < activities.size(); i++) {
			Activity act = activities.get(i);
			actArray[i][0] = act.getActivityId();
			actArray[i][1] = act.getType();
			actArray[i][2] = act.getDay();
			actArray[i][3] = act.getStartTimeStr();
			if (act.getAssignedCasualId() != null) {
				actArray[i][4] = act.getAssignedCasualId() + " " + act.getAssignedCasualName();
			}
			else {
				actArray[i][4] = "None";
			}
		}
		
		return actArray;
	}
	
	public String getViewBudget() {
		return course.getBudgetTotal();
	}
	
	public int getViewStatus() {
		String status = course.getStatus();
		if (status.equals("editing")) {
			return CourseView.APPROVAL_EDITING;
		}
		else if (status.equals("pending")) {
			return CourseView.APPROVAL_PENDING;
		}
		else if (status.equals("accepted")) {
			return CourseView.APPROVAL_ACCEPTED;
		}
		else if (status.equals("rejected")) {
			return CourseView.APPROVAL_REJECTED;
		}
		else {
			return 0;
		}
	}
	
	public String getViewCost() {
		return course.getCost();
	}
	
	public boolean getViewOverpriced() {
		return course.isOverpriced();
	}
	
	public boolean getViewApprovalButtons() {
		return approvalButtons;
	}

	public boolean isAddActivity() {
		return addActivity;
	}

	public boolean isApprovalView() {
		return approvalView;
	}
	
	public boolean getError() {
		return error;
	}

}
