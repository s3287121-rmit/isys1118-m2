package isys1118.group1.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import isys1118.group1.client.UpdateCasualService;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.helpers.ValidateCasualInput;
import isys1118.group1.shared.CasualInfo;
import isys1118.group1.shared.ValidateActivityInput;

@SuppressWarnings("serial")
public class UpdateCasualServiceImpl extends RemoteServiceServlet implements UpdateCasualService {

	@Override
	public CasualInfo getCasualInfo(String casualId, String courseId, String activityId) {
		
		if (casualId == null || casualId.isEmpty()) {
			return CasualInfo.create(null, null, null, null, null, true);
		}
		
		try {
			Table casuals = Database.getDatabase().getFullTable("users");
			Table casualDetails = Database.getDatabase().getFullTable("casualdetails");
			Table coursesTable = Database.getDatabase().getFullTable("courses");
			String id;
			String name;
			String rate;
			String[] courses;
			
			// get casual info
			Row thisCasual = casuals.getRowEquals("userid", casualId);
			if (thisCasual == null) {
				return null;
			}
			id = thisCasual.get("userid");
			name = thisCasual.get("name");
			rate = thisCasual.get("rated") + "." + thisCasual.get("ratec");
			
			// get casual courses
			ArrayList<Row> thisDetails = casualDetails.getRowsEqual("userid", casualId);
			if (thisDetails.isEmpty()) {
				return null;
			}
			courses = new String[thisDetails.size()];
			for (int i = 0; i < thisDetails.size(); i++) {
				String cid = thisDetails.get(i).get("course");
				Row courseInfo = coursesTable.getRowEquals("courseid", cid);
				courses[i] = cid + " " + courseInfo.get("coursename");
			}
			
			// return courses
			return CasualInfo.create(id, name, rate, courses, null, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public CasualInfo[] getAllAvailableCasuals(String currentCasId, String courseId, String activityId,
			String day, String starth, String startm, String durationm) {
		
		// do the conflict pre-check. This determines if we actually have
		// enough data to check for conflicts at all.
		boolean conflictsPossible = preCheckConflict(
				currentCasId, day, starth, startm, durationm);
		
		try {
			Table users = Database.getDatabase().getFullTable("users");
			Table details = Database.getDatabase().getFullTable("casualdetails");
			
			ArrayList<Row> detailRows = details.getRowsEqual("course", courseId);
			// if no users are assigned to the course, return an empty array.
			if (detailRows.size() == 0) {
				return new CasualInfo[0];
			}
			// otherwise, go through each one and get their info.
			else {
				CasualInfo[] canBeAssigned = new CasualInfo[detailRows.size()];
				for (int i = 0; i < detailRows.size(); i++) {
					Row curDetailRow = detailRows.get(i);
					Row curUser = users.getRowEquals(
							"userid",
							curDetailRow.get("userid"));
					
					// check for conficts if necessary
					String conflict = null;
					if (conflictsPossible) {
						conflict = checkConflict(currentCasId, day, starth, startm, durationm, activityId);
					}
					
					// get CasualInfo
					canBeAssigned[i] = CasualInfo.create(
							curUser.get("userid"),
							curUser.get("name"),
							curUser.get("rated") + "." + curUser.get("ratec"),
							null,
							conflict,
							false);
				}
				
				// finished getting users. Return them.
				return canBeAssigned;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String checkConflict(String casualId, String day, String starth,
			String startm, String durationm, String activityId) {
		if (!preCheckConflict(casualId, day, starth, startm, durationm)) {
			return null;
		}
		
		// convert ints to ints
		int startHInt = Integer.parseInt(starth);
		int startMInt = Integer.parseInt(startm);
		int durationMInt = Integer.parseInt(durationm);
		
		// now test for conflict
		return ValidateCasualInput.checkCasualConflict(
				casualId, activityId, day, startHInt, startMInt, durationMInt);
	}
	
	private boolean preCheckConflict(String casualId, String day, String starth,
			String startm, String durationm) {
		if (
				ValidateActivityInput.checkCasualInput(casualId) &&
				ValidateActivityInput.checkDay(day) &&
				ValidateActivityInput.checkTimeH(starth) &&
				ValidateActivityInput.checkTimeM(startm) &&
				ValidateActivityInput.checkDuration(durationm)) {
			return true;
		}
		return false;
	}
	
}
