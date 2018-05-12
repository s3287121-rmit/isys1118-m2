package isys1118.group1.server;

import java.util.ArrayList;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.shared.ValidateActivityInput;

public class ValidateCasualInput {

	public static String[] validateInputsServer(
			String type,
			String day,
			String timeh,
			String timem,
			String durm,
			String casual,
			String activityId,
			String courseid) {
		
		ArrayList<String> errors = new ArrayList<String>();
		
		// check type
			if (!ValidateActivityInput.checkType(type)) {
				errors.add("type");
			}
			
			// check day
			if (!ValidateActivityInput.checkDay(day)) {
				errors.add("day");
			}
			
			// check timeh
			if (!ValidateActivityInput.checkTimeH(timeh)) {
				errors.add("timeh");
			}
			
			// check timem
			if (!ValidateActivityInput.checkTimeM(timem)) {
				errors.add("timem");
			}
			
			// check durm
			if (!ValidateActivityInput.checkDuration(durm)) {
				errors.add("durm");
			}
			
			// check casual
			int timeHInt = Integer.valueOf(timeh);
			int timeMInt = Integer.valueOf(timem);
			int durMInt = Integer.valueOf(durm);
			String casualError = checkCasualConflict(casual, activityId, timeHInt, timeMInt, durMInt);
			if (casualError != null && !casualError.isEmpty()) {
				errors.add(casualError);
			}
		
		return errors.size() == 0 ? null : errors.toArray(new String[errors.size()]);
	}
	
	/**
	 * Full check to see that the given casual exists. If it does,
	 * return true. This can only be used on the server side.
	 * @param casual
	 * @return
	 */
	public static boolean checkCasualInDatabase(String casual) {
		if (!ValidateActivityInput.checkCasualInput(casual)) {
			return false;
		}
		try {
			String[] splits = casual.split(" ");
			Table allUsers = Database.getDatabase().getFullTable("users");
			Row checkRow = allUsers.getRowEquals("userid", splits[0]);
			if (checkRow != null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String checkCasualConflict(
			String casual, String activityId,
			int starth, int startm, int durationm) {
		
		// check if data has been entered. If there is no casual data, we don't
		// need to validate.
		if (!ValidateActivityInput.checkCasualInput(casual)) {
			return "casual.no_input";
		}
		
		// check if user is in database
		if (!checkCasualInDatabase(casual)) {
			return "casual.does_not_exist";
		}
		
		// begin checking for clashes
		try {
			String[] splits = casual.split(" ");	// split to get userid
			Table allAssignments = Database.getDatabase().getFullTable("assignments");
			Table allActivities = Database.getDatabase().getFullTable("activities");
			Row thisAct = allActivities.getRowEquals("activityid", activityId);
			
			// go through every other activity that the user has been assigned
			ArrayList<Row> allAssignRows = allAssignments.getRowsEqual("userid", splits[0]);
			for (Row assignRow : allAssignRows) {
				
				// do not compare to the current activity. This is pointless.
				// Note that this will occur when resubmitting an activity with
				// an existing registered user.
				if (thisAct.get("activityid").equals(assignRow.get("activityid"))) {
					continue;
				}
				
				Row actRow = allActivities.getRowEquals("activityid", assignRow.get("activityid"));
				
				// check to see if there is an overlap with any of the user's
				// other activities
				if (actRow != null) {
					int actTimeH = Integer.valueOf(actRow.get("timeh"));
					int actTimeM = Integer.valueOf(actRow.get("timem"));
					int actDurationM = Integer.valueOf(actRow.get("durationm"));
					if (timeRangeInTimeRange(
							actTimeH, actTimeM, actDurationM,
							starth, startm, durationm)) {
						return "casual.clash=" + actRow.get("activityid");
					}
				}
			}
		} catch (Exception | Error e) {
			e.printStackTrace();
			return "casual.exception_raised";
		}
		
		// return empty string, denoting no errors were encountered.
		return "";
	}
	
	private static boolean pointInTimeRange(
			int startHour, int startMinute, int duration,
			int pointHour, int pointMinute) {
		int increaseHours = duration / 60;
		int increaseMinutes = duration % 60;
		int endHour = startHour + increaseHours;
		int endMinute = startMinute + increaseMinutes;
		if (
				(pointHour >= startHour && pointMinute >= startMinute) &&
				(pointHour <= endHour && pointMinute <= endMinute)) {
			return true;
		}
		return false;
	}
	
	private static boolean timeRangeInTimeRange(
			int t0StartHour, int t0StartMinute, int t0Duration,
			int t1StartHour, int t1StartMinute, int t1Duration) {
		
		// check 1: t1 start in t0
		// [---t0---[--]---t1---]
		// [---t0---[---t1---]--]
		if (pointInTimeRange(
				t0StartHour, t0StartMinute, t0Duration,
				t1StartHour, t1StartMinute)) {
			return true;
		}
		
		// check 2: t0 start in t1
		// [---t1---[--]---t0---]
		// [---t1---[---t0---]--]
		if (pointInTimeRange(
				t1StartHour, t1StartMinute, t1Duration,
				t0StartHour, t0StartMinute)) {
			return true;
		}
		
		return false;
	}
}
