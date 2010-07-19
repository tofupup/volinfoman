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

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.lisedex.volinfoman.shared.User;

/**
 * General Data Access Object for VolInfoMan servlets
 * 
 * @author John Schutz <john@lisedex.com>
 */
public class DAO extends DAOBase {

	// register entity classes with Objectify
	static {
		ObjectifyService.register(User.class);
	}

	// reference to the "business end" of Objectify so we can perform
	// datastore manipulation
	private Objectify ofy;

	/**
	 * Default DAO constructor.  Gets a reference to an Objectify instance
	 * we can use for datastore manipulation.
	 */
	public DAO() {
		// Spin up an Objectify reference
		ofy = ObjectifyService.begin();	
	}

	/**
	 * Gets a User object from the datastore where the username field is username
	 * 
	 * @param username the username to filter on in the User objects in the datastore
	 * @return User object from the datastore, or null if the username does not exist
	 */
	public User getUser(String username) {
		User fetched = ofy.query(User.class).filter("username", username).get();
		return fetched;		
	}

	/**
	 * Gets or creates a User object from the datastore where the username field is username.
	 * If there is no object with the specified username, create one and put 
	 * it back in the datastore.
	 * 
	 * @param username the username to filter on in the User objects in the datastore
	 * @return User object from the datastore, or a new User object if the username does
	 * 		   not exist
	 */
	public User getOrCreateUser(String username) {
		User fetched = ofy.query(User.class).filter("username", username).get();
		if (fetched == null) {
			fetched = new User(null, username, User.STATUS_INVALID, null, null, null, null);
			ofy.put(fetched);
		}
		return fetched;
	}
	
	/**
	 * Write User to datastore
	 * @param user User to be written
	 */
	public void putUser(User user) {
		ofy.put(user);
	}
}
