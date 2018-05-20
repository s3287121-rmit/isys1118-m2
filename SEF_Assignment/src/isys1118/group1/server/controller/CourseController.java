package isys1118.group1.server.controller;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.model.CourseModel;
import isys1118.group1.shared.view.CourseView;
import isys1118.group1.shared.view.View;

public class CourseController extends Controller {
	
	CourseModel model;
	CourseView view = new CourseView();
	
	public CourseController(String courseId) {
		model = new CourseModel(courseId);
	}

	@Override
	protected void loadData() {
		// get row for course
		try {
			Table fullTable = Database.getDatabase().getFullTable("courses");
			Row row = fullTable.getRowEquals("courseid", model.getCourseId());
			if (row != null) {
				model.setCourseFromRow(row);
				view.setCourseId(model.getCourseId());
				view.setTitle(model.getViewTitle());
				view.setDescription(model.getViewDescription());
				view.setActivities(model.getViewActivities());
				view.setBudget(model.getViewBudget());
				view.setApprovalStatus(model.getViewStatus());
				view.setCost(model.getViewCost());
				view.setOverpriced(model.getViewOverpriced());
			}
			else {
				// TODO show 404 page.
			}
			// TODO check that the user has access to the course and send a bad view if they do.
		} catch (Exception e) {
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
