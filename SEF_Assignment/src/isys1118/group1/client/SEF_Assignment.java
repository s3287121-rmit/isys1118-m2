package isys1118.group1.client;

import isys1118.group1.shared.view.LoginView;
import isys1118.group1.shared.view.MenuView;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SEF_Assignment implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		/*
		MenuView vm = new MenuView();
		vm.setView();
		vm.show();
		*/
		
		LoginView view = new LoginView();
		view.setView();
		view.show();
		
	}
}
