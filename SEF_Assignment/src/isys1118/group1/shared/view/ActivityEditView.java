package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.client.handlers.SubmitActivityHandler;
import isys1118.group1.shared.model.Model;

public class ActivityEditView extends View {
	
	private String title;
	private String activityId;
	private String courseId;
	private String courseName;
	private String type;
	private String day;
	private int startTimeH;
	private int startTimeM;
	private int durationM;
	private String casualId;
	private String casualName;

	@Override
	public void setView(Model model) {}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setStartTimeH(int startTimeH) {
		this.startTimeH = startTimeH;
	}

	public void setStartTimeM(int startTimeM) {
		this.startTimeM = startTimeM;
	}

	public void setDuration(int durationM) {
		this.durationM = durationM;
	}

	public void setCasualId(String casualId) {
		this.casualId = casualId;
	}

	public void setCasualName(String casualName) {
		this.casualName = casualName;
	}

	@Override
	public void show() {
		final RootPanel title = RootPanel.get("title");
		final RootPanel content = RootPanel.get("content");
		
		// set up all strings -- this accounts for null and unset values
		String courseStr = this.courseId + " " + this.courseName;
		String typeStr = this.type == null ? "" : this.type;
		String dayStr = this.day == null ? "" : this.day;
		String startTimeHStr = this.startTimeH == -1 ? "" : String.valueOf(this.startTimeH);
		String startTimeMStr = this.startTimeM == -1 ? "" : String.valueOf(this.startTimeM);
		String durationMStr = this.durationM == -1 ? "" : String.valueOf(this.durationM);
		String casualStr = this.casualId == null ? "" : this.casualId + " " + this.casualName;
		
		// clear screen
		clearMain();
		
		// set title
		title.add(new Label(this.title));
		
		VerticalPanel vp = new VerticalPanel();
		content.add(vp);
		
		// details
		HTML courseLink = new HTML("<p>Course: " + courseStr + "</p>");
		// courseLink.addClickHandler(new ControllerLink("course", courseId));
		vp.add(courseLink);
		
		// Type
		HorizontalPanel typeHP = new HorizontalPanel();
		HTML typeLabel = new HTML("<P>Activity Type: </p>");
		typeHP.add(typeLabel);
		TextBox typeBox = new TextBox();
		typeBox.getElement().setId("edit-type");
		typeBox.setValue(typeStr);
		typeHP.add(typeBox);
		HTML typeError = new HTML();
		typeError.getElement().setId("edit-type-error");
		typeError.addStyleName("form-error");
		typeHP.add(typeError);
		vp.add(typeHP);

		// Day
		HorizontalPanel dayHP = new HorizontalPanel();
		HTML dayLabel = new HTML("<P>Day: </p>");
		dayHP.add(dayLabel);
		TextBox dayBox = new TextBox();
		dayBox.getElement().setId("edit-day");
		dayBox.setValue(dayStr);
		dayHP.add(dayBox);
		HTML dayError = new HTML();
		dayError.getElement().setId("edit-day-error");
		dayError.addStyleName("form-error");
		dayHP.add(dayError);
		vp.add(dayHP);
		
		// Start Time
		HorizontalPanel timeHP = new HorizontalPanel();
		HTML labelTime = new HTML("<P>Start Time: </p>");
		timeHP.add(labelTime);
		TextBox timeBoxH = new TextBox();
		timeBoxH.getElement().setId("edit-timestarth");
		timeBoxH.setValue(startTimeHStr);
		timeHP.add(timeBoxH);
		HTML colon = new HTML("<P> : </p>");
		timeHP.add(colon);
		TextBox timeBoxM = new TextBox();
		timeBoxM.getElement().setId("edit-timestartm");
		timeBoxM.setValue(startTimeMStr);
		timeHP.add(timeBoxM);
		HTML timeError = new HTML();
		timeError.getElement().setId("edit-timestart-error");
		timeError.addStyleName("form-error");
		timeHP.add(timeError);
		vp.add(timeHP);
		
		// Duration
		HorizontalPanel durationHP = new HorizontalPanel();
		HTML durationLabel = new HTML("<P>Duration: </p>");
		durationHP.add(durationLabel);
		TextBox durBox = new TextBox();
		durBox.getElement().setId("edit-durationm");
		durBox.setValue(durationMStr);
		durationHP.add(durBox);
		HTML durationError = new HTML();
		durationError.getElement().setId("edit-durationm-error");
		durationError.addStyleName("form-error");
		durationHP.add(durationError);
		vp.add(durationHP);

		// Casual
		HorizontalPanel casualHP = new HorizontalPanel();
		HTML casualLabel = new HTML("<P>Casual: </p>");
		casualHP.add(casualLabel);
		TextBox casualBox = new TextBox();
		casualBox.getElement().setId("edit-casual");
		casualBox.setValue(casualStr);
		casualBox.setEnabled(false);
		casualHP.add(casualBox);
		HTML casualError = new HTML();
		casualError.getElement().setId("edit-casual-error");
		casualError.addStyleName("form-error");
		casualHP.add(casualError);
		vp.add(casualHP);
		
		// TODO casualPopup handler
		
		// submit and cancel buttons
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.addStyleName("right-align");
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ControllerLink("activity", activityId));
		SubmitButton submitButton = new SubmitButton("Submit");
		submitButton.addClickHandler(new SubmitActivityHandler(activityId, courseId));
		buttonPanel.add(cancelButton);
		buttonPanel.add(submitButton);
		vp.add(buttonPanel);
		
	}

}
