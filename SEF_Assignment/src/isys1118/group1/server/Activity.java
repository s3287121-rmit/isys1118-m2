package isys1118.group1.server;

import java.io.Serializable;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;

@SuppressWarnings("serial")
public class Activity implements Serializable {

	private String activityId = null;
	private String courseId = null;
	private String type = null;
	private String day = null;
	private int startTimeH = -1;
	private int startTimeM = -1;
	private int durationM = -1;
	private String assignedCasualId = null;
	private String assignedCasualName = null;
	
	public void setFromRow(Row row) {
		this.activityId = row.get("activityid");
		this.courseId = row.get("courseid");
		this.type = row.get("type");
		this.day = row.get("day");
		String timeHStr = row.get("timeh");
		this.startTimeH = Integer.parseInt(timeHStr);
		String timeMStr = row.get("timem");
		this.startTimeM = Integer.parseInt(timeMStr);
		String durationStr = row.get("durationm");
		this.durationM = Integer.parseInt(durationStr);
		
		// get assigned casual using casualId
		try {
			Table assignments = Database.getDatabase().getFullTable("assignments");
			Table users = Database.getDatabase().getFullTable("users");
			Row assignedCasRow = assignments.getRowEquals("activityid", this.activityId);
			// if a row is found in the assignments database with this activity,
			// then we assign the casual.
			if (assignedCasRow != null) {
				this.assignedCasualId = assignedCasRow.get("userid");
				Row cas = users.getRowEquals("userid", assignedCasualId);
				this.setAssignedCasualName(cas.get("name"));
			}
			// otherwise the casual is null
			else {
				this.assignedCasualId = null;
				this.assignedCasualName = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public int getStartTimeH() {
		return startTimeH;
	}
	
	public void setStartTimeH(int startTimeH) {
		this.startTimeH = startTimeH;
	}
	
	public void setStartTimeH(String startTimeH) {
		this.startTimeH = Integer.parseInt(startTimeH);
	}
	
	public int getStartTimeM() {
		return startTimeM;
	}
	
	public void setStartTimeM(int startTimeM) {
		this.startTimeM = startTimeM;
	}
	
	public void setStartTimeM(String startTimeM) {
		this.startTimeM = Integer.parseInt(startTimeM);
	}
	
	public String getStartTimeStr() {
		return String.valueOf(startTimeH) + ":" + String.format("%02d", startTimeM);
	}
	
	public int getDurationM() {
		return durationM;
	}
	
	public void setDurationM(int durationM) {
		this.durationM = durationM;
	}
	
	public void setDurationM(String durationM) {
		this.durationM = Integer.parseInt(durationM);
	}
	
	public String getDurationHours() {
		int num = durationM / 60;
		int denom = durationM % 60;
		String hourWord = (num == 1 && denom == 0) ? " hour" : " hours";
		String timeStr =
				(denom == 0) ?
				String.valueOf(num) :
				String.valueOf(num) + "." + String.valueOf(denom);
		return timeStr + hourWord;
	}
	
	public String getAssignedCasualId() {
		return assignedCasualId;
	}
	
	public void setAssignedCasualId(String assignedCasualId) {
		this.assignedCasualId = assignedCasualId;
	}
	
	public String getAssignedCasualName() {
		return assignedCasualName;
	}

	public void setAssignedCasualName(String assignedCasualName) {
		this.assignedCasualName = assignedCasualName;
	}
	
	public String getAssignedCasualStr() {
		if (assignedCasualId == null) {
			return "None";
		}
		else {
			return assignedCasualId + " " + assignedCasualName;
		}
	}

	public void setRowToThis(Row dst) {
		dst.setColumn("activityid", activityId);
		dst.setColumn("type", type);
		dst.setColumn("day", day);
		dst.setColumn("timeh", String.valueOf(startTimeH));
		dst.setColumn("timem", String.valueOf(startTimeM));
		dst.setColumn("durationm", String.valueOf(durationM));
		dst.setColumn("courseid", courseId);
	}
	
	public void saveCasualAssignment() {
		try {
			// find the relevant assignment (only one per activity)
			Table allAssignments = Database.getDatabase().getFullTable("assignments");
			Row assignment = allAssignments.getRowEquals("activityid", activityId);
			// if it has not been assigned, we need to create it.
			if (assignment == null) {
				assignment = allAssignments.createNewRow(new String[] {assignedCasualId, activityId});
			}
			// if it has been assigned, we need to update information
			else {
				assignment.setColumn("userId", assignedCasualId);
			}
			allAssignments.commitChanges();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
