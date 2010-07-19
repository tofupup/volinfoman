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
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.lisedex.volinfoman.client.data.UserService;
import com.lisedex.volinfoman.client.data.UserServiceAsync;
import com.lisedex.volinfoman.shared.User;

/**
 * @author John Schutz <john@lisedex.com>
 *
 */
public class DefaultHomepage extends Homepage {
    /*
     * Create UiBinder
     */
	private static DefaultHomepageUiBinder uiBinder = GWT
		.create(DefaultHomepageUiBinder.class);

	/*
	 * Create a remote service proxy to talk to server side User service
	 */
	private static UserServiceAsync userService = GWT
		.create(UserService.class);
	
	interface DefaultHomepageUiBinder extends UiBinder<Widget, DefaultHomepage> {
	}

	@UiField
	TextBox username;

	@UiField
	PasswordTextBox password;

	@UiField
	Button sendButton;

	@UiField
	HTML sendStatus;

	public DefaultHomepage() {
		Log.debug("in constructor");

		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi

		sendButton.setText("Login");
		
		class MyHandler implements ClickHandler {

			/* (non-Javadoc)
			 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
			 */
			@Override
			public void onClick(ClickEvent event) {
				sendStatus.setText("Sending..." + username.getText() + 
						"/" + password.getText());
				sendButton.setEnabled(false);
				userService.getUser(username.getText(), 
						new AsyncCallback<User>() {
							public void onFailure(Throwable caught) {
								Log.debug("got RPC failure.  exception=" + caught.toString());
								sendStatus.addStyleName("serverResponseLabelError");
								sendStatus.setText(sendStatus.getText() 
										+ "   FAILED");
								sendButton.setEnabled(true);
							}

							@Override
							public void onSuccess(User result) {
								if (result == null) {
									sendStatus.setText(sendStatus.getText() + "    NO SUCH USER");
									sendButton.setEnabled(true);
									return;
								}
								Log.debug("got RPC success.  user=" + result.toString());
								sendStatus.removeStyleName("serverResponseLabelError");
								sendStatus.setText(sendStatus.getText() 
										+ "    SUCCESS: " + result.toString());
								sendButton.setEnabled(true);
							}
				});
			}
			
		}
		
		sendButton.addClickHandler(new MyHandler());
		
		username.setFocus(true);
		username.selectAll();

		Log.debug("out constructor");
	}
}