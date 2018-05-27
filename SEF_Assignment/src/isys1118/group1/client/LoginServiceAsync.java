package isys1118.group1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.shared.view.ViewSerial;

public interface LoginServiceAsync {
	
	public void login(String username, String password, AsyncCallback<ViewSerial> callback);
	
}
