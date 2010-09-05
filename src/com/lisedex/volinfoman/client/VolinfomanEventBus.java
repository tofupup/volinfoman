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

import com.lisedex.volinfoman.client.gin.VolinfomanModule;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

/**
 * Configure the application event bus for mvp4g.  All events
 * in the application come through here to be dispatched to 
 * their handlers.
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
// startView - what view does the application start on
// historyOnStart - do we want history enabled
// ginModule - gin configuration module
// logLevel - DETAILED or SIMPLE
// logger - what class to send event log messages through
@Events(startView = MainPageView.class, historyOnStart = true, ginModules=VolinfomanModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = Mvp4gLoggerToGwtLogAdapter.class )
public interface VolinfomanEventBus extends EventBus {
	
	/**
	 * Log the user in
	 */
//	@Event(handlers = LoginPresenter.class)
//	public void login();
//	
//	@Event(handlers = LoginPresenter.class)
//	public void loadHomepage();
}
