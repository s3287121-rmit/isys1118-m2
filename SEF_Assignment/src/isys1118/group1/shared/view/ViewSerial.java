package isys1118.group1.shared.view;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ViewSerial implements Serializable {
	
	public View menu;
	public View content;
	
	public static ViewSerial create(View menu, View content) {
		ViewSerial vs = new ViewSerial();
		vs.content = content;
		vs.menu = menu;
		return vs;
	}
	
}
