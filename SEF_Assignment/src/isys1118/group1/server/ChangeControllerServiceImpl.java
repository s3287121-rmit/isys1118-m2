package isys1118.group1.server;

import java.util.Random;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import isys1118.group1.client.ChangeControllerService;
import isys1118.group1.server.controller.ActivityController;
import isys1118.group1.server.controller.ActivityEditController;
import isys1118.group1.server.controller.Controller;
import isys1118.group1.server.controller.CourseController;
import isys1118.group1.server.controller.CourseListController;
import isys1118.group1.server.controller.MenuController;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.session.Session;
import isys1118.group1.shared.view.ViewSerial;

@SuppressWarnings("serial")
public class ChangeControllerServiceImpl extends RemoteServiceServlet implements ChangeControllerService {

	@Override
	public ViewSerial changeController(String name, String id) throws Exception {
		
		// type == Course
		Controller toSet = null;
		if (name.equals("course")) {
			toSet = new CourseController(id);
		}
		// type == activity
		else if (name.equals("activity")) {
			toSet = new ActivityController(id);
		}
		// type == course list
		else if (name.equals("courselist")) {
			toSet = new CourseListController();
		}
		// type == login - used only when user just logged in
		else if (name.equals("login")) {
			MenuController mc = new MenuController();
			Session.sessionInst.setMenu(mc);
			Session.sessionInst.setController(null);
			Session.sessionInst.run();
			return Session.sessionInst.getViewSerial();
		}
		// type == logout - used only when user just logged out
		else if (name.equals("logout")) {
			// TODO login creation and setting
		}
		// type == activity edit
		else if (name.equals("edit")) {
			toSet = new ActivityEditController(id, false);
		}
		if (toSet != null) {
			Session.sessionInst.setController(toSet);
			Session.sessionInst.run();
			return Session.sessionInst.getViewSerial();
		}
		
		throw new Exception("Couldn't find a controller type.");
	}

	@Override
	public ViewSerial addActivity(String courseId) throws Exception {

		// create random id and check table to see if it exists
		Table fullTable = Database.getDatabase().getFullTable("activities");
		Row check = null;
		Random rand = new Random();
		int nextId = 0;
		do {
			nextId = rand.nextInt(9999) + 1;
			fullTable.getRowEquals("activityid", String.valueOf(nextId));
		}
		while (check != null);
		
		// TODO create activityedit with random id
		ActivityEditController aec = new ActivityEditController(String.valueOf(nextId), true);
		
		
		return null;
	}
	
}
