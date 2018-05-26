package isys1118.group1.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Sends data from the server to an ActivityEdit page.
 */
public class CasualInfo implements IsSerializable {
	
	public String casualId;
	public String casualName;
	public String casualRate;
	public String[] casualCourses;
	public String casualConflict;
	public boolean noCasual;
	
	public void setAll(String casualId, String casualName, String casualRate,
			String[] casualCourses, String casualConflict, boolean noCasual) {
		this.casualId = casualId;
		this.casualName = casualName;
		this.casualRate = casualRate;
		this.casualCourses = casualCourses;
		this.casualConflict = casualConflict;
		this.noCasual = noCasual;
	}
	
	public static CasualInfo create(String casualId, String casualName, String casualRate,
			String[] casualCourses, String casualConflict, boolean noCasual) {
		CasualInfo ci = new CasualInfo();
		ci.setAll(casualId, casualName, casualRate,
			casualCourses, casualConflict, noCasual);
		return ci;
	}
	
}
