package isys1118.group1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public interface MenuServiceAsync {
	public void getMenuOption(String option, AsyncCallback<View> callback);
	
	public void resetDatabase(String name, String id, AsyncCallback<ViewSerial> callback);
}
