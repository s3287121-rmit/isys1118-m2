package isys1118.group1.server.controller;

import java.io.IOException;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.model.CourseModel;
import isys1118.group1.shared.error.DatabaseException;
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
				if (model.getError()) {
					error = true;
					return;
				}
				view.setCourseId(model.getCourseId());
				view.setTitle(model.getViewTitle());
				view.setDescription(model.getViewDescription());
				view.setActivities(model.getViewActivities());
				view.setBudget(model.getViewBudget());
				view.setApprovalStatus(model.getViewStatus());
				view.setCost(model.getViewCost());
				view.setOverpriced(model.getViewOverpriced());
				view.setApprovalButtons(model.getViewApprovalButtons());
				view.setAddActivity(model.isAddActivity());
				view.setApprovalView(model.isApprovalView());
			}
			else {
				error = true;
			}
		} catch (DatabaseException | IOException e) {
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
