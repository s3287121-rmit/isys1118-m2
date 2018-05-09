package isys1118.group1.shared.model;

import isys1118.group1.server.Activity;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;

public class ActivityModel extends Model {
	
	private final String activityId;
	private Activity activity;
	
	public ActivityModel(String activityId) {
		this.activityId = activityId;
	}
	
	public void setActivityFromRow(Row row) {
		activity = new Activity();
		activity.setFromRow(row);
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public String getViewId() {
		return activity.getActivityId();
	}
	
	public String getViewType() {
		return activity.getType();
	}
	
	public String getViewDay() {
		return activity.getDay();
	}
	
	public String getViewTime() {
		return activity.getStartTimeStr();
	}
	
	public String getViewDuration() {
		return activity.getDurationHours();
	}
	
	public String getViewCourseId() {
		return activity.getCourseId();
	}
	
	public String getViewCourseName() {
		try {
			Table fullTable = Database.getDatabase().getFullTable("courses");
			Row row = fullTable.getRowEquals("courseid", activity.getCourseId());
			if (row != null) {
				return row.get("coursename");
			}
			else {
				throw new Exception("Activity " + activityId + " does not have a course set.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getViewAssignedCasual() {
		return activity.getAssignedCasualStr();
	}

}
