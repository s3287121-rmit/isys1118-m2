package isys1118.group1.shared.model;

import java.util.ArrayList;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.shared.view.CourseView;
import isys1118.group1.server.Activity;
import isys1118.group1.server.Course;

public class CourseModel extends Model {
	
	private static final int ACTIVITY_LEN = 5;
	
	private final String courseId;
	private Course course;
	private ArrayList<Activity> activities;
	private boolean activityLoadError = false;
	
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
		try {
			Table allActivities = Database.getDatabase().getFullTable("activities");
			ArrayList<Row> actList = allActivities.getRowsEqual("courseid", course.getCourseId());
			for (Row act : actList) {
				addActivity(act);
			}
		} catch (Exception e) {
			activityLoadError = true;
			e.printStackTrace();
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

}
