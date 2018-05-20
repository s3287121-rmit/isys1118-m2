package isys1118.group1.server.helpers;

import java.util.ArrayList;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;

public class CasualPriceCalculator {

	public static final int TIMES_WORKED_PER_ACTIVITY = 12;
	public static final double LOADING = 1.3D;
	
	public static Cost costOfActivity(String userId, String activityId) {
		try {
			// get user rate
			Table allUsers = Database.getDatabase().getFullTable("users");
			Row user = allUsers.getRowEquals("userid", userId);
			int dollars = Integer.valueOf(user.get("rated"));
			int cents = Integer.valueOf(user.get("ratec"));

			// get duration of activity
			Table allActivities = Database.getDatabase().getFullTable("activities");
			Row theActivity = allActivities.getRowEquals("activityid", activityId);
			int durationM = Integer.valueOf(theActivity.get("durationm"));
			int totalDurationM = durationM * TIMES_WORKED_PER_ACTIVITY;
			
			// calculate price
			double priceD = dollars + ((double) cents) / 100D;
			priceD *= totalDurationM / 60D;
			priceD *= LOADING;
			int priceDollars = (int) priceD;
			int priceCents = (int) ((priceD - priceDollars) * 100D);
			
			// return price
			Cost price = new Cost(priceDollars, priceCents);
			return price;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Cost costOfAllActivities(String courseId) {
		try {
			Table allActivities = Database.getDatabase().getFullTable("activities");
			Table allAssignments = Database.getDatabase().getFullTable("assignments");
			Table allUsers = Database.getDatabase().getFullTable("users");
			double totalCost = 0D;
			
			// get all activities which are assigned to this course
			ArrayList<Row> courseActivities = allActivities.getRowsEqual("courseid", courseId);
			for (Row act : courseActivities) {
				
				// find the casual
				Row assign = allAssignments.getRowEquals("activityid", act.get("activityid"));
				if (assign != null) {
					Row casual = allUsers.getRowEquals("userid", assign.get("userid"));
					if (casual != null) {
						
						// get casual's rate and calculate their price
						int dollars = Integer.valueOf(casual.get("rated"));
						int cents = Integer.valueOf(casual.get("ratec"));
						int durationM = Integer.valueOf(act.get("durationm"));
						int totalDurationM = durationM * TIMES_WORKED_PER_ACTIVITY;
						double priceD = dollars + ((double) cents) / 100D;
						priceD *= totalDurationM / 60D;
						totalCost += priceD;
						
					}
				}
			}
			
			// finalise cost and return
			totalCost *= LOADING;
			int priceDollars = (int) totalCost;
			int priceCents = (int) Math.round(((totalCost - priceDollars) * 100D));
			// handle rounding errors
			if (priceCents >= 100) {
				priceDollars += priceCents / 100;
				priceCents = priceCents % 100;
			}
			
			// return price
			Cost price = new Cost(priceDollars, priceCents);
			return price;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
