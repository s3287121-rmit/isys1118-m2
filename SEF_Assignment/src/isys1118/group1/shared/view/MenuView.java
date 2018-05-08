package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.shared.model.MenuModel;
import isys1118.group1.shared.model.Model;

public class MenuView extends View {
	
	/**
	 * First nested: each activity
	 * Second nest: activity details
	 *   0: pagetype
	 *   1: pageid (usually null)
	 *   2: name
	 */
	private String[][] options;

	@Override
	public void setView(Model model) {
		MenuModel cm = (MenuModel) model;
		
		// options
		options = new String[3][3];
		
		options[0][0] = "courselist";
		options[0][1] = "";
		options[0][2] = "Courses";
		
		options[1][0] = "course";
		options[1][1] = "";
		options[1][2] = "Test Course";
		
		options[2][0] = "logout";
		options[2][1] = "";
		options[2][2] = "Log Out";
		
	}

	@Override
	public void show() {
		final RootPanel menu = RootPanel.get("menu");
		
		// clear screen
		clearMenu();
		
		// create vp
		VerticalPanel vp = new VerticalPanel();
		menu.add(vp);
		
		// set title
		vp.add(new HTML("<h2>Menu</h2>"));
		
		// options
		VerticalPanel optionPanel = new VerticalPanel();
		vp.add(optionPanel);
		for (String[] optionSingle: options) {
			HTML anOption = new HTML(optionSingle[2]);
			anOption.addClickHandler(new ControllerLink(optionSingle[0], optionSingle[1]));
			vp.add(anOption);
		}
	}

}
