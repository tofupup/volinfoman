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
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author John Schutz <john@lisedex.com>
 *
 */
public class DefaultHomepage extends Homepage {

	private static DefaultHomepageUiBinder uiBinder = GWT
	.create(DefaultHomepageUiBinder.class);

	interface DefaultHomepageUiBinder extends UiBinder<Widget, DefaultHomepage> {
	}

	@UiField
	TextBox username;

	@UiField
	PasswordTextBox password;

	@UiField
	Button sendButton;

	@UiField
	Label sendStatus;

	public DefaultHomepage() {
		Log.debug("in constructor");

		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi

		sendButton.setText("Login");
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				sendStatus.setText("Sending..." + username.getText() + 
						"/" + password.getText());
			}
		});

		username.setFocus(true);
		username.selectAll();

		Log.debug("out constructor");
	}
}
