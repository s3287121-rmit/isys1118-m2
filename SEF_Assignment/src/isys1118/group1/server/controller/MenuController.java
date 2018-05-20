package isys1118.group1.server.controller;

import isys1118.group1.server.model.MenuModel;
import isys1118.group1.shared.view.MenuView;
import isys1118.group1.shared.view.View;

public class MenuController extends Controller {
	
	MenuModel model = new MenuModel();
	MenuView view = new MenuView();

	@Override
	protected void loadData() {
		view.setView();
	}

	@Override
	public void show() {
		view.show();
	}

	@Override
	public void run() {
		loadData();
		viewShown = true;
	}

	@Override
	public View getView() {
		return view;
	}

}
