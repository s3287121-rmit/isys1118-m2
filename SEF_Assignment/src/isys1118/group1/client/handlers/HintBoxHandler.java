package isys1118.group1.client.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;

public class HintBoxHandler implements ClickHandler {
	
	private DialogBox hintBox;
	
	public HintBoxHandler(DialogBox db) {
		hintBox = db;
	}

	@Override
	public void onClick(ClickEvent event) {
		if (hintBox.isShowing()) {
			hintBox.hide();
		}
		else {
			hintBox.show();
		}
	}

}
