package isys1118.group1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.shared.view.ViewSerial;

public interface ChangeCourseStatusServiceAsync {
	
	public void changeStatus(String courseId, String status, AsyncCallback<ViewSerial> callback);

}
