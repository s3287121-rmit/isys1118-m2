package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.shared.model.ActivityEditModel;
import isys1118.group1.shared.model.ActivityModel;
import isys1118.group1.shared.model.Model;

public class ActivityEditView extends View {
	
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
		ActivityEditModel cm = (ActivityEditModel) model;
		
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
		// courseLink.addClickHandler(new ControllerLink("course", courseId));
		vp.add(courseLink);
		
		// form
		FormPanel form = new FormPanel();
		
		TextBox typeBox = new TextBox();
		typeBox.setText("Type");
		typeBox.setValue(this.type);
		form.add(typeBox);
		
		TextBox dayBox = new TextBox();
		dayBox.setText("Day");
		dayBox.setValue(this.day);
		form.add(dayBox);
		
		TextBox timeBox = new TextBox();
		timeBox.setText("Time");
		timeBox.setValue(this.startTime);
		form.add(timeBox);
		
		TextBox durBox = new TextBox();
		durBox.setText("Duration");
		durBox.setValue(this.duration);
		form.add(durBox);
		
		TextBox casualBox = new TextBox();
		casualBox.setText("Casual");
		casualBox.setValue(this.casualId + " " + this.casualName);
		form.add(casualBox);
		// TODO casualPopup handler
		
		// submit and cancel buttons
		Button cancelButton = new Button("Cancel");
		SubmitButton submitButton = new SubmitButton("Submit");
		form.add(cancelButton);
		form.add(submitButton);
		
		vp.add(form);
		
	}

}
