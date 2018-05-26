package isys1118.group1.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import isys1118.group1.client.SubmitActivityService;
import isys1118.group1.server.database.Database;
import isys1118.group1.server.database.Row;
import isys1118.group1.server.database.Table;
import isys1118.group1.server.helpers.ValidateCasualInput;
import isys1118.group1.shared.EditActivityInputs;

@SuppressWarnings("serial")
public class SubmitActivityServiceImpl extends RemoteServiceServlet implements SubmitActivityService {

	@Override
	public EditActivityInputs submit(String activityId, EditActivityInputs inputs) throws Exception {

		// check for errors
		String[] errors = ValidateCasualInput.validateInputsServer(
				inputs.type,
				inputs.day,
				inputs.timeh,
				inputs.timem,
				inputs.durm,
				inputs.casual,
				activityId,
				inputs.courseid);
		
		// if there are errors, pass back the problem
		EditActivityInputs toSubmit;
		if (errors != null && errors.length > 0) {
			toSubmit = EditActivityInputs.createServer(errors);
			// But only if the errors were not acceptable!
			if (!toSubmit.success) {
				return toSubmit;
			}
		}
		
		// handle casual assignment only if a casual has been added.
		if (inputs.casual != null && !inputs.casual.trim().isEmpty()) {
			
			// add a row to the assignments database
			String[] casualData = inputs.casual.split(" ");
			
			// create new assignment if one does not already exist.
			Table allAssignments
			= Database.getDatabase().getFullTable("assignments");
			Row checkAlreadyExists = allAssignments.getRowEquals(
					"activityid", activityId);
			if (checkAlreadyExists == null) {
				System.out.println("New Assignment added for userid=" + casualData[0] + " and activityid=" + activityId);
				allAssignments.createNewRow(new String[] {casualData[0], activityId});
				allAssignments.commitChanges();
			}
			// otherwise just update
			else {
				checkAlreadyExists.setColumn("userid", casualData[0]);
				allAssignments.commitChanges();
			}
			
		}
		// TODO check for casual removal and remove assignment to match.
		
		// update activity info. If the row already exists, then update it.
		// Otherwise, create a new row.
		Table allActivities = Database.getDatabase().getFullTable("activities");
		Row activityExists = allActivities.getRowEquals("activityid", activityId);
		String[] rowData = new String[] {
				activityId,
				inputs.type,
				inputs.day,
				inputs.timeh,
				inputs.timem,
				inputs.durm,
				inputs.courseid
		};
		if (activityExists != null) {
			activityExists.setData(rowData);
		}
		else {
			activityExists = allActivities.createNewRow(rowData);
		}
		allActivities.commitChanges();
		
		// return with a success message
		return EditActivityInputs.createServer(null);
	}
	
}
