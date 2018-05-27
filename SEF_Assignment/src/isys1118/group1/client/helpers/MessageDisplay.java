package isys1118.group1.client.helpers;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class MessageDisplay {
	
	public static void displayMessage(String message) {
		RootPanel messageBox = RootPanel.get("message");
		messageBox.clear();
		messageBox.add(new HTML(message));
		messageBox.getElement().getStyle()
			.setVisibility(Visibility.VISIBLE);
		Timer timer = new Timer() {
			@Override
			public void run() {
				RootPanel messageBox = RootPanel.get("message");
				messageBox.getElement().getStyle()
					.setVisibility(Visibility.HIDDEN);
				messageBox.clear();
			}
		};
		timer.schedule(10000);
	}
	
}
