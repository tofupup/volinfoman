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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.lisedex.volinfoman.client.data.UserServiceAsync;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author John Schutz <john@lisedex.com>
 * 
 */
@Presenter(view = MainPageView.class)
public class MainPagePresenter
		extends
		BasePresenter<MainPagePresenter.MainPageViewInterface, VolinfomanEventBus> {

	/**
	 * @author John Schutz <john@lisedex.com>
	 *
	 */
	public interface MainPageViewInterface {
		public void setContent(Widget w);
	}

	private static final String LOADING_MESSAGE = "<h2>Loading...</h2>";
	private static final String RPC_FAILURE_MESSAGE = "<h2>Failed to contact login server.  Please try again.</h2>";
	private static final String NOT_AUTHENTICATED_MESSAGE = "<h2>You have not logged in.  Redirecting to login page...</h2>";
//	private static final String HOME_PAGE_URL = "http://lisedexvolinfomantest.appspot.com";
	private static final String HOME_PAGE_URL = "/index.html";
	
	private UserServiceAsync userService = null;
	
	@InjectService
	public void setService(UserServiceAsync service) {
		Log.debug("setService() " + service);
		this.userService = service;
	}
	
	public MainPagePresenter() {
		Log.debug("constructor()");
	}
	
	@Override
	public void bind() {
		Log.debug("bind()");
		view.setContent(new HTMLPanel(LOADING_MESSAGE));
		
		userService.isAuthenticated(new AsyncCallback<Boolean>() {	
			@Override
			public void onSuccess(Boolean result) {
				if (result.booleanValue() == true) {
					view.setContent(new HTMLPanel("<h1>AUTHENTICATED</h1>"));
				} else {
					view.setContent(new HTMLPanel(NOT_AUTHENTICATED_MESSAGE));
					Window.Location.replace(HOME_PAGE_URL);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				view.setContent(new HTMLPanel(RPC_FAILURE_MESSAGE));	
			}
		});
	}
}
