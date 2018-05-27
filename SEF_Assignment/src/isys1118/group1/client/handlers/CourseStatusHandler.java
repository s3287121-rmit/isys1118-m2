package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.client.ChangeCourseStatusService;
import isys1118.group1.client.ChangeCourseStatusServiceAsync;
import isys1118.group1.client.helpers.LogClient;
import isys1118.group1.client.helpers.MessageDisplay;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class CourseStatusHandler implements ClickHandler {

	private ChangeCourseStatusServiceAsync ccs;
	private String courseId;
	private String status;
	
	public CourseStatusHandler(String courseId, String status) {
		this.courseId = courseId;
		this.status = status;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		ccs = GWT.create(ChangeCourseStatusService.class);
		ccs.changeStatus(courseId, status, new AsyncCallback<ViewSerial>() {

			@Override
			public void onFailure(Throwable caught) {
				LogClient.logError(
						"There was a problem changing a Course's status.\n" +
								caught.getMessage(), caught);
				MessageDisplay.displayMessage(
						"There was a problem changing the course's status!");
			}

			@Override
			public void onSuccess(ViewSerial result) {
				
				// display the course again
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
				
				// TODO add update message
				
			}
			
		});
	}

}
