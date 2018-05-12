package isys1118.group1.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EditActivityInputs implements IsSerializable {
	
	public boolean client;
	public boolean success = false;
	
	public String type;
	public String day;
	public String timeh;
	public String timem;
	public String durm;
	public String casual;
	public String courseid;
	
	public String[] errors;
	
	public static EditActivityInputs createClient(
			String type,
			String day,
			String timeh,
			String timem,
			String durm,
			String casual,
			String courseid) {
		
		EditActivityInputs eai = new EditActivityInputs();
		eai.type = type;
		eai.day = day;
		eai.timeh = timeh;
		eai.timem = timem;
		eai.durm = durm;
		eai.casual = casual;
		eai.courseid = courseid;
		eai.client = true;
		return eai;
	}
	
	public static EditActivityInputs createServer(String[] errors) {
		EditActivityInputs eai = new EditActivityInputs();
		eai.errors = errors;
		eai.client = false;
		if (errors == null || errors.length == 0) {
			eai.success = true;
		}
		else {
			for (String e : errors) {
				if (!e.contains("casual")) {
					eai.success = false;
					break;
				}
				else if (e.contains("no_input")) {
					eai.success = true;
				}
				else {
					eai.success = false;
					break;
				}
			}
		}
		return eai;
	}

}
