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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lisedex.volinfoman.client.data.UserServiceAsync;
import com.lisedex.volinfoman.shared.User;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * Login page Presenter, which provides the logic behind the LoginView,
 * binding events from actions on the View to the event bus.  It also 
 * defines handlers for events it wants to watch on the event bus.
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
@Presenter(view = LoginView.class)
public class LoginPresenter extends BasePresenter<LoginPresenter.LoginViewInterface, 
								VolinfomanEventBus> {
	
	/**
	 * Defines the interface for communications between the Presenter
	 * and View.  Answers "what functionality do I need from my View?"
	 * 
	 * @author John Schutz <john@lisedex.com>
	 *
	 */
	public interface LoginViewInterface {
		
		/**
		 * Get the username entered in the View
		 * @return the username
		 */
		public String getUsername();
		
		/**
		 * Get the password entered in the View
		 * @return the password
		 */
		public String getPassword();
		
		/**
		 * Set the message we want the user to see
		 * @param msg the message to be sent
		 */
		public void setMessage(String msg);
		
		/**
		 * Get a reference to something that sends an event when the
		 * user clicks, generally a button for logging in
		 * @return reference to something that sends Click events
		 */
		public HasClickHandlers getLoginButton();
		
		/**
		 * Set whether the login button is enabled
		 * @param enabled true to enable, false to disable
		 */
		public void setLoginButtonEnabled(boolean enabled);
	}

	// userService for talking to our user servlet via RPC
	private UserServiceAsync userService = null;

	/**
	 * Set the user RPC handler.  Injected via mvp4g
	 * 
	 * @param userService reference to our user RPC service 
	 */
	@InjectService
	public void setService(UserServiceAsync service) {
		this.userService = service;
	}
	
	/* (non-Javadoc)
	 * @see com.mvp4g.client.event.BaseEventHandler#bind()
	 */
	@Override
	public void bind() {
		super.bind();
		
		// throw the login event onto the event bus when LoginButton 
		// is clicked
		view.getLoginButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.setLoginButtonEnabled(false);
				eventBus.login();
			}
		});
	}
	
	/**
	 * Handle an event to log in the user
	 */
	public void onLogin() {
		Log.debug("Got login event");
		
		// if the injection failed for some reason, that's pretty bad
		if (userService == null) {
			Log.fatal("userService is null, not injected", 
					new NullPointerException("LoginPresenter.userService"));
			view.setMessage("Fatal application error.  \"userService not injected.\"");
			view.setLoginButtonEnabled(true);
			return;
		}
		
		// use RPC to get the user from the server
		userService.getUser(view.getUsername(), new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				Log.debug("got RPC failure.  exception=" + caught.getMessage());
				view.setMessage("RPC FAILED");
				view.setLoginButtonEnabled(true);
			}

			@Override
			public void onSuccess(User result) {
				if (result == null) {
					Log.debug("got RPC success.  user=null");
					view.setMessage("NO SUCH USER");
				} else {
					Log.debug("got RPC success.  user=" + result.toString());
					view.setMessage("SUCCESS: " + result.toString());
				}
				view.setLoginButtonEnabled(true);
			}
		});
	}
}
