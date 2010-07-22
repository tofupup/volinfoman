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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lisedex.volinfoman.shared.User;

/**
 * 
 * Marker interface for User related RPC services
 * 
 * @author John Schutz <john@lisedex.com>
 */
@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
	/**
	 * Get user by id field
	 * @param id Id field in datastore
	 * @return User object with specified id
	 */
	User getUser(Long id);
	
	/**
	 * Get user by username
	 * @param username username field in datastore
	 * @return User object with specified username
	 */
	User getUser(String username);
	
	/**
	 * Write user to datastore
	 * @param user User object to be written
	 */
	void putUser(User user);
}
