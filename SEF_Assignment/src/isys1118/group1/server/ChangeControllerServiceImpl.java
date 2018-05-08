package isys1118.group1.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import isys1118.group1.client.ChangeControllerService;
import isys1118.group1.server.controller.ActivityController;
import isys1118.group1.server.controller.ActivityEditController;
import isys1118.group1.server.controller.Controller;
import isys1118.group1.server.controller.CourseController;
import isys1118.group1.server.controller.CourseListController;
import isys1118.group1.server.controller.MenuController;
import isys1118.group1.server.session.Session;
import isys1118.group1.shared.view.ViewSerial;

@SuppressWarnings("serial")
public class ChangeControllerServiceImpl extends RemoteServiceServlet implements ChangeControllerService {

	@Override
	public ViewSerial changeController(String name, String id) throws Throwable {
		
		// type == Course
		Controller toSet = null;
		if (name.equals("course")) {
			toSet = new CourseController();
		}
		else if (name.equals("activity")) {
			toSet = new ActivityController();
		}
		else if (name.equals("courselist")) {
			toSet = new CourseListController();
		}
		else if (name.equals("login")) {
			MenuController mc = new MenuController();
			Session.sessionInst.setMenu(mc);
			Session.sessionInst.setController(null);
			Session.sessionInst.run();
			return Session.sessionInst.getViewSerial();
		}
		else if (name.equals("logout")) {
			// TODO login creation and setting
		}
		else if (name.equals("edit")) {
			toSet = new ActivityEditController();
		}
		if (toSet != null) {
			Session.sessionInst.setController(toSet);
			Session.sessionInst.run();
			return Session.sessionInst.getViewSerial();
		}
		
		throw new Throwable("Couldn't find a controller type.");
	}
	
}
