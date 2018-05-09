package isys1118.group1.server.session;

import java.util.Random;

import isys1118.group1.server.controller.Controller;
import isys1118.group1.server.controller.MenuController;
import isys1118.group1.server.database.Database;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class Session {
	
	public static Session sessionInst;
	
	public final long sessionId;
	
	private Controller currentController;
	private MenuController menuController;
	
	private Session(long sessionId) {
		this.sessionId = sessionId;
	}
	
	public void setController(Controller controller) {
		currentController = controller;
	}
	
	public void setMenu(MenuController controller) {
		menuController = controller;
	}
	
	public void run() {
		if (currentController != null) {
			currentController.run();
		}
		if (menuController != null) {
			menuController.run();
		}
		else {
			menuController = new MenuController();
			menuController.run();
		}
	}
	
	public static void createSession() {
		if (checkSession()) {
			// TODO throw error
			return;
		}
		Random r = new Random();
		sessionInst = new Session(r.nextLong());
		try {
			Database.connectToDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkSession() {
		return sessionInst != null;
	}
	
	public View getCurrentView() {
		if (currentController != null) {
			return currentController.getView();
		}
		// has already been shown or does not exist.
		return null;
	}
	
	public View getMenuView() {
		if (menuController != null) {
			return menuController.getView();
		}
		// has already been shown or does not exist.
		return null;
	}
	
	public ViewSerial getViewSerial() {
		return ViewSerial.create(getMenuView(), getCurrentView());
	}
	
}
