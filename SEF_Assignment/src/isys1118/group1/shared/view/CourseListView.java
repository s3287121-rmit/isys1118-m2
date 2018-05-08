package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.shared.model.CourseListModel;
import isys1118.group1.shared.model.Model;

public class CourseListView extends View {
	
	private String title;
	/**
	 * First nested: each activity
	 * Second nest: activity details
	 *   0: ID
	 *   1: Name
	 *   2: Desc
	 *   3: Status
	 */
	private String[][] courses;

	@Override
	public void setView(Model model) {
		CourseListModel cm = (CourseListModel) model;
		
		// title
		title = "Test Course List";
		
		// courses
		courses = new String[5][4];
		
		courses[0][0] = "abcd1234";
		courses[0][1] = "Applied Something";
		courses[0][2] = "This is a test description.";
		courses[0][3] = "Pending";
		
		courses[1][0] = "asgf6831";
		courses[1][1] = "Doing the thing";
		courses[1][2] = "This is a test description.";
		courses[1][3] = "Not Sent";
		
		courses[2][0] = "ghfd1258";
		courses[2][1] = "Learning things";
		courses[2][2] = "This is a test description.";
		courses[2][3] = "Approved";
		
		courses[3][0] = "vbee1872";
		courses[3][1] = "Big Course";
		courses[3][2] = "This is a test description.";
		courses[3][3] = "Rejected";
		
		courses[4][0] = "klhd9453";
		courses[4][1] = "Teaching Something";
		courses[4][2] = "This is a test description.";
		courses[4][3] = "Pending";
		
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
		
		// courses
		VerticalPanel coursePanel = new VerticalPanel();
		for (String[] courseSingle: courses) {
			FocusPanel wrapper = new FocusPanel();
			
			VerticalPanel courseCard = new VerticalPanel();
			courseCard.addStyleName("course-card");
			
			HorizontalPanel topLine = new HorizontalPanel();
			HTML topLineId = new HTML("<p>" + courseSingle[0] + "</p>");
			topLine.add(topLineId);
			HTML topLineName = new HTML("<p>" + courseSingle[1] + "</p>");
			topLine.add(topLineName);
			courseCard.add(topLine);
			
			HorizontalPanel bottomLine = new HorizontalPanel();
			HTML bottomLineDesc = new HTML("<p>" + courseSingle[2] + "</p>");
			bottomLine.add(bottomLineDesc);
			HTML bottomLineStatus = new HTML("<p>" + courseSingle[3] + "</p>");
			bottomLineStatus.addStyleName("right-align");
			bottomLine.add(bottomLineStatus);
			courseCard.add(bottomLine);
			
			wrapper.add(courseCard);
			wrapper.addClickHandler(new ControllerLink("course", courseSingle[0]));
			coursePanel.add(wrapper);
		}
		vp.add(coursePanel);
		
	}

}
