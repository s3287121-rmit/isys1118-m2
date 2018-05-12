package isys1118.group1.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import isys1118.group1.shared.EditActivityInputs;

@RemoteServiceRelativePath("submit")
public interface SubmitActivityService extends RemoteService {

	public EditActivityInputs submit(String activityId, EditActivityInputs inputs) throws Exception;
	
}
