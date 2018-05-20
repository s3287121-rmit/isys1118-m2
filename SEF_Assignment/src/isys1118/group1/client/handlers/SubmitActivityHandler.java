package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.client.SubmitActivityService;
import isys1118.group1.client.SubmitActivityServiceAsync;
import isys1118.group1.client.helpers.LogClient;
import isys1118.group1.shared.EditActivityInputs;
import isys1118.group1.shared.ValidateActivityInput;

public class SubmitActivityHandler implements ClickHandler {
	
	private SubmitActivityServiceAsync sasa;
	private final String activityId;
	private final String courseId;
	
	public SubmitActivityHandler(String activityId, String courseId) {
		this.activityId = activityId;
		this.courseId = courseId;
	}

	@Override
	public void onClick(ClickEvent event) {
		
		// get text boxes
		Element typeBox = DOM.getElementById("edit-type");
		Element dayBox = DOM.getElementById("edit-day");
		Element timehBox = DOM.getElementById("edit-timestarth");
		Element timemBox = DOM.getElementById("edit-timestartm");
		Element durmBox = DOM.getElementById("edit-durationm");
		Element casualBox = DOM.getElementById("edit-casual");
		
		// check that each element is the correct type
		InputElement typeBoxI = (InputElement) typeBox;
		InputElement dayBoxI = (InputElement) dayBox;
		InputElement timehBoxI = (InputElement) timehBox;
		InputElement timemBoxI = (InputElement) timemBox;
		InputElement durmBoxI = (InputElement) durmBox;
		InputElement casualBoxI = (InputElement) casualBox;
		
		// get contents from box
		String typeStr = typeBoxI.getValue().trim();
		String dayStr = dayBoxI.getValue().trim();
		String timehStr = timehBoxI.getValue().trim();
		String timemStr = timemBoxI.getValue().trim();
		String durmStr = durmBoxI.getValue().trim();
		String casualStr = casualBoxI.getValue().trim();
		
		// validate on client
		String[] errors = ValidateActivityInput.validateInputsClient(
				typeStr, dayStr, timehStr, timemStr, durmStr, casualStr);
		
		// if errors exist, show them on the page and don't send to server
		// Note that not assigning a casual is fine, but we can't send the
		// course for approval without assigning casuals to all activities.
		if (errors != null && errors.length > 0) {
			if (!doErrorMessages(errors)) {
				return;
			}
		}
		
		// TODO no major errors so send to server for validation
		sasa = GWT.create(SubmitActivityService.class);
		EditActivityInputs eai = EditActivityInputs.createClient(
				typeStr,
				dayStr,
				timehStr,
				timemStr,
				durmStr,
				casualStr,
				courseId);
		sasa.submit(activityId, eai, new AsyncCallback<EditActivityInputs>() {

			@Override
			public void onFailure(Throwable caught) {
				LogClient.logError("Error validating the activity inputs.", caught);
			}

			@Override
			public void onSuccess(EditActivityInputs result) {
				
				// if there are errors, just display the error messages.
				if (!result.success && result.errors != null && result.errors.length > 0) {
					doErrorMessages(result.errors);
					return;
				}
				
				// otherwise, switch controllers.
				Window.alert("Saved!");
				
			}
		
		});
		
	}
	
	private boolean doErrorMessages(String[] errors) {
		boolean allowSave = true;
		for (String e : errors) {
			LogClient.logMessage(e);
			if (e.equalsIgnoreCase("type")) {
				Element typeError = DOM.getElementById("edit-type-error");
				typeError.setInnerHTML("<p>Type must be either 'Lecture', 'Tutorial', 'Practical', or 'Lab'</p>");
				typeError.getStyle().setDisplay(Style.Display.INLINE);
				allowSave = false;
			}
			else if (e.equalsIgnoreCase("day")) {
				Element dayError = DOM.getElementById("edit-day-error");
				dayError.setInnerHTML("<p>Day must be a day of the week (ex. 'Monday')</p>");
				dayError.getStyle().setDisplay(Style.Display.INLINE);
				allowSave = false;
			}
			else if (e.equalsIgnoreCase("timeh") ||
					e.equalsIgnoreCase("timem")) {
				Element timeError = DOM.getElementById("edit-timestart-error");
				timeError.setInnerHTML("<p>Hours must be between 7 and 21. Minutes must be between 0 and 59.</p>");
				timeError.getStyle().setDisplay(Style.Display.INLINE);
				allowSave = false;
			}
			else if (e.equalsIgnoreCase("durm")) {
				Element durmError = DOM.getElementById("edit-durationm-error");
				durmError.setInnerHTML("<p>Duration must be between 30 and 240 minutes</p>");
				durmError.getStyle().setDisplay(Style.Display.INLINE);
				allowSave = false;
			}
			else if (e.contains("casual")) {
				if (!e.contains(".")) {
					LogClient.logMessage("found a blank casual: " + e);
					continue;
				}
				String eMessage = e.substring(e.indexOf('.') + 1);
				LogClient.logMessage(eMessage);
				Element casualError = DOM.getElementById("edit-casual-error");
				if (eMessage.contains("no_input")) {
					continue;
				}
				else if (eMessage.contains("does_not_exist")) {
					casualError.setInnerHTML("<p>This casual does not exist in the database!</p>");
					casualError.getStyle().setDisplay(Style.Display.INLINE);
					allowSave = false;
				}
				else if (eMessage.contains("exception_raised") ||
						eMessage.contains("database_error")) {
					casualError.setInnerHTML("<p>There was an error checking the user.</p>");
					casualError.getStyle().setDisplay(Style.Display.INLINE);
					allowSave = false;
				}
				else if (eMessage.contains("clash")) {
					String clashId = eMessage.substring(eMessage.indexOf('=') + 1);
					casualError.setInnerHTML("<p>There was a clash with Activity " + clashId + "</p>");
					casualError.getStyle().setDisplay(Style.Display.INLINE);
					allowSave = false;
				}
			}
		}
		return allowSave;
	}

}
