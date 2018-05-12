package isys1118.group1.server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import isys1118.group1.client.MenuService;
import isys1118.group1.server.controller.ActivityController;
import isys1118.group1.server.controller.ActivityEditController;
import isys1118.group1.server.controller.Controller;
import isys1118.group1.server.controller.CourseController;
import isys1118.group1.server.controller.CourseListController;
import isys1118.group1.server.controller.MenuController;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.session.Session;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

@SuppressWarnings("serial")
public class MenuServiceImpl extends RemoteServiceServlet implements MenuService {
	
	public View getMenuOption(String option) {
		return null;
	}

	@Override
	public ViewSerial resetDatabase(String name, String id) throws Exception {
		
		File dbDir = Database.getDatabase().dbDir;
		File backupDir = new File(dbDir, "/.backup/");
		File[] toDelete = dbDir.listFiles();
		
		// delete all current files
		for (File f : toDelete) {
			f.delete();
		}
		
		// replace with backup files
		FileUtils.copyDirectory(backupDir, dbDir);
		
		// set controller again
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
	
}
