package isys1118.group1.server.model;

import java.util.Random;

public class MenuModel extends Model {
	
	public final String name;
	
	public MenuModel() {
		Random r = new Random();
		char[] title = new char[10];
		for (int i = 0; i < title.length; i++) {
			title[i] = (char) (r.nextInt(25) + 65);
		}
		name = new String(title);
	}

}
