package isys1118.group1.client.helpers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import isys1118.group1.client.handlers.CasualConfirmHandler;
import isys1118.group1.client.handlers.CasualListHandler;
import isys1118.group1.client.handlers.CasualPopupHider;
import isys1118.group1.client.handlers.CasualPopupShower;
import isys1118.group1.client.handlers.CasualSelectHandler;
import isys1118.group1.shared.CasualInfo;
import isys1118.group1.shared.view.ActivityEditView;

public class CasualModal extends DialogBox {
	
	public class CasualSelect {
		
		private final String casualId;
		private final String casualName;
		private final String casualRate;
		private final String casualConflict;
		private final boolean conflict;
		private final FocusPanel panel;
		
		private CasualSelect(CasualInfo details) {
			casualId = details.casualId;
			casualName = details.casualName;
			casualRate = details.casualRate;
			casualConflict = details.casualConflict;
			panel = new FocusPanel();
			if (casualConflict == null || casualConflict.isEmpty()) {
				conflict = false;
			}
			else {
				conflict = true;
			}
			createPanels();
		}
		
		private void createPanels() {
			panel.addStyleName("select-casual-single");
			
			VerticalPanel vp = new VerticalPanel();
			
			// name
			HTML name = new HTML("<p>" + casualId + " " + casualName + "</p>");
			vp.add(name);
			
			// rate
			HTML rate = new HTML("<p>" + casualRate + "</p>");
			vp.add(rate);
			
			// conflict
			HTML conflictMessage = new HTML("<p>Conflict: " + getCasualConflict() + "</p>");
			vp.add(conflictMessage);
			
			panel.add(vp);
			
			setUpClickHandler();
			
		}

		public String getCasualId() {
			return casualId;
		}

		public String getCasualName() {
			return casualName;
		}

		public String getCasualRate() {
			return casualRate;
		}

		public String getCasualConflict() {
			return casualConflict == null || casualConflict.isEmpty()
					? "None!" : casualConflict;
		}
		
		private void removeSelectStyle() {
			panel.removeStyleName("selected");
		}
		
		private void addSelectStyle() {
			panel.addStyleName("selected");
		}
		
		public FocusPanel getPanel() {
			return panel;
		}
		
		public void setUpClickHandler() {
			// if there's a conflict, display it a little differently.
			if (conflict) {
				panel.addStyleName("conflict");
			}
			// otherwise, set up a ClickHandler which up
			else {
				addSelectionHandler();
			}
		}
		
		private void addSelectionHandler() {
			panel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (selectedCasual != null) {
						selectedCasual.removeSelectStyle();
					}
					selectedCasual = CasualSelect.this;
					selectedCasual.addSelectStyle();
				}
			});
		}
		
	}
	
	// preset info for activity
	private ActivityEditView activityEdit;

	// currently set casual
	private String casualId;
	
	/**
	 * close handler -- used to handle the X button press and closing
	 * functionality.
	 */
	private CasualPopupHider hider;
	
	private SimplePanel content;
	private HTML title;
	
	/**
	 * The casual selected. If null, then when the "select" button is clicked
	 * it will set the casual as null. Otherwise, it will set it to this.
	 */
	private CasualSelect selectedCasual = null;
	
	public CasualModal(String casualId, ActivityEditView eav) {
		super(true, true);
		hide();
		this.casualId = casualId;
		this.activityEdit = eav;
		this.selectedCasual = null;
		createInnerHtml();
	}
	
	/**
	 * Build after constructing.
	 */
	private void createInnerHtml() {
		// set element for RootPanel acces.
		this.getElement().setId("casual-modal");
		
		// set close handler
		hider = new CasualPopupHider(activityEdit, this);
		this.addCloseHandler(hider);
		
		VerticalPanel vp = new VerticalPanel();
		
		// exit button
		Button exit = new Button("X");
		exit.getElement().setId("casual-modal-close");
		exit.addClickHandler(hider);
		vp.add(exit);
		
		// set title
		title = new HTML();
		title.getElement().setId("casual-modal-title");
		vp.add(title);
		
		// content
		content = new SimplePanel();
		content.getElement().setId("casual-modal-content");
		vp.add(content);
		
		this.add(vp);
	}
	
	public void setCasualId(String casualId) {
		this.casualId = casualId;
	}

	public CasualSelect getSelectedCasual() {
		return selectedCasual;
	}

	public String getCasualId() {
		return casualId;
	}
	
	/**
	 * Callback function for showing a single user.
	 * @param userId
	 * @param userName
	 * @param userRate
	 * @param userCourses
	 */
	public void showCasualSingle(String userId, String userName,
			String userRate, String[] userCourses) {
		clearContent();
		
		VerticalPanel vp = new VerticalPanel();
		selectedCasual = null;
		
		// Select button --  takes to casual list
		Button selectDifferent = new Button("Select Casual");
		selectDifferent.addClickHandler(
				new CasualListHandler(activityEdit, this));
		vp.add(selectDifferent);
		
		// there is no casual listed!
		if (userId != null) {

			// casual name
			HTML nameDisplay =
					new HTML("<p>" + userId + " " + userName + "</p>");
			vp.add(nameDisplay);
			
			// casual rate
			HTML rateDisplay = new HTML("<p>Rate: " + userRate + "</p>");
			vp.add(rateDisplay);
			
			// some info about the casual's courses
			HTML assignedCoursesTitle = new HTML("<p>Assigned Courses:</p>");
			vp.add(assignedCoursesTitle);
			
			if (userCourses == null || userCourses.length == 0) {
				HTML noCourses = new HTML("<p>None!</p>");
				noCourses.addStyleName("casual-assigned-course");
				vp.add(noCourses);
			}
			else {
				VerticalPanel courseList = new VerticalPanel();
				for (String s : userCourses) {
					HTML toAdd = new HTML("<p>" + s + "</p>");
					courseList.add(toAdd);
				}
				vp.add(courseList);
			}
			
			// confirm button
			Button confirm = new Button("Confirm Casual");
			confirm.addClickHandler(
					new CasualConfirmHandler(activityEdit, this));
			vp.add(confirm);
			
		}
		else {
			HTML noCas = new HTML("<p>No casual has been set!</p>");
			vp.add(noCas);
		}
		
		content.add(vp);
		
	}
	
	public void showAllCasuals(CasualInfo[] allCasuals) {
		clearContent();
		
		VerticalPanel vp = new VerticalPanel();
		selectedCasual = null;
		
		if (allCasuals != null) {
			for (CasualInfo ci : allCasuals) {
				CasualSelect cs = new CasualSelect(ci);
				vp.add(cs.getPanel());
			}
		}
		else {
			HTML noCasuals = new HTML("<p>No casuals are available for this course!</p>");
			vp.add(noCasuals);
		}
		
		HorizontalPanel hp = new HorizontalPanel();
		Button cancel = new Button("Cancel");
		cancel.addClickHandler(new CasualPopupShower(activityEdit, this));
		hp.add(cancel);
		
		Button select = new Button("Select");
		select.addClickHandler(new CasualSelectHandler(activityEdit, this));
		hp.add(select);
		
		vp.add(hp);
		
		content.add(vp);
		
	}
	
	public void clearContent() {
		content.clear();
	}
	
}
