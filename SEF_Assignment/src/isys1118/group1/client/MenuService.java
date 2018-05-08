package isys1118.group1.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import isys1118.group1.shared.view.View;

@RemoteServiceRelativePath("test")
public interface MenuService extends RemoteService {
	public View getMenuOption(String option);
}
