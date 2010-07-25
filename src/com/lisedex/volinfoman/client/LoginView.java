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
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.lisedex.volinfoman.client.widgets.LongLoginWidget;

/**
 * Login page View
 * 
 * Defines the fields used in the associated LoginView.ui.xml file,
 * and implementations of the methods defined as needed in the 
 * LoginPresenter class
 * 
 * @author John Schutz <john@lisedex.com>
 */
public class LoginView extends Composite 
	implements LoginPresenter.LoginViewInterface {

    /*
     * Create UiBinder
     */
	private static LoginViewUiBinder uiBinder = GWT
		.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	// fields filled by the UiBinder cannot be private

	@UiField
	LongLoginWidget loginWidget;
	
	@UiField
	HTML sendStatus;
	
	/**
	 * Construct the Login page View, setting up the objects' initial
	 * states
	 */
	public LoginView() {
		Log.debug("in constructor");
		
		initWidget(uiBinder.createAndBindUi(this));
				
		sendStatus.setStyleName("serverResponseLabelError");
		
		// set focus in a deferred command, since it doesn't work otherwise
		// see gwt issue 1849
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				loginWidget.setFocus(true);
				loginWidget.selectAll();
			}
		});
		
		Log.debug("out constructor");
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.LoginPresenter.LoginViewInterface#getUsername()
	 */
	@Override
	public String getUsername() {
		return loginWidget.getUsername();
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.LoginPresenter.LoginViewInterface#getPassword()
	 */
	@Override
	public String getPassword() {
		return loginWidget.getPassword();
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.LoginPresenter.LoginViewInterface#setMessage(java.lang.String)
	 */
	@Override
	public void setMessage(String msg) {
		sendStatus.setText(msg);
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.LoginPresenter.LoginViewInterface#getLoginButton()
	 */
	@Override
	public HasClickHandlers getLoginButton() {
		return loginWidget;
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.LoginPresenter.LoginViewInterface#setLoginButtonEnabled(boolean)
	 */
	@Override
	public void setLoginButtonEnabled(boolean enabled) {
		loginWidget.setEnabled(enabled);
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.LoginPresenter.LoginViewInterface#tryAgain()
	 */
	@Override
	public void tryAgain() {
		loginWidget.setEnabled(true);
		loginWidget.setFocus(true);
		loginWidget.selectAll();
	}
}
