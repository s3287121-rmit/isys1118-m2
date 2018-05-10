package isys1118.group1.shared.model;

import isys1118.group1.server.Activity;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;

public class ActivityEditModel extends Model {
	
	private final boolean constructed;
	private final String activityId;
	private Activity activity;
	
	public ActivityEditModel(String activityId, boolean constructed) {
		this.activityId = activityId;
		this.constructed = constructed;
		activity = new Activity();
	}
	
	public void setActivityFromRow(Row row) {
		activity.setFromRow(row);
	}
	
	public boolean isConstructed() {
		return constructed;
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public String getViewId() {
		return activity.getActivityId();
	}
	
	public void setActitiyId(String activityId) {
		activity.setActivityId(activityId);;
	}
	
	public String getViewType() {
		return activity.getType();
	}
	
	public void setActitiyType(String type) {
		activity.setType(type);
	}
	
	public String getViewDay() {
		return activity.getDay();
	}
	
	public void setActitiyDay(String day) {
		activity.setType(day);
	}
	
	public String getViewTime() {
		return activity.getStartTimeStr();
	}
	
	public int getViewTimeH() {
		return activity.getStartTimeH();
	}
	
	public int getViewTimeM() {
		return activity.getStartTimeM();
	}
	
	public void setActitiyTimeH(int startTimeH) {
		activity.setStartTimeH(startTimeH);
	}
	
	public void setActitiyTimeM(int startTimeM) {
		activity.setStartTimeM(startTimeM);
	}
	
	public String getViewDuration() {
		return activity.getDurationHours();
	}
	
	public int getViewDurationM() {
		return activity.getDurationM();
	}
	
	public void setActitiyDurationM(int durationM) {
		activity.setDurationM(durationM);
	}
	
	public String getViewCourseId() {
		return activity.getCourseId();
	}
	
	public void setActitiyCourseId(String courseId) {
		activity.setCourseId(courseId);
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
	
	public String getViewAssignedCasualId() {
		return activity.getAssignedCasualId();
	}
	
	public String getViewAssignedCasualName() {
		return activity.getAssignedCasualName();
	}
	
	public void setActitiyCasualId(String casualId) {
		activity.setAssignedCasualId(casualId);
	}
	
	public void setActitiyCasualName(String casualName) {
		activity.setAssignedCasualName(casualName);
	}

}
