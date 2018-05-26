package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.client.UpdateCasualService;
import isys1118.group1.client.UpdateCasualServiceAsync;
import isys1118.group1.client.helpers.CasualModal;
import isys1118.group1.shared.CasualInfo;
import isys1118.group1.shared.view.ActivityEditView;

/**
 * Confirms that a casual should be set when the user clicks a confirm button.
 */
public class CasualListHandler implements ClickHandler {

	private final ActivityEditView EAView;
	private final CasualModal modal;
	private UpdateCasualServiceAsync ucsa;
	
	public CasualListHandler(ActivityEditView eav, CasualModal cm) {
		this.EAView = eav;
		this.modal = cm;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		
		// get text boxes
		Element dayBox = DOM.getElementById("edit-day");
		Element timehBox = DOM.getElementById("edit-timestarth");
		Element timemBox = DOM.getElementById("edit-timestartm");
		Element durmBox = DOM.getElementById("edit-durationm");
		
		// check that each element is the correct type
		InputElement dayBoxI = (InputElement) dayBox;
		InputElement timehBoxI = (InputElement) timehBox;
		InputElement timemBoxI = (InputElement) timemBox;
		InputElement durmBoxI = (InputElement) durmBox;
		
		// get contents from box
		String dayStr = dayBoxI.getValue().trim();
		String timehStr = timehBoxI.getValue().trim();
		String timemStr = timemBoxI.getValue().trim();
		String durmStr = durmBoxI.getValue().trim();
		
		ucsa = GWT.create(UpdateCasualService.class);
		ucsa.getAllAvailableCasuals(
				EAView.getCasualId(),
				EAView.getCourseId(),
				EAView.getActivityId(),
				dayStr,
				timehStr,
				timemStr,
				durmStr,
				new AsyncCallback<CasualInfo[]>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("An error occurred while getting the casual's information.");
					}
		
					@Override
					public void onSuccess(CasualInfo[] result) {
						if (result != null && result.length != 0) {
							modal.showAllCasuals(result);
						}
						else if (result.length == 0) {
							modal.showAllCasuals(null);
						}
						else {
							Window.alert("There was a problem getting the casual's information.");
						}
					}
					
				});
	}

}
