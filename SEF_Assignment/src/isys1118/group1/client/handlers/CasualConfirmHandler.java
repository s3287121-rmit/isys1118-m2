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
public class CasualConfirmHandler implements ClickHandler {

	private final ActivityEditView EAView;
	private final CasualModal modal;
	private UpdateCasualServiceAsync ucsa;
	
	public CasualConfirmHandler(ActivityEditView eav, CasualModal cm) {
		this.EAView = eav;
		this.modal = cm;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		ucsa = GWT.create(UpdateCasualService.class);
		// can just reuse casual info.
		ucsa.getCasualInfo(
				modal.getCasualId(),
				EAView.getCourseId(),
				EAView.getActivityId(),
				new AsyncCallback<CasualInfo>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("An error occurred while getting the casual's information.");
					}
		
					@Override
					public void onSuccess(CasualInfo result) {
						if (result != null) {
							
							// set all values within the view for later use
							EAView.setCasualId(result.casualId);
							EAView.setCasualName(result.casualName);
							
							// set field
							Element casualInput = DOM.getElementById("edit-casual");
							InputElement casualInputI = (InputElement) casualInput;
							casualInputI.setValue(result.casualId + " " + result.casualName);
							
							// prepare modal for later use
							modal.hide();
							
						}
						else {
							Window.alert("There was a problem getting the casual's information.");
						}
					}
					
				});
	}

}
