package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import isys1118.group1.client.LoginService;
import isys1118.group1.client.LoginServiceAsync;
import isys1118.group1.client.helpers.LogClient;
import isys1118.group1.client.helpers.MessageDisplay;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class LogoutHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
		LoginServiceAsync login = GWT.create(LoginService.class);
		login.logout(new AsyncCallback<ViewSerial>() {

			@Override
			public void onFailure(Throwable caught) {
				LogClient.logError(caught.getMessage(), caught);
				MessageDisplay.displayMessage(caught.getMessage());
			}

			@Override
			public void onSuccess(ViewSerial result) {
				// if no error, hide login page and run views
				RootPanel login = RootPanel.get("login");
				login.clear();
				login.getElement().getStyle().setVisibility(Visibility.VISIBLE);
				View.clearMenu();
				View.clearContent();
				result.content.show();
			}
			
		});
	}

}
