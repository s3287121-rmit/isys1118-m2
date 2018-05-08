package isys1118.group1.server.controller;

import isys1118.group1.shared.model.ActivityModel;
import isys1118.group1.shared.model.CourseModel;
import isys1118.group1.shared.view.ActivityView;
import isys1118.group1.shared.view.CourseView;
import isys1118.group1.shared.view.View;

public class ActivityController extends Controller {
	
	ActivityModel model = new ActivityModel();
	ActivityView view = new ActivityView();

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
