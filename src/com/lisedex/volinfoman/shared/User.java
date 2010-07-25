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
import com.googlecode.objectify.annotation.Unindexed;

/**
 * User object, containing volunteer account information, including
 * username and password for logging in.
 * 
 * @author John Schutz <john@lisedex.com>
 */
@SuppressWarnings("serial")
@Cached
public class User implements Serializable {
	/**
	 * User object's status is invalid
	 */
	public static long STATUS_INVALID = 0;
	/**
	 * User object is new
	 */
	public static long STATUS_NEW = 1;
	/**
	 * User object is created, but has not had email confirmation
	 */
	public static long STATUS_UNCONFIRMED = 2;
	/**
	 * User object is created and has passed email confirmation
	 */
	public static long STATUS_CONFIRMED = 3;
	/**
	 * User object is closed
	 */
	public static long STATUS_CLOSED = 4;
	
	/**
	 * The index of the highest status label
	 */
	private static long STATUS_MAXNUM = 4;
	/**
	 * The index of the lowest status label
	 */
	private static long STATUS_MINNUM = 0;
	/**
	 * Array of names for status labels, used for toString()
	 */
	private static String[] statusNames = {"INVALID", "NEW", "UNCONFIRMED", "CONFIRMED", "CLOSED" };

	/**
	 * Primary Id key for the datastore
	 */
	@Id
	private Long id;
	/**
	 * Username of the user, used for logins
	 */
	@Indexed
	private String username;
	/**
	 * User's status as defined by STATUS_* constants
	 */
	@Indexed
	private long status;
	/**
	 * User's first name
	 */
	@Unindexed
	private String firstName;
	/**
	 * User's last name
	 */
	@Unindexed
	private String lastName;
	/**
	 * User's email address
	 */
	@Unindexed
	private String email;
	/**
	 * User's password
	 */
	@Unindexed
	private String password;

	/**
	 * Default User constructor
	 */
	public User() {
	}

	/**
	 * User constructor with fields specified
	 * @param id Datastore primary key
	 * @param username User's username
	 * @param status User's status, as defined by User constants STATUS_*
	 * @param firstName User's first name
	 * @param lastName User's last name
	 * @param email User's email address
	 * @param password User's password
	 */
	public User(Long id, String username, long status, String firstName, 
			String lastName, String email, String password) {
		this.setId(id);
		this.username = username;
		this.setStatus(status);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the User's status.  If the status provided is not in the proper
	 * range, set the status to User.STATUS_INVALID
	 * 
	 * @param status the status to set
	 */
	public void setStatus(long status) {
		if ((status >= User.STATUS_MINNUM) && 
				(status <= User.STATUS_MAXNUM)) {
			this.status = status;
			return;
		}
		this.status = User.STATUS_INVALID;
	}

	/**
	 * @return the status
	 */
	public long getStatus() {
		return status;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return a string representation of the User
	 */
	@Override
	public String toString() {
		return getId() + "/" + username + "/" + statusNames[(int) getStatus()] + "/" + 
		firstName + "/" + lastName + "/" + email + "/" + password;
	}
}