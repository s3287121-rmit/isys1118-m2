package isys1118.group1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import isys1118.group1.shared.CasualInfo;

public interface UpdateCasualServiceAsync {
	
	public void getCasualInfo(String casualId, String courseId,
			String activityId, AsyncCallback<CasualInfo> callback);
	
	public void getAllAvailableCasuals(String currentCasId, String courseId,
			String activityId, String day, String starth, String startm,
			String durationm, AsyncCallback<CasualInfo[]> callback);

}
