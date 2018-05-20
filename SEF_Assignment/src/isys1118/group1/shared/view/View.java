package isys1118.group1.shared.view;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class View implements IsSerializable {
	
	public View() {}
	
	public abstract void setView();
	
	public abstract void show();
	
	protected void clearMain() {
		RootPanel titleRoot = RootPanel.get("title");
		RootPanel contentRoot = RootPanel.get("content");
		if (titleRoot == null) {
			// TODO throw error
			return;
		}
		if (contentRoot == null) {
			// TODO throw error
			return;
		}
		titleRoot.clear(true);
		contentRoot.clear(true);
	}
	
	public static void clearMenu() {
		RootPanel menu = RootPanel.get("menu");
		if (menu == null) {
			// TODO throw error
			return;
		}
		menu.clear(true);
	}
	
	public static void clearContent() {
		RootPanel content = RootPanel.get("content");
		RootPanel title = RootPanel.get("title");
		if (content == null || title == null) {
			// TODO throw error
			return;
		}
		content.clear(true);
		title.clear(true);
	}
	
	public static void clearLogin() {
		RootPanel login = RootPanel.get("login");
		if (login == null) {
			// TODO throw error
			return;
		}
		login.clear(true);
	}
	
}
