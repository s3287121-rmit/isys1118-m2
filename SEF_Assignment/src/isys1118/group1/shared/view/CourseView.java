package isys1118.group1.shared.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.ControllerLink;
import isys1118.group1.client.handlers.CourseStatusHandler;
import isys1118.group1.client.handlers.CreateActivityHandler;
import isys1118.group1.shared.Constants;

public class CourseView extends View {
	
	public static final int APPROVAL_EDITING = 0x1001;
	public static final int APPROVAL_PENDING = 0x1002;
	public static final int APPROVAL_ACCEPTED = 0x1003;
	public static final int APPROVAL_REJECTED = 0x1004;
	
	private String courseId;
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
	private String cost;
	private boolean overpriced;
	
	private int approvalStatus;
	
	private boolean addActivity;
	private boolean approvalButtons;
	private boolean approvalView;

	@Override
	public void setView() {}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

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

	public void setCost(String cost) {
		this.cost = cost;
	}

	public void setOverpriced(boolean overpriced) {
		this.overpriced = overpriced;
	}

	public boolean isApprovalButtons() {
		return approvalButtons;
	}

	public void setApprovalButtons(boolean approvalButtons) {
		this.approvalButtons = approvalButtons;
	}

	public boolean isAddActivity() {
		return addActivity;
	}

	public void setAddActivity(boolean addActivity) {
		this.addActivity = addActivity;
	}

	public boolean isApprovalView() {
		return approvalView;
	}

	public void setApprovalView(boolean approvalView) {
		this.approvalView = approvalView;
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
			actPanel.add(wrapper);
			
			VerticalPanel actCard = new VerticalPanel();
			actCard.addStyleName("card");
			wrapper.add(actCard);
			
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
			
			wrapper.addClickHandler(new ControllerLink("activity", actSingle[0]));
		}

		// add activity button if allowed to add.
		if (addActivity) {
			Button edit = new Button("Add new activity");
			edit.addClickHandler(new CreateActivityHandler(courseId));
			edit.setStyleName("right-align");
			vp.add(edit);
		}
		
		// budget - only if allowed
		if (cost != null) {
			HTML budgetHeader = new HTML("<h2>Budget</h2>");
			HTML costAmount = new HTML("<p>Total Cost of Casuals: " + cost + "</p>");
			if (overpriced) {
				costAmount.addStyleName("overpriced");
			}
			HTML budgetAmount = new HTML("<p>Allowed Budget: " + budget + "</p>");
			vp.add(budgetHeader);
			vp.add(costAmount);
			vp.add(budgetAmount);
		}
		
		// status message
		if (approvalView) {
			HTML statusHeader = new HTML("<h2>Status</h2>");
			vp.add(statusHeader);
			HTML status = new HTML();
			vp.add(status);
			if (approvalStatus == APPROVAL_REJECTED) {
				HTML recentRejection = new HTML(
						"<p>NOTE: This course was recently rejected. " +
						"Please update and resend.</p>");
				recentRejection.addStyleName("rejected");
				vp.add(recentRejection);
			}
			// set status message
			if (approvalStatus == APPROVAL_EDITING ||
					approvalStatus == APPROVAL_REJECTED) {
				status.setHTML("<p>Course needs to be sent for approval!</p>");
			}
			else if(approvalStatus == APPROVAL_PENDING) {
				status.setHTML("<p>Course is waiting for approval.</p>");
			}
			else if (approvalStatus == APPROVAL_ACCEPTED) {
				status.setHTML("<p>Course has been approved.</p>");
			}
		}
		
		// status state buttons
		if (approvalButtons) {
			if (approvalStatus == APPROVAL_EDITING ||
						approvalStatus == APPROVAL_REJECTED) {
				Button sendButton = new Button();
				sendButton.setText("Send for approval");
				sendButton.addStyleName("right-align");
				sendButton.addClickHandler(
						new CourseStatusHandler(courseId,
								Constants.COURSE_STATUS_PENDING));
				vp.add(sendButton);
			}
			else if (approvalStatus == APPROVAL_PENDING) {
				HorizontalPanel hp = new HorizontalPanel();
				hp.addStyleName("right-align");
				Button acceptButton = new Button();
				acceptButton.setText("Accept");
				acceptButton.addClickHandler(
						new CourseStatusHandler(courseId,
								Constants.COURSE_STATUS_ACCEPTED));
				Button rejectButton = new Button();
				rejectButton.setText("Reject");
				rejectButton.addClickHandler(
						new CourseStatusHandler(courseId,
								Constants.COURSE_STATUS_REJECTED));
				hp.add(acceptButton);
				hp.add(rejectButton);
				vp.add(hp);
			}
		}
		
	}

}
