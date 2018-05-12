package isys1118.group1.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.client.MenuService;
import isys1118.group1.client.MenuServiceAsync;
import isys1118.group1.shared.view.View;
import isys1118.group1.shared.view.ViewSerial;

public class ResetDatabaseHandler implements ClickHandler {
	
	private MenuServiceAsync msa;
	private final String type;
	private final String id;
	
	public ResetDatabaseHandler(String type, String id) {
		this.type = type;
		this.id = id;
	}

	@Override
	public void onClick(ClickEvent event) {
		msa = GWT.create(MenuService.class);
		msa.resetDatabase(type, id, new AsyncCallback<ViewSerial>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("There was a problem resetting the database!");
			}

			@Override
			public void onSuccess(ViewSerial result) {
				if (result.menu != null) {
					result.menu.show();
				}
				else {
					View.clearMenu();
				}
				if (result.content != null) {
					result.content.show();
				}
				else {
					View.clearContent();
				}
				Window.alert("Database reset!");
			}
			
		});
	}

}
