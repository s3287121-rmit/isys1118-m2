package isys1118.group1.client.helpers;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CasualModal extends DialogBox {
	
	private DialogBox box;
	
	// preset info for activity
	private String courseId;
	private String activityId;

	// currently set casual
	private String casualId;
	
	public CasualModal(String casualId, String courseId, String activityId) {
		this.casualId = casualId;
		this.courseId = courseId;
		this.activityId = activityId;
		this.box = new DialogBox(true, true);
		createInnerHtml();
		hide();
	}
	
	/**
	 * Build after constructing.
	 */
	private void createInnerHtml() {
		this.box.getElement().setId("casual-modal");
		
		VerticalPanel vp = new VerticalPanel();
		
		Button exit = new Button("X");
		exit.getElement().setId("casual-modal-close");
		vp.add(exit);
		
		HTML title = new HTML();
		title.getElement().setId("casual-modal-title");
		vp.add(title);
		
		VerticalPanel content = new VerticalPanel();
		content.getElement().setId("casual-modal-content");
		vp.add(content);
		
		box.add(vp);
	}
	
	public void showUserSingle(String userId, String userName, String userRate, String userConflict) {
		clearContent();
		RootPanel content = RootPanel.get("casual-modal-content");
		
		// Button selectDifferent = 
	}
	
	private void clearContent() {
		RootPanel content = RootPanel.get("casual-modal-content");
		content.clear();
	}
	
}
