package isys1118.group1.shared.model;

import java.util.Random;

public class ActivityModel extends Model {
	
	public final String name;
	
	public ActivityModel() {
		Random r = new Random();
		char[] title = new char[10];
		for (int i = 0; i < title.length; i++) {
			title[i] = (char) (r.nextInt(25) + 65);
		}
		name = new String(title);
	}

}
