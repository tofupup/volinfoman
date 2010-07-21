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
import com.mvp4g.client.event.Mvp4gLogger;

/**
 * Provides a connection between Mvp4g's internal logging for the event
 * bus and gwt-log, which we use for logging.
 * 
 * @author John Schutz <john@lisedex.com>
 */
public class Mvp4gLoggerToGwtLogAdapter implements Mvp4gLogger {
	/**
	 * Array of strings to provide quick indentation for log message 
	 */
	final static private String INDENTS[] = {"", "  ", "    ", "      ", "        ", "          "};
	
	/**
	 * Takes log message, applies indentation based on depth, and passes
	 * it on to gwt-log
	 * @param messages Message to log
	 * @param depth How much to indent message
	 */
	public void log( String message, int depth ) {
    	if (depth >= INDENTS.length) {
    		depth = INDENTS.length - 1;
    	}
        Log.debug( INDENTS[depth] + "Mvp4gLogMsg: " + message );
    }
}
