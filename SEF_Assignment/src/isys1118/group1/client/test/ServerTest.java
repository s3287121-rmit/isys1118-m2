package isys1118.group1.client.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import isys1118.group1.server.controller.ActivityController;
import isys1118.group1.server.controller.Controller;
import isys1118.group1.server.controller.CourseController;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.session.Session;
import isys1118.group1.server.user.Administrator;
import isys1118.group1.shared.error.DatabaseException;
import isys1118.group1.shared.view.ActivityView;
import isys1118.group1.shared.view.CourseView;
import isys1118.group1.shared.view.ViewSerial;

public class ServerTest {
	
	private static final String USER_ID = "e99999";
	private static final String USER_NAME = "Loki";
	
	private static final String TEST_COURSE_ID = "test0000";
	private static final String TEST_COURSE_NAME = "TEST 1";
	private static final String TEST_COURSE_DESC
		= "Testing course for approval.";
	private static final String TEST_COURSE_BUDGET_D = "100000";
	private static final String TEST_COURSE_BUDGET_C = "00";
	private static final String TEST_COURSE_COORDINATOR = "e25231";
	private static final String TEST_COURSE_STATUS = "pending";
	
	private static final String TEST_ACTIVITY_ID = "9999";
	private static final String TEST_ACTIVITY_TYPE = "Tutorial";
	private static final String TEST_ACTIVITY_DAY = "Monday";
	private static final String TEST_ACTIVITY_TIME_H = "12";
	private static final String TEST_ACTIVITY_TIME_M = "00";
	private static final String TEST_ACTIVITY_DURATION_M = "120";
	private static final String TEST_ACTIVITY_COURSE = TEST_COURSE_ID;

	private static final String TEST_CASUAL_ID = "e12348";
	
	@BeforeClass
	public static void setupClass() {
		
		// set up session
		Session.createSession();
		Administrator admin = new Administrator(USER_ID, USER_NAME);
		Session.sessionInst.setLoggedInUser(admin);
		
		// set up DB
		Path curPath = Paths.get("");
		String sPath = curPath.toAbsolutePath().toString();
		String pathToDB =
				sPath + File.separator + "war" + File.separator + "db";
		try {
			Database.connectToDatabase(pathToDB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// add a test course for approving.
		try {
			Table allCourses = Database.getDatabase().getFullTable("courses");
			allCourses.createNewRow(new String[] {
					TEST_COURSE_ID, TEST_COURSE_NAME,
					TEST_COURSE_DESC,
					TEST_COURSE_BUDGET_D, TEST_COURSE_BUDGET_C,
					TEST_COURSE_COORDINATOR,
					TEST_COURSE_STATUS
			});
			allCourses.commitChanges();
		}
		catch (DatabaseException | IOException e) {
			e.printStackTrace();
		}
		
		// add a test activity that allows you to add a user.
		try {
			Table allActivities
				= Database.getDatabase().getFullTable("activities");
			allActivities.createNewRow(new String[] {
					TEST_ACTIVITY_ID,
					TEST_ACTIVITY_TYPE,
					TEST_ACTIVITY_DAY,
					TEST_ACTIVITY_TIME_H, TEST_ACTIVITY_TIME_M,
					TEST_ACTIVITY_DURATION_M,
					TEST_ACTIVITY_COURSE
			});
			allActivities.commitChanges();
		}
		catch (DatabaseException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void teardownClass() {
		
		// remove course
		try {
			Table allCourses = Database.getDatabase().getFullTable("courses");
			boolean check = allCourses.deleteRow("courseid", TEST_COURSE_ID);
			if (!check) {
				DatabaseException de = new DatabaseException();
				de.setMessage("Course " + TEST_COURSE_ID + " did not delete!");
				throw de;
			}
			allCourses.commitChanges();
		}
		catch (DatabaseException | IOException e) {
			e.printStackTrace();
		}
		
		// remove activity
		try {
			Table allActivities
				= Database.getDatabase().getFullTable("activities");
			boolean check
				= allActivities.deleteRow("activityid", TEST_ACTIVITY_ID);
			if (!check) {
				DatabaseException de = new DatabaseException();
				de.setMessage("Activity " + TEST_ACTIVITY_ID +
						" did not delete!");
				throw de;
			}
			allActivities.commitChanges();
		}
		catch (DatabaseException | IOException e) {
			e.printStackTrace();
		}
		
		// remove assignment if needed.
		try {
			Table allActivities
				= Database.getDatabase().getFullTable("assignments");
			Row check = allActivities.getRowEquals(
					new String[] {
							"userid", "activityid"
					}, 
					new String[] {
							TEST_CASUAL_ID, TEST_ACTIVITY_ID
					});
			if (check != null) {
				boolean success = allActivities.deleteRow(
						new String[] {
								"userid", "activityid"
						},
						new String[] {
								TEST_CASUAL_ID, TEST_ACTIVITY_ID
						});
				if (!success) {
					DatabaseException de = new DatabaseException();
					de.setMessage("Assignment (" + TEST_CASUAL_ID + ", " +
							TEST_ACTIVITY_ID + ") did not delete!");
					throw de;
				}
				allActivities.commitChanges();
			}
			else {
				System.out.println("\nDid not delete assignment (" +
						TEST_CASUAL_ID + ", " + TEST_ACTIVITY_ID +
						") because it does not exist!");
			}
		}
		catch (DatabaseException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Before
	public void setup() {
		// set the controller to null to reset everything.
		Session.sessionInst.setController(null);
	}

	/**
	 * Tests if a course will return a View correctly. Also tests to make sure
	 * that a fake course will not display.
	 */
	@Test
	public void testViewCourse() {
		System.out.println("\n=== Test: View a Course ===");
		System.out.println("Details: Sets a course using an ID, and returns " +
				"a View to the client without raising any errors.");
		
		// fake course
		try {
			String courseId = "fake1234";
			CourseController toSet = new CourseController(courseId);
			setController(toSet);
			ViewSerial views = Session.sessionInst.getViewSerial();
			assertFalse("A ViewSerial was not returned for fake course " +
					courseId + ", which is outside of expectations.",
					views == null);
			assertTrue("A View was returned for fake course " + courseId +
					" when one should not have been.",
					views.content == null);
			System.out.println(
					"Fake course " + courseId +
					" did not return, as expected.");
		}
		catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
		
		// real course
		try {
			String courseId = "acha8888";
			CourseController toSet = new CourseController(courseId);
			setController(toSet);
			ViewSerial views = Session.sessionInst.getViewSerial();
			assertTrue("Did not find the course " + courseId + ".",
					views != null &&
					views.content != null &&
					views.content instanceof CourseView);
			System.out.println(
					"Real course " + courseId +
					" returned, as expected.");
		}
		catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
		
	}
	
	/**
	 * Tests if an activity will return a View correctly. Also tests to make
	 * sure that a fake course will not display.
	 */
	@Test
	public void testViewActivity() {
		System.out.println("\n=== Test: View an Activity ===");
		System.out.println("Details: Sets an activity using an ID, and " +
				"returns a View to the client without raising any errors.");
		
		// fake activity
		try {
			String activityId = "notanid";
			ActivityController toSet = new ActivityController(activityId);
			setController(toSet);
			ViewSerial views = Session.sessionInst.getViewSerial();
			assertFalse("A ViewSerial was not returned for fake activity " +
					activityId + ", which is outside of expectations.",
					views == null);
			assertTrue("A View was returned for fake activity " + activityId +
					" when one should not have been.",
					views.content == null);
			System.out.println(
					"Fake activity " + activityId +
					" did not return, as expected.");
		}
		catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
		
		// real activity
		try {
			String activityId = "102";
			ActivityController toSet = new ActivityController(activityId);
			setController(toSet);
			ViewSerial views = Session.sessionInst.getViewSerial();
			assertTrue("Did not find the activity " + activityId + ".",
					views != null &&
					views.content != null &&
					views.content instanceof ActivityView);
			System.out.println(
					"Real activity " + activityId +
					" returned, as expected.");
		}
		catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
	}
	
	/**
	 * Tests to see that an approver can successfully approve a course.
	 */
	@Test
	public void testApproveCourse() {
		System.out.println("\n=== Test: Approve a Course ===");
		System.out.println("Details: Tests to see if an approver can " +
				"successfully approve a course. If any errors occur, the " +
				"test fails.");
		
		// set up approval in DB
		try {
			// find course information
			Table fullTable = Database.getDatabase().getFullTable("courses");
			Row row = fullTable.getRowEquals("courseid", TEST_COURSE_ID);
			
			// change data
			row.setColumn("status", "accepted");
			
			// update database
			fullTable.commitChanges();
			
		} catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
		
		// check that course has changed
		try {
			// find course information
			Table fullTable = Database.getDatabase().getFullTable("courses");
			Row row = fullTable.getRowEquals("courseid", TEST_COURSE_ID);
			
			// check change
			String status = row.get("status");
			assertEquals("Course status did not update.", status, "accepted");
			System.out.println("Course status has been successfully updated " +
					"to " + status + ".");
			
		} catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
	}
	
	/**
	 * Tests to see that an approver can successfully approve a course.
	 */
	@Test
	public void testAddCasualToActivity() {
		System.out.println("\n=== Test: Adding a Casual to an Activity ===");
		System.out.println("Details: Tests to see if a casual can be added " +
				"to an activity successfully.");
		
		// set up assignment in DB
		try {
			
			// create assignment
			Table fullTable
				= Database.getDatabase().getFullTable("assignments");
			fullTable.createNewRow(
					new String[] {
							TEST_CASUAL_ID, TEST_ACTIVITY_ID
					});
			
			// update database
			fullTable.commitChanges();
			
		} catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
		
		// check that assignment has changed
		try {
			// find assignment information
			Table fullTable
				= Database.getDatabase().getFullTable("assignments");
			Row row = fullTable.getRowEquals(
					new String[] {
							"userid", "activityid"
					},
					new String[] {
							TEST_CASUAL_ID, TEST_ACTIVITY_ID
					});
			
			// check assignment
			assertNotEquals("Assignment was not added to database.", row, null);
			System.out.println("Assignment (" + TEST_CASUAL_ID + ", " +
					TEST_ACTIVITY_ID + ") was successfully added.");
			
		} catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
	}
	
	private void setController(Controller toSet) {
		Session.sessionInst.setController(toSet);
		Session.sessionInst.run();
	}

}
