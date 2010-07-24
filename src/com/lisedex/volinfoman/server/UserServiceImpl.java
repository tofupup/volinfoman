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
import com.lisedex.volinfoman.server.util.Session;
import com.lisedex.volinfoman.shared.User;

/**
 * Implementation of the UserService interface for getting User information
 * from the datastore
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());

    @Inject
    private Dao dao;
	
	/*
	 * (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.data.UserService#checkUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkUserPassword(String username, String password) {
		LOG.info("checkUserPassword(" + username + ", " + password + ")");
		return dao.checkUserPassword(username, password);
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.data.UserService#authenticateUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticateUser(String username, String password) {
		HttpSession session = this.getThreadLocalRequest().getSession();

		LOG.info("authenticateUser(" + username + ", " + password + ")");
		if (username == null || password == null) {
			LOG.info("authenticateUser: login failed, username or password null");
			return false;
		}
		
		if (!checkUserPassword(username, password)) {
			LOG.info("authenticateUser: login failed, username does not exist, or "
					+ "password does not match stored in datastore");
			return false;
		}
		
		session.setAttribute(Session.AUTHENTICATEDUSER, username);
		LOG.info("authenticateUser: login succeeded.  session = " + session.getId());
		return true;
	}
}
