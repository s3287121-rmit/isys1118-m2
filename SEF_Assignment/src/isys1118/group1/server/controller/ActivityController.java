package isys1118.group1.server.controller;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.model.ActivityModel;
import isys1118.group1.shared.view.ActivityView;
import isys1118.group1.shared.view.View;

public class ActivityController extends Controller {
	
	private ActivityModel model;
	private ActivityView view = new ActivityView();
	
	public ActivityController(String courseId) {
		model = new ActivityModel(courseId);
	}

	@Override
	protected void loadData() {
		try {
			Table fullTable = Database.getDatabase().getFullTable("activities");
			Row row = fullTable.getRowEquals(
					"activityid", model.getActivityId());
			if (row != null) {
				model.setActivityFromRow(row);
				model.setCasualCost();
				view.setActivityId(model.getActivityId());
				view.setTitle("Activity");
				view.setCourseId(model.getViewCourseId());
				view.setCourseName(model.getViewCourseName());
				view.setType(model.getViewType());
				view.setDay(model.getViewDay());
				view.setStartTime(model.getViewTime());
				view.setDuration(model.getViewDuration());
				view.setCasual(model.getViewAssignedCasual());
				view.setCasualPrice(model.getViewCost());
				view.setCanEdit(model.getViewCanEdit());
				view.setCanViewCost(model.getViewCanViewCost());
			}
			else {
				error = true;
			}
		} catch (Exception e) {
			error = true;
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		view.show();
		viewShown = true;
	}

	@Override
	public void run() {
		loadData();
	}

	@Override
	public View getView() {
		return view;
	}

}
