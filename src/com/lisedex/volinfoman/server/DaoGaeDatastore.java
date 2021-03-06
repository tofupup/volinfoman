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

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;
import com.lisedex.volinfoman.server.util.BCrypt;
import com.lisedex.volinfoman.shared.ConfirmationCode;
import com.lisedex.volinfoman.shared.User;

/**
 * Google App Engine specific implementation of the Dao interface
 * 
 * @author John Schutz <john@lisedex.com>
 */
public class DaoGaeDatastore extends DAOBase implements Dao {

    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(DaoGaeDatastore.class.getName());
    
	// register entity classes with Objectify
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(ConfirmationCode.class);
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
	
	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#changeUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void changeUserPassword(User user, String password) {
		if (user == null) return;
		
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(hashed);
		putUser(user);
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#checkUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkUserPassword(String username, String password) {
		if (username == null || password == null) {
			return false;
		}
		
		User user = getUser(username);
		if (user == null || user.getPassword() == null) {
			return false;
		}
		
		return BCrypt.checkpw(password, user.getPassword());
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#deleteUser(com.lisedex.volinfoman.shared.User)
	 */
	@Override
	public void deleteUser(Long id) {
		ofy().delete(User.class, id.longValue()); 
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#putConfirmationCode(com.lisedex.volinfoman.shared.ConfirmationCode)
	 */
	@Override
	public void putConfirmationCode(ConfirmationCode code) {
		ofy().put(code);		
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#deleteAllConfirmationCodes()
	 */
	@Override
	public void deleteAllConfirmationCodes() {
		ofy().delete(ofy().query(ConfirmationCode.class).fetchKeys());
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#expireCodesBefore(java.util.Calendar)
	 */
	@Override
	public void expireCodesBefore(long now) {
		Query<ConfirmationCode> oldCodes = ofy().query(ConfirmationCode.class).filter("expires <", now);
		for (ConfirmationCode code: oldCodes) {

			User user = getUser(code.getUsername());
			if (user != null) {
				// make sure user is still in unconfirmed state
				if (user.getStatus() == User.STATUS_UNCONFIRMED) {
					deleteUser(user.getId());
				}
			}
			
			deleteConfirmationCode(code);
		}
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#getConfirmationCode(java.lang.String)
	 */
	@Override
	public ConfirmationCode getConfirmationCode(String code) {
		ConfirmationCode fetched = ofy().query(ConfirmationCode.class).filter("code", code).get();
		return fetched;
	}

	/* (non-Javadoc)
	 * @see com.lisedex.volinfoman.server.Dao#deleteConfirmationCode(com.lisedex.volinfoman.shared.ConfirmationCode)
	 */
	@Override
	public void deleteConfirmationCode(ConfirmationCode code) {
		if (code != null) {
			ofy().delete(code);
		}
	}
}
