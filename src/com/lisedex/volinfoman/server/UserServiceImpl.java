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

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.googlecode.objectify.helper.DAOBase;
import com.lisedex.volinfoman.client.data.UserService;
import com.lisedex.volinfoman.shared.User;

/**
 * @author John Schutz <john@lisedex.com>
 *
 */
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    @Inject
    private Dao dao;
    
	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.data.UserService#getUser(java.lang.Long)
	 */
	public User getUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.data.UserService#getUser(java.lang.String)
	 */
	public User getUser(String username) {
		log.info("getUser(String)");
		return dao.getUser(username); 
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.client.data.UserService#putUser(com.lisedex.volinfoman.shared.User)
	 */
	public void putUser(User user) {
		// TODO Auto-generated method stub

	}

}
