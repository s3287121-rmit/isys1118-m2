package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import isys1118.group1.client.LoginService;
import isys1118.group1.client.LoginServiceAsync;
import isys1118.group1.client.helpers.LogClient;
import isys1118.group1.client.helpers.MessageDisplay;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class LoginSubmitHandler implements ClickHandler {
	
	private LoginServiceAsync service;

	@Override
	public void onClick(ClickEvent event) {
		
		// get info from input boxes
		Element usernameBox = DOM.getElementById("login-username");
		Element passwordBox = DOM.getElementById("login-password");
		
		InputElement usernameBoxI = (InputElement) usernameBox;
		InputElement passwordBoxI = (InputElement) passwordBox;
		
		String username = usernameBoxI.getValue();
		String password = passwordBoxI.getValue();
		
		// run service
		service = GWT.create(LoginService.class);
		service.login(username, password, new AsyncCallback<ViewSerial>() {

			@Override
			public void onFailure(Throwable caught) {
				LogClient.logError(caught.getMessage(), caught);
				MessageDisplay.displayMessage(caught.getMessage());
			}

			@Override
			public void onSuccess(ViewSerial result) {
				
				// if error is found, get message box and display error
				if (result == null) {
					Element messageBox = DOM.getElementById("login-message");
					InputElement messageBoxI = (InputElement) messageBox;
					messageBoxI.setInnerHTML(
							"<p>An error occurred when trying to log in.</p>");
					messageBoxI.getStyle().setVisibility(Visibility.VISIBLE);
					LogClient.logMessage(
							"An error occurred when trying to log in.");
					return;
				}
				else if (result.error) {
					Element messageBox = DOM.getElementById("login-message");
					InputElement messageBoxI = (InputElement) messageBox;
					messageBoxI.setInnerHTML(
							"<p>" + result.errorMessage + "</p>");
					messageBoxI.getStyle().setVisibility(Visibility.VISIBLE);
					LogClient.logMessage(result.errorMessage);
					return;
				}
				
				// if no error, hide login page and run views
				RootPanel login = RootPanel.get("login");
				RootPanel hintBox = RootPanel.get("hint-box");
				login.clear();
				login.getElement().getStyle().setVisibility(Visibility.HIDDEN);
				if (hintBox != null) {
					hintBox.clear();
					hintBox.getElement().getStyle()
						.setVisibility(Visibility.HIDDEN);
				}
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
