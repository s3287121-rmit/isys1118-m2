package isys1118.group1.client.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FormPanel;

public class SubmitActivityHandler implements ClickHandler {
	
	private final FormPanel form;
	
	public SubmitActivityHandler(FormPanel fp) {
		this.form = fp;
	}

	@Override
	public void onClick(ClickEvent event) {
		form.submit();
	}

}
