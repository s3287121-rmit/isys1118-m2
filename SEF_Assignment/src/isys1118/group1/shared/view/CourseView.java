package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.shared.model.CourseModel;
import isys1118.group1.shared.model.Model;

public class CourseView extends View {
	
	private String title;
	private String description;
	/**
	 * First nested: each activity
	 * Second nest: activity details
	 *   0: ID
	 *   1: Type
	 *   2: Day
	 *   3: Time
	 *   4: Casual ID
	 *   5: Casual Name
	 */
	private String[][] activities;
	private String budget;

	@Override
	public void setView(Model model) {
		CourseModel cm = (CourseModel) model;
		
		// title
		title = "Test Course";
		
		// description
		description = "<p>This course is not real!</p><p>I am doing this to test the page</p>";
		
		// activities
		activities = new String[4][6];
		
		activities[0][0] = "AAA111";
		activities[0][1] = "Lecture";
		activities[0][2] = "Monday";
		activities[0][3] = "12:30";
		activities[0][4] = "e44444";
		activities[0][5] = "John Johnson";

		activities[1][0] = "BBB222";
		activities[1][1] = "Tutorial";
		activities[1][2] = "Wednesday";
		activities[1][3] = "9:30";
		activities[1][4] = "e11111";
		activities[1][5] = "Daniel Decker";
		
		activities[2][0] = "CCC333";
		activities[2][1] = "Tutorial";
		activities[2][2] = "Wednesday";
		activities[2][3] = "16:30";
		activities[2][4] = "e33333";
		activities[2][5] = "Linda Lovegood";

		activities[3][0] = "DDD666";
		activities[3][1] = "Tutorial";
		activities[3][2] = "Tuesday";
		activities[3][3] = "10:30";
		activities[3][4] = "e22222";
		activities[3][5] = "Elizbeth Elsewood";
		
		// budget
		budget = "$12,330.29";
		
	}

	@Override
	public void show() {
		final RootPanel title = RootPanel.get("title");
		final RootPanel content = RootPanel.get("content");
		
		// clear screen
		clearMain();
		
		// set title
		title.add(new Label(this.title));
		
		VerticalPanel vp = new VerticalPanel();
		content.add(vp);
		
		// description
		HTML descHeader = new HTML("<h2>Description</h2>");
		HTML descHtml = new HTML(description);
		vp.add(descHeader);
		vp.add(descHtml);
		
		// activities
		HTML actHeader = new HTML("<h2>Activities</h2>");
		VerticalPanel actPanel = new VerticalPanel();
		vp.add(actHeader);
		vp.add(actPanel);
		for (String[] actSingle: activities) {
			FocusPanel wrapper = new FocusPanel();
			
			VerticalPanel actCard = new VerticalPanel();
			actCard.addStyleName("activity-card");
			
			HorizontalPanel topLine = new HorizontalPanel();
			HTML topLineType = new HTML("<p>" + actSingle[1] + "</p>");
			topLine.add(topLineType);
			HTML topLineDay = new HTML("<p>" + actSingle[2] + "</p>");
			topLine.add(topLineDay);
			HTML topLineTime = new HTML("<p>" + actSingle[3] + "</p>");
			topLine.add(topLineTime);
			actCard.add(topLine);
			
			HorizontalPanel bottomLine = new HorizontalPanel();
			HTML bottomLineCasId = new HTML("<p>" + actSingle[4] + "</p>");
			bottomLine.add(bottomLineCasId);
			HTML bottomLineCasName = new HTML("<p>" + actSingle[5] + "</p>");
			bottomLine.add(bottomLineCasName);
			actCard.add(bottomLine);
			
			wrapper.add(actCard);
			wrapper.addClickHandler(new ControllerLink("activity", actSingle[0]));
			actPanel.add(wrapper);
		}
		
		// budget
		HTML budgetHeader = new HTML("<h2>Budget</h2>");
		HTML budgetAmount = new HTML("<p>" + budget + "</p>");
		vp.add(budgetHeader);
		vp.add(budgetAmount);
		
	}

}
