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
package com.lisedex.volinfoman.shared;

import java.io.Serializable;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;

/**
 * Confirmation code object.  This is used when a user account is created,
 * and we send an email with the confirmation code.  When the user visits
 * the confirmation page with the code, we match it to the user, and mark
 * the user as confirmed
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
@SuppressWarnings("serial")
@Cached
public class ConfirmationCode implements Serializable {
	@Id
	private Long id;
	
	@Indexed
	private String username;
	
	@Indexed
	private String code;
	
	@Indexed
	private long expires;
	
	/**
	 * Default constructor
	 */
	public ConfirmationCode() {
	}

	/**
	 * Constructor
	 * @param id Datastore primary key
	 * @param username username associated with code
	 * @param code confirmation code associated with username
	 * @param expires expiration date for code in milliseconds
	 */
	public ConfirmationCode(Long id, String username, String code, long expires) {
		setId(id);
		setUsername(username);
		setCode(code);
		setExpires(expires);
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the expires
	 */
	public long getExpires() {
		return expires;
	}

	/**
	 * @param expires the expires to set
	 */
	public void setExpires(long expires) {
		this.expires = expires;
	}
	
}
