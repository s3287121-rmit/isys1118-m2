package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.client.ChangeControllerService;
import isys1118.group1.client.ChangeControllerServiceAsync;
import isys1118.group1.client.helpers.LogClient;
import isys1118.group1.client.helpers.MessageDisplay;
import isys1118.group1.shared.error.PermissionException;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class ControllerLink implements ClickHandler {

	private ChangeControllerServiceAsync ccs;
	private final String controllerType;
	private final String controllerId;
	
	public ControllerLink(String type, String id) {
		this.controllerType = type;
		this.controllerId = id;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		ccs = GWT.create(ChangeControllerService.class);
		ccs.changeController(controllerType, controllerId, new AsyncCallback<ViewSerial>() {

			@Override
			public void onFailure(Throwable caught) {
				LogClient.logError("There was a problem getting a new Controller.\n" + caught.getMessage(), caught);
				if (caught instanceof PermissionException) {
					PermissionException pe = (PermissionException) caught;
					MessageDisplay.displayMessage(pe.getMessage());
				}
			}

			@Override
			public void onSuccess(ViewSerial result) {
				if (result.menu != null) {
					result.menu.show();
				}
				else {
					View.clearMenu();
				}
				if (result.content != null) {
					result.content.show();
				}
				else {
					View.clearContent();
				}
			}
			
		});
	}

}
