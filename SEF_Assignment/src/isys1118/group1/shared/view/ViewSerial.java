package isys1118.group1.shared.view;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ViewSerial implements Serializable {
	
	public View menu;
	public View content;
	public boolean error;
	public String errorMessage;
	
	public static ViewSerial create(View menu, View content) {
		ViewSerial vs = new ViewSerial();
		vs.content = content;
		vs.menu = menu;
		vs.error = false;
		vs.errorMessage = null;
		return vs;
	}
	
	public static ViewSerial createError(String errorMessage) {
		ViewSerial vs = new ViewSerial();
		vs.content = null;
		vs.menu = null;
		vs.error = true;
		vs.errorMessage = errorMessage;
		return vs;
	}
	
}
