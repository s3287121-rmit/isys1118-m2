package isys1118.group1.server.helpers;

import java.io.Serializable;

import isys1118.group1.server.database.Row;

@SuppressWarnings("serial")
public class Course implements Serializable {
	
	private String courseId;
	private String courseName;
	private String description;
	private int totalBudgetDollars;
	private int totalBudgetCents;
	private String coordinatorId;
	private String status;
	private String cost;
	private boolean overpriced = false;
	
	public void setFromRow(Row r) {
		this.courseId = r.get("courseid");
		this.courseName = r.get("coursename");
		this.description = r.get("description");
		String budgetDStr = r.get("totalbudgetd");
		this.totalBudgetDollars = Integer.valueOf(budgetDStr);
		String budgetCStr = r.get("totalbudgetc");
		this.totalBudgetCents = Integer.valueOf(budgetCStr);
		this.coordinatorId = r.get("coordinator");
		this.status = r.get("status");
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getTotalBudgetDollars() {
		return totalBudgetDollars;
	}
	
	public void setTotalBudgetDollars(int totalBudgetDollars) {
		this.totalBudgetDollars = totalBudgetDollars;
	}
	
	public void setTotalBudgetDollars(String totalBudgetDollars) {
		this.totalBudgetDollars = Integer.parseInt(totalBudgetDollars);
	}
	
	public int getTotalBudgetCents() {
		return totalBudgetCents;
	}
	
	public void setTotalBudgetCents(int totalBudgetCents) {
		this.totalBudgetCents = totalBudgetCents;
	}
	
	public void setTotalBudgetCents(String totalBudgetCents) {
		this.totalBudgetCents = Integer.parseInt(totalBudgetCents);
	}
	
	public String getBudgetTotal() {
		return "$" + String.valueOf(totalBudgetDollars) + String.format(".%02d", totalBudgetCents);
	}
	
	public String getCoordinatorId() {
		return coordinatorId;
	}
	
	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public boolean isOverpriced() {
		return overpriced;
	}

	public void setOverpriced(boolean overpriced) {
		this.overpriced = overpriced;
	}

}
