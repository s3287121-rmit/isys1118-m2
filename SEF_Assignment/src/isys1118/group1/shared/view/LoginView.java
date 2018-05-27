package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.LoginSubmitHandler;

public class LoginView extends View {

	@Override
	public void setView() {}

	@Override
	public void show() {
		RootPanel login = RootPanel.get("login");
		
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("login-box");
		
		// userid
		HorizontalPanel hpId = new HorizontalPanel();
		HTML idLabel = new HTML("<p>User ID: </p>");
		hpId.add(idLabel);
		TextBox idInput = new TextBox();
		idInput.getElement().setId("login-username");
		hpId.add(idInput);
		vp.add(hpId);
		
		// password
		HorizontalPanel hpPass = new HorizontalPanel();
		HTML passLabel = new HTML("<p>Password: </p>");
		hpPass.add(passLabel);
		TextBox passInput = new TextBox();
		passInput.getElement().setId("login-password");
		hpPass.add(passInput);
		vp.add(hpPass);
		
		// button
		Button submitButton = new Button("Login");
		submitButton.addClickHandler(new LoginSubmitHandler());
		vp.add(submitButton);
		
		// error message
		HTML message = new HTML();
		message.getElement().setId("login-message");
		vp.add(message);
		
		login.add(vp);
		
	}

}
