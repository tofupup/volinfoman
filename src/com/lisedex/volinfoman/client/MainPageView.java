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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author John Schutz <john@lisedex.com>
 * 
 */
public class MainPageView extends Composite implements
		MainPagePresenter.MainPageViewInterface {

	private static MainPageViewUiBinder uiBinder = GWT
			.create(MainPageViewUiBinder.class);

	interface MainPageViewUiBinder extends UiBinder<Widget, MainPageView> {
	}

	@UiField
	SimplePanel contentPanel;

	public MainPageView() {
		Log.debug("MainPageView()");
		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi
	}

	public void setContent(Widget w) {
		contentPanel.setWidget(w);
	}
}
