package isys1118.group1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.shared.view.View;

public interface MenuServiceAsync {
	public void getMenuOption(String option, AsyncCallback<View> callback);
}
