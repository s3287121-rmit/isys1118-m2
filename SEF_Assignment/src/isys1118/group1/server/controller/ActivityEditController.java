package isys1118.group1.server.controller;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.shared.model.ActivityEditModel;
import isys1118.group1.shared.view.ActivityEditView;
import isys1118.group1.shared.view.View;

public class ActivityEditController extends Controller {
	
	ActivityEditModel model;
	ActivityEditView view = new ActivityEditView();
	
	public ActivityEditController(String activityId, boolean constructed) {
		model = new ActivityEditModel(activityId, constructed);
	}
	
	public void loadEmpty(String courseId) {
		model.setActitiyCourseId(courseId);
	}

	@Override
	protected void loadData() {
		if (!model.isConstructed()) {
			Table fullTable;
			try {
				fullTable = Database.getDatabase().getFullTable("activities");
				Row row = fullTable.getRowEquals("activityid", model.getActivityId());
				if (row != null) {
					model.setActivityFromRow(row);
					view.setTitle("Edit Activity");
					view.setActivityId(model.getActivityId());
					view.setCourseId(model.getViewCourseId());
					view.setCasualName(model.getViewCourseName());
					view.setType(model.getViewType());
					view.setDay(model.getViewDay());
					view.setStartTimeH(model.getViewTimeH());
					view.setStartTimeM(model.getViewTimeM());
					view.setDuration(model.getViewDurationM());
					view.setCasualId(model.getViewAssignedCasualId());
					view.setCasualId(model.getViewAssignedCasualName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
