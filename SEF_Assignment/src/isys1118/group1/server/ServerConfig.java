package isys1118.group1.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.session.Session;

public class ServerConfig implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Session.createSession();
		ServletContext context = arg0.getServletContext();
		String pathToDB = context.getRealPath("/db/");
		try {
			Database.connectToDatabase(pathToDB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
