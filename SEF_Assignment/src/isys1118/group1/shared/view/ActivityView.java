package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.CasualPopupShower;
import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.client.helpers.CasualModal;

public class ActivityView extends View {
	
	private String activityId;
	private String title;
	private String courseId;
	private String courseName;
	private String type;
	private String day;
	private String startTime;
	private String duration;
	private String casual;
	private String casualPrice;
	
	private boolean canEdit;
	private boolean canViewCost;

	@Override
	public void setView() {}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setCasual(String casual) {
		this.casual = casual;
	}
	
	public void setCasualPrice(String casualPrice) {
		this.casualPrice = casualPrice;
	}
	
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	public void setCanViewCost(boolean canViewCost) {
		this.canViewCost = canViewCost;
	}

	@Override
	public void show() {
		final RootPanel title = RootPanel.get("title");
		final RootPanel content = RootPanel.get("content");
		final RootPanel popup = RootPanel.get("popup");
		
		// set up casual popup
		String casualId = null;
		if (casual != null && !casual.equals("None")) {
			casualId = casual.split(" ")[0];
		}
		CasualModal casualModal = new CasualModal(casualId, null, true);
		popup.add(casualModal);
		
		// clear screen
		clearMain();
		
		// set title
		title.add(new Label(this.title));
		
		VerticalPanel vp = new VerticalPanel();
		content.add(vp);
		
		// details
		HTML courseLink = new HTML("<p>Course: " + courseId + " " + courseName + "</p>");
		courseLink.addClickHandler(new ControllerLink("course", courseId));
		courseLink.addStyleName("pretty-link");
		vp.add(courseLink);
		vp.add(new HTML("<p>Type: " + type + "</p>"));
		vp.add(new HTML("<p>Time: " + day + " " + startTime + " (" + duration + ")" + "</p>"));
		
		// casual details
		HTML casualLink = new HTML("<p>Assigned Casual: " + casual + "</p>");
		casualLink.addStyleName("pretty-link");
		casualLink.addClickHandler(new CasualPopupShower(null, casualModal));
		vp.add(casualLink);
		
		// optional casual price
		if (canViewCost && casual != null && !casual.isEmpty() &&
				casualPrice != null && !casualPrice.isEmpty()) {
			vp.add(new HTML("<p>Cost of casual: $" + casualPrice + "</p>"));
		}
		
		// edit button
		if (canEdit) {
			Button edit = new Button("Edit");
			edit.addClickHandler(new ControllerLink("edit", activityId));
			edit.setStyleName("right-align");
			vp.add(edit);
		}
	}

}
