package isys1118.group1.server.session;

import java.util.Random;

import isys1118.group1.server.controller.Controller;
import isys1118.group1.server.controller.MenuController;
import isys1118.group1.server.helpers.Permissions;
import isys1118.group1.server.user.User;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class Session {
	
	public static Session sessionInst;
	
	public final long sessionId;
	
	private final Permissions permissionInst;
	
	private Controller currentController;
	private MenuController menuController;
	
	private User loggedInUser;
	
	private Session(long sessionId) {
		this.sessionId = sessionId;
		this.permissionInst = new Permissions();
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
	}
	
	public static boolean checkSession() {
		return sessionInst != null;
	}
	
	public static Permissions getPermissions() {
		if (!checkSession()) {
			return null;
		}
		else {
			return sessionInst.permissionInst;
		}
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

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
}
