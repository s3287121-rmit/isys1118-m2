package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.shared.model.Model;

public class CourseView extends View {
	
	public static final int APPROVAL_EDITING = 0x1001;
	public static final int APPROVAL_PENDING = 0x1002;
	public static final int APPROVAL_ACCEPTED = 0x1003;
	public static final int APPROVAL_REJECTED = 0x1004;
	
	private String title;
	private String description;
	/**
	 * First nested: each activity
	 * Second nest: activity details
	 *   0: ID
	 *   1: Type
	 *   2: Day
	 *   3: Time
	 *   4: Casual
	 */
	private String[][] activities;
	private String budget;
	
	private int approvalStatus;

	@Override
	public void setView(Model model) {}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setActivities(String[][] activities) {
		this.activities = activities;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
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
		actPanel.addStyleName("card-list");
		for (String[] actSingle: activities) {
			FocusPanel wrapper = new FocusPanel();
			
			VerticalPanel actCard = new VerticalPanel();
			actCard.addStyleName("card");
			
			HorizontalPanel topLine = new HorizontalPanel();
			topLine.addStyleName("card-third-line");
			HTML topLineType = new HTML("<p>" + actSingle[1] + "</p>");
			topLine.add(topLineType);
			HTML topLineDay = new HTML("<p>" + actSingle[2] + "</p>");
			topLine.add(topLineDay);
			HTML topLineTime = new HTML("<p>" + actSingle[3] + "</p>");
			topLine.add(topLineTime);
			actCard.add(topLine);
			
			HorizontalPanel bottomLine = new HorizontalPanel();
			HTML bottomLineCasId = new HTML("<p>Casual: " + actSingle[4] + "</p>");
			bottomLine.add(bottomLineCasId);
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
		
		// status
		HTML statusHeader = new HTML("<h2>Status</h2>");
		vp.add(statusHeader);
		HTML status = new HTML();
		vp.add(status);
		if (approvalStatus == APPROVAL_REJECTED) {
			HTML recentRejection = new HTML("<p>NOTE: This course was recently rejected. Please update and resend.</p>");
			recentRejection.addStyleName("rejected");
			vp.add(recentRejection);
		}
		if (approvalStatus == APPROVAL_EDITING ||
				approvalStatus == APPROVAL_REJECTED) {
			Button sendButton = new Button();
			sendButton.setText("Send for approval");
			sendButton.addStyleName("right-align");
			status.setHTML("<p>Course needs to be sent for approval!</p>");
			vp.add(sendButton);
		}
		if (approvalStatus == APPROVAL_PENDING) {
			HorizontalPanel hp = new HorizontalPanel();
			hp.addStyleName("right-align");
			Button acceptButton = new Button();
			acceptButton.setText("Accept");
			Button rejectButton = new Button();
			rejectButton.setText("Reject");
			status.setHTML("<p>Course is waiting for approval.</p>");
			hp.add(acceptButton);
			hp.add(rejectButton);
			vp.add(hp);
		}
		if (approvalStatus == APPROVAL_ACCEPTED) {
			status.setHTML("<p>Course has been approved.</p>");
		}
		
	}

}
