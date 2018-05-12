package isys1118.group1.shared;

import java.util.ArrayList;

public class ValidateActivityInput {
	
	public static String[] validateInputsClient(
			String type,
			String day,
			String timeh,
			String timem,
			String durm,
			String casual) {
		
		ArrayList<String> errors = new ArrayList<String>();
		
		// check type
		if (!checkType(type)) {
			errors.add("type");
		}
		
		// check day
		if (!checkDay(day)) {
			errors.add("day");
		}
		
		// check timeh
		if (!checkTimeH(timeh)) {
			errors.add("timeh");
		}
		
		// check timem
		if (!checkTimeM(timem)) {
			errors.add("timem");
		}
		
		// check durm
		if (!checkDuration(durm)) {
			errors.add("durm");
		}
		
		// check casual
		if (!checkCasualInput(casual)) {
			errors.add("casual.no_input");
		}
		
		// if empty, return null to confirm there are no errors. Otherwise
		// return all string with errors
		return errors.size() == 0 ? null : errors.toArray(new String[errors.size()]);
	}
	
	/**
	 * <P>Returns true only if the type is acceptable. Type can only be:</p>
	 * <ul>
	 *   <li>lecture</li>
	 *   <li>tutorial</li>
	 *   <li>practical</li>
	 *   <li>lab</li>
	 * </ul>
	 * @param type
	 * @return
	 */
	public static boolean checkType(String type) {
		if ((type == null || type.isEmpty()) || !(
				type.toLowerCase().equals("lecture") ||
				type.toLowerCase().equals("tutorial") ||
				type.toLowerCase().equals("practical") ||
				type.toLowerCase().equals("lab"))) {
			return false;
		}
		return true;
	}
	/**
	 * <P>Returns true only if the day is acceptable. Day can only be:</p>
	 * <ul>
	 *   <li>Monday</li>
	 *   <li>Tuesday</li>
	 *   <li>Wednesday</li>
	 *   <li>Thursday</li>
	 *   <li>Friday</li>
	 *   <li>Saturday</li>
	 *   <li>Sunday</li>
	 * </ul>
	 * @param type
	 * @return
	 */
	public static boolean checkDay(String day) {
		if ((day == null || day.isEmpty()) || !(
				day.toLowerCase().equals("monday") ||
				day.toLowerCase().equals("tuesday") ||
				day.toLowerCase().equals("wednesday") ||
				day.toLowerCase().equals("thursday") ||
				day.toLowerCase().equals("friday") ||
				day.toLowerCase().equals("saturday") ||
				day.toLowerCase().equals("sunday"))) {
			return false;
		}
		return true;
	}
	
	/**
	 * Hour must be between 7 (7am) and 21 (9pm).
	 * @param timeh
	 * @return
	 */
	public static boolean checkTimeH(String timeh) {
		if (timeh == null || timeh.isEmpty()) {
			return false;
		}
		int timehNumber;
		try {
			timehNumber = Integer.valueOf(timeh);
		}
		catch (NumberFormatException e) {
			return false;
		}
		if (timehNumber < 7 || timehNumber > 21) {
			return false;
		}
		return true;
	}
	
	/**
	 * Minutes must be an actual minute (0 - 59)
	 * @param timem
	 * @return
	 */
	public static boolean checkTimeM(String timem) {
		if (timem == null || timem.isEmpty()) {
			return false;
		}
		int timemNumber;
		try {
			timemNumber = Integer.valueOf(timem);
		}
		catch (NumberFormatException e) {
			return false;
		}
		if (timemNumber < 0 || timemNumber > 59) {
			return false;
		}
		return true;
	}
	
	/**
	 * Duration must be between 30 minutes and 4 hours.
	 * @param durm
	 * @return
	 */
	public static boolean checkDuration(String durm) {
		if (durm == null || durm.isEmpty()) {
			return false;
		}
		int durmNumber;
		try {
			durmNumber = Integer.valueOf(durm);
		}
		catch (NumberFormatException e) {
			return false;
		}
		if (durmNumber < 30 || durmNumber > 240) {
			return false;
		}
		return true;
	}
	
	/**
	 * Partial check to see if casual has been correctly entered.
	 * @param casual
	 * @return
	 */
	public static boolean checkCasualInput(String casual) {
		if (casual == null || casual.isEmpty() ||
				casual.equalsIgnoreCase("null") ||
				casual.equalsIgnoreCase("none")) {
			return false;
		}
		return true;
	}
	
}
