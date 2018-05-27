package isys1118.group1.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import isys1118.group1.shared.CasualInfo;

@RemoteServiceRelativePath("updatecasual")
public interface UpdateCasualService extends RemoteService {
	
	/**
	 * Returns info for a single casual, using the given ID.
	 * @param casualId
	 * @return
	 */
	public CasualInfo getCasualInfo(String casualId);
	
	/**
	 * Gets all casuals in a list. This information will then be used to choose
	 * which casual gets assigned to an activity.
	 * @param courseId
	 * @param activityId
	 * @return
	 */
	public CasualInfo[] getAllAvailableCasuals(String currentCasId,
			String courseId, String activityId, String day, String starth,
			String startm, String durationm);

}
