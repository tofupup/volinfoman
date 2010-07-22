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
package com.lisedex.volinfoman.client.data;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lisedex.volinfoman.shared.User;

/**
 * Marker interface for async User related RPC services
 * 
 * @author John Schutz <john@lisedex.com>
 */
public interface UserServiceAsync {

	/**
	 * Get user by id field
	 * @param id Id field in datastore
	 * @param callback executed when RPC returns
	 */
	void getUser(Long id, AsyncCallback<User> callback);

	/** Get user by username
	 * 
	 * @param username username field in datastore
	 * @param callback executed when RPC returns
	 */
	void getUser(String username, AsyncCallback<User> callback);

	/** 
	 * Write user to datastore
	 * @param user User object to be written
	 * @param callback executed when RPC returns
	 */
	void putUser(User user, AsyncCallback<Void> callback);
}
