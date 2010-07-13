/**
 *    Copyright 2010 John Schutz <john@lisedex.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 **/

package com.lisedex.volinfoman.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.RootPanel;
import com.lisedex.volinfoman.client.gin.VolinfomanGinjector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Volinfoman implements EntryPoint {
	/**
	 * Note, we defer all application initialization code to
	 * {@link #onModuleLoad2()} so that the UncaughtExceptionHandler
	 * can catch any unexpected exceptions.
	 */
	public void onModuleLoad() {
		/* Install an UncaughtExceptionHandler which will
		 * produce <code>FATAL</code> log messages
		 */
		Log.setUncaughtExceptionHandler();

		/* Use a deferred command so that the UncaughtExceptionHandler
		 * catches any exceptions in onModuleLoad2()
		 */
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				onModuleLoad2();
			}
		});
	}

	/**
	 * As Log.setUncaughtExceptionHandler() needs to finish before
	 * we continue initialization, it calls onModuleLoad2() as a 
	 * DeferredCommand.  The rest of my code for onModuleLoad()
	 * goes here.
	 */
	private void onModuleLoad2() {
		Log.debug("in onModuleLoad2");
		
		// Create a ginjector
		VolinfomanGinjector ginjector = GWT.create(VolinfomanGinjector.class);
		// Add the homepage to the rootpanel
		RootPanel.get().add(ginjector.getHomepage());
		
		Log.debug("out onModuleLoad2");
	}
}
