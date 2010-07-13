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
 *
 **/
package com.lisedex.volinfoman.client;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author John Schutz <john@lisedex.com>
 *
 */
public class DefaultHomepage extends Homepage {
	private VerticalPanel loginPanel = new VerticalPanel();
	private Button button = new Button();
	
	public DefaultHomepage() {
		Log.debug("in constructor");
		
		button.setText("Button Text");
		
		loginPanel.add(button);
		initWidget(loginPanel);
		
		Log.debug("out constructor");
	}
}
