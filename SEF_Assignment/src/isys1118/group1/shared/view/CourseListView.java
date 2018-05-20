package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;

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
	public void setView() {}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCourses(String[][] courses) {
		this.courses = courses;
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
		coursePanel.addStyleName("card-list");
		for (String[] courseSingle: courses) {
			FocusPanel wrapper = new FocusPanel();
			
			VerticalPanel courseCard = new VerticalPanel();
			courseCard.addStyleName("card");
			
			HorizontalPanel topLine = new HorizontalPanel();
			topLine.addStyleName("card-half-line");
			HTML topLineId = new HTML("<p>" + courseSingle[0] + "</p>");
			topLine.add(topLineId);
			HTML topLineName = new HTML("<p>" + courseSingle[1] + "</p>");
			topLine.add(topLineName);
			courseCard.add(topLine);
			
			HorizontalPanel bottomLine = new HorizontalPanel();
			bottomLine.addStyleName("card-simple-line");
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
