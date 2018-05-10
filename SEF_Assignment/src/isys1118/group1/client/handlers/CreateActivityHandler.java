package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.client.ChangeControllerService;
import isys1118.group1.client.ChangeControllerServiceAsync;
import isys1118.group1.client.LogClient;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class CreateActivityHandler implements ClickHandler {

	private ChangeControllerServiceAsync ccs;
	private String courseId;
	
	public CreateActivityHandler(String courseId) {
		this.courseId = courseId;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		ccs = GWT.create(ChangeControllerService.class);
		ccs.addActivity(courseId, new AsyncCallback<ViewSerial>() {

			@Override
			public void onFailure(Throwable caught) {
				LogClient.logError("There was a problem getting a new Controller.\n" + caught.getMessage(), caught);
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
