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

import com.lisedex.volinfoman.shared.ConfirmationCode;
import com.lisedex.volinfoman.shared.User;

/**
 * Data Access Object interface for datastore interactions
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
public interface Dao {

	/**
	 * Gets a User object from the datastore where the username field is username
	 * 
	 * @param username the username to filter on in the User objects in the datastore
	 * @return User object from the datastore, or null if the username does not exist
	 */
	public abstract User getUser(String username);

	/**
	 * Gets or creates a User object from the datastore where the username field is username.
	 * If there is no object with the specified username, create one and put 
	 * it back in the datastore.
	 * 
	 * @param username the username to filter on in the User objects in the datastore
	 * @return User object from the datastore, or a new User object if the username does
	 * 		   not exist
	 */
	public abstract User getOrCreateUser(String username);

	/**
	 * Write User to datastore
	 * @param user User to be written
	 */
	public abstract void putUser(User user);

	/**
	 * Delete all users in datastore.
	 */
	public abstract void deleteAllUsers();
	
	/**
	 * Change a specified user's password, writing the modified user to the datastore
	 * 
	 * @param username username of the User to change
	 * @param password password to change to
	 */
	public abstract void changeUserPassword(User user, String password);
	
	/**
	 * Check the password provided against the User stored in the datastore under
	 * the specified username
	 * 
	 * @param username username field of the User object to check the password against
	 * @param password password to check
	 * @return true if password is a match, false otherwise
	 */
	public abstract boolean checkUserPassword(String username, String password);

	/**
	 * Delete user from datastore by id number
	 * 
	 * @param id id of user to delete
	 */
	public abstract void deleteUser(Long id);

	/**
	 * Store confirmation code in database
	 * @param code confirmation code object to store
	 */
	public abstract void putConfirmationCode(ConfirmationCode code);

	/**
	 * Delete all confirmation codes in the datastore
	 */
	public abstract void deleteAllConfirmationCodes();
}