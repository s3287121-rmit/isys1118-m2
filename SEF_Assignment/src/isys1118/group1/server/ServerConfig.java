package isys1118.group1.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import isys1118.group1.server.controller.CourseController;
import isys1118.group1.server.controller.MenuController;
import isys1118.group1.server.session.Session;

public class ServerConfig implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Session.createSession();
		MenuController mc = new MenuController();
		Session.sessionInst.setMenu(mc);
	}

}
