package isys1118.group1.server.model;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.helpers.Activity;
import isys1118.group1.server.helpers.CasualPriceCalculator;
import isys1118.group1.server.helpers.Cost;

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
	
	public void setCasualCost() {
		
		String casualId = activity.getAssignedCasualId();
		if (casualId == null || casualId.isEmpty()) {
			return;
		}
		
		// get cost
		Cost cost = CasualPriceCalculator.costOfActivity(
				activity.getAssignedCasualId(),
				activity.getActivityId());
		
		if (cost != null) {
			activity.setCasualPrice(cost.getPriceStr());
		}
	}
	
	public String getViewCost() {
		return activity.getCasualPrice();
	}

}
