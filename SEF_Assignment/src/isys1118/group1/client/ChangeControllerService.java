package isys1118.group1.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import isys1118.group1.shared.error.PermissionException;
import isys1118.group1.shared.view.ViewSerial;

@RemoteServiceRelativePath("change")
public interface ChangeControllerService extends RemoteService {

	public ViewSerial changeController(String type, String id)
			throws Exception, PermissionException;

	public ViewSerial addActivity(String courseId)
			throws Exception, PermissionException;
	
}
