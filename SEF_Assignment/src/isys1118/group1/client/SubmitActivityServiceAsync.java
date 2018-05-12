package isys1118.group1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.shared.EditActivityInputs;

public interface SubmitActivityServiceAsync {

	public void submit(String activityId, EditActivityInputs inputs, AsyncCallback<EditActivityInputs> callback);
	
}
