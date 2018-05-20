package isys1118.group1.server.model;

import java.util.ArrayList;

import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.helpers.Course;

public class CourseListModel extends Model {
	
	private static final int ARRAY_LEN = 4;
	
	private ArrayList<Course> courses = new ArrayList<Course>();
	
	public void setCoursesFromUser(String userId) {
		// TODO get courses that are only accessible to user
		try {
			Table courses = Database.getDatabase().getFullTable("courses");
			for (int i = 0; i < courses.getNumRows(); i++) {
				addCourse(courses.getRowIndex(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[][] getCoursesForView() {
		String[][] courseArr = new String[courses.size()][ARRAY_LEN];
		
		for (int i = 0; i < courses.size(); i++) {
			Course c = courses.get(i);
			courseArr[i][0] = c.getCourseId();
			courseArr[i][1] = c.getCourseName();
			String fullDesc = c.getDescription();
			String shortDesc = reduceString(fullDesc);
			courseArr[i][2] = shortDesc;
			courseArr[i][3] = c.getStatus();
		}
		
		return courseArr;
	}
	
	private String reduceString(String src) {
		String ret = src.replaceAll("<p>", "")
						.replaceAll("</p>", " ")
						.trim();
		if (ret.length() > 100) {
			return ret.substring(0, 100) + "...";
		}
		else {
			return ret;
		}
	}
	
	private void addCourse(Row row) {
		Course course = new Course();
		course.setFromRow(row);
		courses.add(course);
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}

}
