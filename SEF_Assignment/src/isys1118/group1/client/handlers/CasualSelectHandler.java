package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.client.UpdateCasualService;
import isys1118.group1.client.UpdateCasualServiceAsync;
import isys1118.group1.client.helpers.CasualModal;
import isys1118.group1.shared.CasualInfo;
import isys1118.group1.shared.view.ActivityEditView;

/**
 * Selects a Casual from a list, updates the modal dialog to show that the
 * casual has been selected.
 */
public class CasualSelectHandler implements ClickHandler {
	
	private CasualModal modal;
	private ActivityEditView activityEdit;
	private UpdateCasualServiceAsync ucsa;
	
	public CasualSelectHandler(ActivityEditView eav, CasualModal cm) {
		this.modal = cm;
		this.activityEdit = eav;
	}

	@Override
	public void onClick(ClickEvent event) {
		
		// check if there is no selected casual.
		// If not, that means we throw an alert and return.
		if (modal.getSelectedCasual() == null) {
			Window.alert("No casual has been selected!");
			return;
		}
		
		ucsa = GWT.create(UpdateCasualService.class);
		ucsa.getCasualInfo(
				modal.getSelectedCasual().getCasualId(),
				new AsyncCallback<CasualInfo>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("An error occurred while getting the casual's information.");
					}

					@Override
					public void onSuccess(CasualInfo result) {
						if (result != null && !result.noCasual) {
							modal.setCasualId(result.casualId);
							modal.showCasualSingle(
									result.casualId,
									result.casualName,
									result.casualRate,
									result.casualCourses);
						}
						else {
							Window.alert("There was a problem getting the casual's information.");
						}
					}
					
				});
		
	}

}
