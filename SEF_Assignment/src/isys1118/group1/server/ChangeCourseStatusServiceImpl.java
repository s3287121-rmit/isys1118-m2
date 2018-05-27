package isys1118.group1.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import isys1118.group1.client.ChangeCourseStatusService;
import isys1118.group1.server.controller.CourseController;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.session.Session;
import isys1118.group1.shared.Constants;
import isys1118.group1.shared.error.PermissionException;
import isys1118.group1.shared.view.ViewSerial;

@SuppressWarnings("serial")
public class ChangeCourseStatusServiceImpl extends RemoteServiceServlet implements ChangeCourseStatusService {

	@Override
	public ViewSerial changeStatus(String courseId, String status)
			throws PermissionException {
		
		// check if allowed. If not, then throw a permission exception
		if (status.equals(Constants.COURSE_STATUS_EDITING) ||
				status.equals(Constants.COURSE_STATUS_REJECTED)) {
			if (!Session.getPermissions().allowCourseEdit(courseId)) {
				PermissionException pe = new PermissionException();
				pe.set(
						Session.sessionInst.getLoggedInUser().accountId,
						Session.sessionInst.getLoggedInUser().name,
						"Update Course Status (id = " + courseId + ")");
				throw pe;
			}
		}
		else if (status.equals(Constants.COURSE_STATUS_PENDING)) {
			if (!Session.getPermissions().allowCourseApprove(courseId)) {
				PermissionException pe = new PermissionException();
				pe.set(
						Session.sessionInst.getLoggedInUser().accountId,
						Session.sessionInst.getLoggedInUser().name,
						"Update Course Status (id = " + courseId + ")");
				throw pe;
			}
		}
		
		try {
			// find course information
			Table fullTable = Database.getDatabase().getFullTable("courses");
			Row row = fullTable.getRowEquals("courseid", courseId);
			
			// change data
			row.setColumn("status", status);
			
			// update database
			fullTable.commitChanges();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// finished processing, redo course display
		Session.sessionInst.setController(new CourseController(courseId));
		Session.sessionInst.run();
		return Session.sessionInst.getViewSerial();
	}

	
}
