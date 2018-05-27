package isys1118.group1.server.controller;

import isys1118.group1.server.database.Row;
import isys1118.group1.shared.view.View;

public abstract class Controller {
	
	protected boolean viewShown = false;
	
	protected abstract void loadData();
	
	public abstract void show();
	
	public abstract void run();
	
	public abstract View getView();
	
	public boolean hasBeenShown() {
		return viewShown;
	}

}
