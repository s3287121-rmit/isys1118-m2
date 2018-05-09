package isys1118.group1.shared;

import java.util.HashMap;

public class Form {
	
	private HashMap<String, String> fields = new HashMap<String, String>();
	
	public void setField(String key, String value) {
		fields.put(key, value);
	}
	
	public String getFieldValue(String key) {
		return fields.get(key);
	}

}
