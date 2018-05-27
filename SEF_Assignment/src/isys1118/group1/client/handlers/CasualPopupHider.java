package isys1118.group1.client.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;

import isys1118.group1.client.helpers.CasualModal;
import isys1118.group1.shared.view.ActivityEditView;

/**
 * Fired whenever the Casual Modal box is hidden, or the X in the corner is
 * clicked.
 */
public class CasualPopupHider implements ClickHandler, CloseHandler<PopupPanel> {
	
	private CasualModal modal;
	private ActivityEditView activityEdit;
	
	public CasualPopupHider(ActivityEditView eav, CasualModal cm) {
		this.modal = cm;
		this.activityEdit = eav;
	}

	@Override
	public void onClick(ClickEvent event) {
		modal.hide();
		fireEvent();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onClose(CloseEvent event) {
		fireEvent();
	}
	
	private void fireEvent() {
		if (activityEdit != null) {
			modal.setCasualId(activityEdit.getCasualId());
		}
		modal.clearContent();
	}

}
