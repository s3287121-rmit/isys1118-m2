package isys1118.group1.server.controller;

import isys1118.group1.shared.model.CourseListModel;
import isys1118.group1.shared.view.CourseListView;
import isys1118.group1.shared.view.View;

public class CourseListController extends Controller {
	
	CourseListModel model = new CourseListModel();
	CourseListView view = new CourseListView();

	@Override
	protected void loadData() {
		model.setCoursesFromUser("");
		// view.setView(model);
		view.setTitle("All Courses");
		view.setCourses(model.getCoursesForView());
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
