package isys1118.group1.server.controller;

import isys1118.group1.shared.model.CourseListModel;
import isys1118.group1.shared.model.CourseModel;
import isys1118.group1.shared.view.CourseListView;
import isys1118.group1.shared.view.CourseView;
import isys1118.group1.shared.view.View;

public class CourseListController extends Controller {
	
	CourseListModel model = new CourseListModel();
	CourseListView view = new CourseListView();

	@Override
	protected void loadData() {
		view.setView(model);
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
