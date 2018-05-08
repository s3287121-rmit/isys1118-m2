package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.shared.model.ActivityModel;
import isys1118.group1.shared.model.Model;

public class ActivityView extends View {
	
	private String title;
	private String courseId;
	private String courseName;
	private String type;
	private String day;
	private String startTime;
	private String duration;
	private String casualId;
	private String casualName;

	@Override
	public void setView(Model model) {
		ActivityModel cm = (ActivityModel) model;
		
		// title
		title = "Test Course";
		
		// details
		courseId = "aaaa2222";
		courseName = "Applied Something";
		type = "Lecture";
		day = "Monday";
		startTime = "12:30";
		duration = "2 hours";
		casualId = "e00000";
		casualName = "Mr. McGee";
		
	}

	@Override
	public void show() {
		final RootPanel title = RootPanel.get("title");
		final RootPanel content = RootPanel.get("content");
		
		// clear screen
		clearMain();
		
		// set title
		title.add(new Label(this.title));
		
		VerticalPanel vp = new VerticalPanel();
		content.add(vp);
		
		// details
		HTML courseLink = new HTML("<p>Course: " + courseId + " " + courseName + "</p>");
		courseLink.addClickHandler(new ControllerLink("course", courseId));
		vp.add(courseLink);
		vp.add(new HTML("<p>Type: " + type + "</p>"));
		vp.add(new HTML("<p>Time: " + day + " " + startTime + " (" + duration + " )" + "</p>"));
		HTML casualLink = new HTML("<p>Assigned Casual: " + casualId + " " + casualName + "</p>");
		vp.add(casualLink);
		
	}

}