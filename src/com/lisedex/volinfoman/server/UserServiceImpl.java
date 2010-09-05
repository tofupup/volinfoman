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
package com.lisedex.volinfoman.server;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.lisedex.volinfoman.client.data.UserService;

/**
 * Implementation of the UserService interface for getting User information from
 * the datastore
 * 
 * @author John Schutz <john@lisedex.com>
 * 
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class
			.getName());

	@Inject
	private Dao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lisedex.volinfoman.client.data.UserService#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated(final String sessionId) {
		if (validateSessionId(sessionId)) {
			return SessionHandler.isAuthenticated(getSession());
		} else {
			LOG.severe("Possible CSRF.  JSESSIONID cookie does not match included session id");
			return false;
		}
	}

	private HttpSession getSession() {
		return this.getThreadLocalRequest().getSession();
	}

	private boolean validateSessionId(String sessionId) {
		String headerSessionId = (String) getSession().getId();
		if (sessionId != null && headerSessionId != null
				&& headerSessionId.equals(sessionId)) {
			return true;
		}
		return false;
	}
}
