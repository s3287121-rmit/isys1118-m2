package isys1118.group1.server.helpers;

import java.io.IOException;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.session.Session;
import isys1118.group1.server.user.User;
import isys1118.group1.shared.error.DatabaseException;

public class Permissions {
	
	/**
	 * Checks if the currently signed in user has permission to view a course.
	 * @param id
	 * @return
	 */
	public boolean allowCourseView(String id) {
		User curUser = Session.sessionInst.getLoggedInUser();
		if (curUser == null) {
			return false;
		}
		
		try {
			Table users = Database.getDatabase().getFullTable("users");
			Table courses = Database.getDatabase().getFullTable("courses");
			Table casualDetails
				= Database.getDatabase().getFullTable("casualdetails");
			Row userRow = users.getRowEquals(
					"userid",
					curUser.accountId);
			
			// if there is no user, it's automatically false
			if (userRow == null) {
				return false;
			}
			
			// if the user is an approver, it's automatically true
			if (
					curUser.getUserType().equals(User.TYPE_APPROVER) ||
					curUser.getUserType().equals(User.TYPE_ADMIN)) {
				return true;
			}
			
			// if user is course coordinator, we need to check that they are
			// assigned to the course
			if (curUser.getUserType().equals(User.TYPE_COORDINATOR)) {
				Row courseRow = courses.getRowEquals("courseid", id);
				if (courseRow == null) {
					return false;
				}
				if (courseRow.get("coordinator").equals(curUser.accountId)) {
					return true;
				}
			}
			
			// if user is a casual, check to see if they are assigned to this
			// course.
			if (curUser.getUserType().equals(User.TYPE_CASUAL)) {
				Row cdCheck = casualDetails.getRowEquals(
						new String[] {"userid", "course"},
						new String[] {curUser.accountId, id});
				if (cdCheck != null) {
					return true;
				}
			}
			
		} catch (DatabaseException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Checks if the current user has permission to access the course.
	 * @param id
	 * @return
	 */
	public boolean allowActivityView(String id) {
		User curUser = Session.sessionInst.getLoggedInUser();
		if (curUser == null) {
			return false;
		}
		
		try {
			Table users = Database.getDatabase().getFullTable("users");
			Table courses = Database.getDatabase().getFullTable("courses");
			Table activities
				= Database.getDatabase().getFullTable("activities");
			Table assignments
				= Database.getDatabase().getFullTable("assignments");
			Row userRow = users.getRowEquals(
					"userid",
					curUser.accountId);
			
			// if there is no user, it's automatically false
			if (curUser == null || userRow == null) {
				return false;
			}
			
			// if the user is an approver, it's automatically true
			if (
					curUser.getUserType().equals(User.TYPE_APPROVER) ||
					curUser.getUserType().equals(User.TYPE_ADMIN)) {
				return true;
			}
			
			// if user is course coordinator, we need to check that they are
			// assigned to the course
			if (curUser.getUserType().equals(User.TYPE_COORDINATOR)) {

				Row actRow = activities.getRowEquals("activityid", id);
				if (actRow == null) {
					DatabaseException de = new DatabaseException();
					de.setMessage("Activity " + id + " could not be found in" +
							"the database.");
					throw de;
				}
				Row courseRow = courses.getRowEquals(
						"courseid", actRow.get("courseid"));
				if (courseRow == null) {
					DatabaseException de = new DatabaseException();
					de.setMessage("Course " + actRow.get("courseid") +
							" could not be found in the database.");
					throw de;
				}
				
				if (courseRow.get("coordinator").equals(curUser.accountId)) {
					return true;
				}
			}
			
			// if user is a casual, check to see if they are assigned to this
			// activity.
			if (curUser.getUserType().equals(User.TYPE_CASUAL)) {
				Row cdCheck = assignments.getRowEquals(
						new String[] {"userid", "activityid"},
						new String[] {curUser.accountId, id});
				if (cdCheck != null) {
					return true;
				}
			}
			
		} catch (DatabaseException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Checks if the user has permission to edit the course (add activities,
	 * send to approver for approval)
	 * @param id
	 * @return
	 */
	public boolean allowCourseEdit(String id) {
		User curUser = Session.sessionInst.getLoggedInUser();
		if (curUser == null) {
			return false;
		}
		
		try {
			Table users = Database.getDatabase().getFullTable("users");
			Table courses = Database.getDatabase().getFullTable("courses");
			Row userRow = users.getRowEquals(
					"userid",
					curUser.accountId);
			
			// if there is no user, it's automatically false
			if (curUser == null || userRow == null) {
				return false;
			}
			
			// if the user is an admin, it's automatically true
			if (curUser.getUserType().equals(User.TYPE_ADMIN)) {
				return true;
			}
			
			// if user is course coordinator, we need to check that they are
			// assigned to the course
			if (curUser.getUserType().equals(User.TYPE_COORDINATOR)) {
				Row courseRow = courses.getRowEquals("courseid", id);
				if (courseRow == null) {
					return false;
				}
				if (courseRow.get("coordinator").equals(curUser.accountId)) {
					return true;
				}
			}
			
		} catch (DatabaseException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Checks if the user has permission to approve the course.
	 * @param id
	 * @return
	 */
	public boolean allowCourseApprove(String id) {
		User curUser = Session.sessionInst.getLoggedInUser();
		if (curUser == null) {
			return false;
		}
		
		try {
			Table users = Database.getDatabase().getFullTable("users");
			Row userRow = users.getRowEquals(
					"userid",
					curUser.accountId);
			
			// if there is no user, it's automatically false
			if (curUser == null || userRow == null) {
				return false;
			}
			
			// if the user is an approver, it's automatically true
			if (
					curUser.getUserType().equals(User.TYPE_APPROVER) ||
					curUser.getUserType().equals(User.TYPE_ADMIN)) {
				return true;
			}
			
		} catch (DatabaseException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Checks if the current user has permission to edit an activity.
	 * @param activityId
	 * @param courseId
	 * @return
	 */
	public boolean allowActivityEdit(String activityId, String courseId) {
		User curUser = Session.sessionInst.getLoggedInUser();
		if (curUser == null) {
			return false;
		}
		
		try {
			Table users = Database.getDatabase().getFullTable("users");
			Table courses = Database.getDatabase().getFullTable("courses");
			Table activities
				= Database.getDatabase().getFullTable("activities");
			Row userRow = users.getRowEquals(
					"userid",
					curUser.accountId);
			
			// if there is no user, it's automatically false
			if (curUser == null || userRow == null) {
				return false;
			}
			
			// if the user is an admin, it's automatically true
			if (curUser.getUserType().equals(User.TYPE_ADMIN)) {
				return true;
			}
			
			// if user is course coordinator, we need to check that they are
			// assigned to the course
			if (curUser.getUserType().equals(User.TYPE_COORDINATOR)) {

				Row courseRow;
				if (courseId == null) {
					Row actRow
						= activities.getRowEquals("activityid", activityId);
					if (actRow == null) {
						DatabaseException de = new DatabaseException();
						de.setMessage("Activity " + activityId +
								" could not be found in the database.");
						throw de;
					}
					courseRow = courses.getRowEquals(
							"courseid", actRow.get("courseid"));
					if (courseRow == null) {
						DatabaseException de = new DatabaseException();
						de.setMessage("Course " + actRow.get("courseid") +
								" could not be found in the database.");
						throw de;
					}
				}
				else {
					courseRow = courses.getRowEquals("courseid", courseId);
					if (courseRow == null) {
						DatabaseException de = new DatabaseException();
						de.setMessage("Course " + courseId +
								" could not be found in the database.");
						throw de;
					}
				}
				
				if (courseRow.get("coordinator").equals(curUser.accountId)) {
					return true;
				}
			}
			
		} catch (DatabaseException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
}
