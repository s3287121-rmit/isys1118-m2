package isys1118.group1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public interface ChangeControllerServiceAsync {

	public void changeController(String type, String id, AsyncCallback<ViewSerial> callback);
	
}
