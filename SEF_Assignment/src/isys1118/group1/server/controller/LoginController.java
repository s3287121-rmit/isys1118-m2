package isys1118.group1.server.controller;

import isys1118.group1.server.model.LoginModel;
import isys1118.group1.shared.view.LoginView;
import isys1118.group1.shared.view.View;

public class LoginController extends Controller {
	
	LoginView view = new LoginView();
	LoginModel model = new LoginModel();

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
	}

	@Override
	public View getView() {
		return view;
	}

}
