package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.HintBoxHandler;
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
		submitButton.getElement().setId("login-button");
		submitButton.addClickHandler(new LoginSubmitHandler());
		vp.add(submitButton);
		
		// hint
		
		// hint dialog box
		DialogBox hintBox = new DialogBox(false, false);
		hintBox.getElement().setId("hint-box");
		hintBox.hide();
		VerticalPanel hintBoxVP = new VerticalPanel();
		
		Button closeHint = new Button("X");
		closeHint.addStyleName("right-align");
		closeHint.addClickHandler(new HintBoxHandler(hintBox));
		hintBoxVP.add(closeHint);
		
		HorizontalPanel userHeader = new HorizontalPanel();
		userHeader.addStyleName("hint-table-header");
		userHeader.add(new HTML("User Type"));
		userHeader.add(new HTML("ID"));
		userHeader.add(new HTML("Name"));
		userHeader.add(new HTML("Password"));
		hintBoxVP.add(userHeader);
		
		HorizontalPanel user1 = new HorizontalPanel();
		user1.addStyleName("hint-table-row");
		user1.add(new HTML("Approver"));
		user1.add(new HTML("e84412"));
		user1.add(new HTML("Zeus"));
		user1.add(new HTML("lightning"));
		hintBoxVP.add(user1);
		
		HorizontalPanel user2 = new HorizontalPanel();
		user2.addStyleName("hint-table-row");
		user2.add(new HTML("Course Coordinator"));
		user2.add(new HTML("e25231"));
		user2.add(new HTML("Atrides"));
		user2.add(new HTML("king"));
		hintBoxVP.add(user2);
		
		HorizontalPanel user3 = new HorizontalPanel();
		user3.addStyleName("hint-table-row");
		user3.add(new HTML("Course Coordinator"));
		user3.add(new HTML("e39431"));
		user3.add(new HTML("Priam"));
		user3.add(new HTML("wall"));
		hintBoxVP.add(user3);
		
		HorizontalPanel user4 = new HorizontalPanel();
		user4.addStyleName("hint-table-row");
		user4.add(new HTML("Casual"));
		user4.add(new HTML("e89953"));
		user4.add(new HTML("Hector"));
		user4.add(new HTML("spear"));
		hintBoxVP.add(user4);
		
		HorizontalPanel user5 = new HorizontalPanel();
		user5.addStyleName("hint-table-row");
		user5.add(new HTML("Admin"));
		user5.add(new HTML("e99999"));
		user5.add(new HTML("Loki"));
		user5.add(new HTML("password"));
		hintBoxVP.add(user5);
		
		hintBox.add(hintBoxVP);
		
		// hint button
		Button hintButton = new Button("HINT");
		hintButton.addClickHandler(new HintBoxHandler(hintBox));
		vp.add(hintButton);
		
		// error message
		HTML message = new HTML();
		message.getElement().setId("login-message");
		vp.add(message);
		
		login.add(vp);
		
	}

}
