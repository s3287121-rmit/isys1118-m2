package isys1118.group1.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import isys1118.group1.client.LoginService;
import isys1118.group1.server.controller.CourseListController;
import isys1118.group1.server.controller.MenuController;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.session.Session;
import isys1118.group1.server.user.CourseCoordinator;
import isys1118.group1.server.user.User;
import isys1118.group1.shared.view.ViewSerial;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	@Override
	public ViewSerial login(String username, String password) {
		
		// check if not filled out
		if (username == null || username.isEmpty() ||
				password == null || password.isEmpty()) {
			return ViewSerial.createError("User / Password has not been given.");
		}

		try {
			Table users = Database.getDatabase().getFullTable("users");
			
			// try id + password
			Row theUser = users.getRowEquals(
					new String[] {"userid", "password"},
					new String[] {username, password});
			
			// if failed, try name + password
			if (theUser == null) {
				theUser = users.getRowEquals(
						new String[] {"name", "password"},
						new String[] {username, password});
			}
			
			// if fail again, return null to indicate we couldn't log in.
			if (theUser == null) {
				return ViewSerial.createError("User / Password is incorrect.");
			}
			
			// otherwise, log in
			
			// check for type and create User
			User userObj;
			if (theUser.get("type").equals("coordinator")) {
				userObj = new CourseCoordinator(
						theUser.get("userid"),
						theUser.get("name"));
			}
			else if (theUser.get("type").equals("approver")) {
				userObj = new CourseCoordinator(
						theUser.get("userid"),
						theUser.get("name"));
			}
			else {
				return ViewSerial.createError("User " + theUser.get("userid") + " " +
						theUser.get("name") +
						" cannot log in with their permissions.");
			}
			
			// log in
			Session.sessionInst.setLoggedInUser(userObj);
			
			// set views
			MenuController mc = new MenuController();
			Session.sessionInst.setMenu(mc);
			CourseListController clc = new CourseListController();
			Session.sessionInst.setController(clc);
			Session.sessionInst.run();
			return Session.sessionInst.getViewSerial();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
