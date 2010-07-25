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
package com.lisedex.volinfoman.client.widgets;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Implementation of a widget that contains username and password fields, 
 * and a Login button, all on one line.  Will produce click events by 
 * registering the requester to the login button.  setFocus() and selectAll()
 * work on the username field.  setEnabled() works on username, password, 
 * and loginButton.
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
public class CompactLoginWidget extends Composite implements HasClickHandlers {

	private static CompactLoginWidgetUiBinder uiBinder = GWT
			.create(CompactLoginWidgetUiBinder.class);

	interface CompactLoginWidgetUiBinder extends
			UiBinder<Widget, CompactLoginWidget> {
	}

	@UiField
	TextBox username;
	
	@UiField
	PasswordTextBox password;
	
	@UiField
	Button loginButton;
	
	public CompactLoginWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// if the user presses enter, we set focus on the password
		// field
		username.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				Log.debug("compactloginwidget password keyhandler fired.  event=" + event.toDebugString());
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					DeferredCommand.addCommand(new Command() {
						public void execute() {
							password.setFocus(true);
							password.selectAll();
						}
					});

				}
			}
		});
		
		// if the user presses enter, it's the same as pressing
		// login
		password.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				Log.debug("compactloginwidget password keyhandler fired.  event=" + event.toDebugString());
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					loginButton.click();
				}
			}
		});
	}

	/**
	 * Sets the focus on the username field
	 * @param focus true means it has focus
	 */
	public void setFocus(boolean focus) {
		username.setFocus(focus);
	}
	
	/**
	 * @return the text from the username field 
	 */
	public String getUsername() {
		return username.getText();
	}
	
	/**
	 * 
	 * @return the text from the password field
	 */
	public String getPassword() {
		return password.getText();
	}
	
	/**
	 * setEnabled controls username, password, and login button
	 * @param enabled true means on, false means off
	 */
	public void setEnabled(boolean enabled) {
		loginButton.setEnabled(enabled);
		username.setEnabled(enabled);
		password.setEnabled(enabled);
	}
	
	/**
	 * Selects all text in the username field
	 */
	public void selectAll() {
		username.selectAll();
	}
	
	/**
	 * The login button produces our click events
	 */
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
	 */
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return loginButton.addClickHandler(handler);
	}
}
