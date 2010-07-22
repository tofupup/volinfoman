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

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;
import com.lisedex.volinfoman.shared.User;

/**
 * Google App Engine specific implementation of the Dao interface
 * 
 * @author John Schutz <john@lisedex.com>
 */
public class DaoGaeDatastore extends DAOBase implements Dao {

	// register entity classes with Objectify
	static {
		ObjectifyService.register(User.class);
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String username) {
		User fetched = ofy().query(User.class).filter("username", username).get();
		return fetched;		
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#getOrCreateUser(java.lang.String)
	 */
	@Override
	public User getOrCreateUser(String username) {
		User fetched = ofy().query(User.class).filter("username", username).get();
		if (fetched == null) {
			fetched = new User(null, username, User.STATUS_INVALID, null, null, null, null);
			ofy().put(fetched);
		}
		return fetched;
	}
	
	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#putUser(com.lisedex.volinfoman.shared.User)
	 */
	@Override
	public void putUser(User user) {
		ofy().put(user);
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#deleteAllUsers()
	 */
	@Override
	public void deleteAllUsers() {
		ofy().delete(ofy().query(User.class).fetchKeys());
	}
}
