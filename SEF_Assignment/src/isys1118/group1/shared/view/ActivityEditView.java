package isys1118.group1.shared.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
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
		form.setEncoding(FormPanel.ENCODING_URLENCODED);
		form.setMethod(FormPanel.METHOD_POST);
		form.setAction("/activitysubmit");
		
		// Add an event handler to the form.
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
	    	@Override
	    	public void onSubmit(SubmitEvent event) {
	    		// TODO validate
	    	}
		});

		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// When the form submission is successfully completed,
				// this event is fired. Assuming the service returned 
				// a response of type text/html, we can get the result
				// here.
				Window.alert(event.getResults());
			}
		});
		
		vp.add(form);
		VerticalPanel vpForm = new VerticalPanel();
		form.add(vpForm);
		
		TextBox typeBox = new TextBox();
		typeBox.setText("Type");
		typeBox.setValue(this.type);
		vpForm.add(typeBox);
		
		TextBox dayBox = new TextBox();
		dayBox.setText("Day");
		dayBox.setValue(this.day);
		vpForm.add(dayBox);
		
		HorizontalPanel hpTime = new HorizontalPanel();
		HTML label = new HTML("<P>Start Time:</p>");
		hpTime.add(label);
		TextBox timeBoxH = new TextBox();
		timeBoxH.setText("TimeH");
		timeBoxH.setValue(String.valueOf(this.startTimeH));
		hpTime.add(timeBoxH);
		HTML colon = new HTML("<P>:</p>");
		hpTime.add(colon);
		TextBox timeBoxM = new TextBox();
		timeBoxM.setText("TimeM");
		timeBoxM.setValue(String.valueOf(this.startTimeM));
		hpTime.add(timeBoxM);
		vpForm.add(hpTime);
		
		TextBox durBox = new TextBox();
		durBox.setText("Duration");
		durBox.setValue(String.valueOf(this.durationM));
		vpForm.add(durBox);
		
		TextBox casualBox = new TextBox();
		casualBox.setText("Casual");
		casualBox.setValue(this.casualId + " " + this.casualName);
		casualBox.setEnabled(false);
		vpForm.add(casualBox);
		// TODO casualPopup handler
		
		// submit and cancel buttons
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.addStyleName("right-align");
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ControllerLink("activity", activityId));
		SubmitButton submitButton = new SubmitButton("Submit");
		submitButton.addClickHandler(new SubmitActivityHandler(form));
		buttonPanel.add(cancelButton);
		buttonPanel.add(submitButton);
		vpForm.add(buttonPanel);
		
	}

}
