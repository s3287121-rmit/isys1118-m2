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
 * Makes the Casual Modal box show. Also sets relevant information to prevent
 * false information from showing.
 */
public class CasualPopupShower implements ClickHandler {
	
	private CasualModal modal;
	private ActivityEditView activityEdit;
	private UpdateCasualServiceAsync ucsa;
	
	public CasualPopupShower(ActivityEditView eav, CasualModal cm) {
		this.modal = cm;
		this.activityEdit = eav;
	}

	@Override
	public void onClick(ClickEvent event) {
		// hard reset of casual ID
		if (activityEdit != null) {
			modal.setCasualId(activityEdit.getCasualId());
		}
		modal.clearContent();
		if (!modal.isShowing()) {
			modal.show();
		}
		
		ucsa = GWT.create(UpdateCasualService.class);
		ucsa.getCasualInfo(
				modal.getCasualId(),
				new AsyncCallback<CasualInfo>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("An error occurred while getting the casual's information.");
					}

					@Override
					public void onSuccess(CasualInfo result) {
						if (result != null && !result.noCasual) {
							modal.showCasualSingle(
									result.casualId,
									result.casualName,
									result.casualRate,
									result.casualCourses);
						}
						else if (result != null && result.noCasual) {
							modal.showCasualSingle(null, null, null, null);
						}
						else {
							Window.alert("There was a problem getting the casual's information.");
						}
					}
					
				});
		
	}

}
