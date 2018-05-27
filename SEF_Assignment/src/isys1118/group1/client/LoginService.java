package isys1118.group1.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import isys1118.group1.shared.view.ViewSerial;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	public ViewSerial login(String username, String password);
	
}
