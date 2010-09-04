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

import javax.servlet.http.HttpSession;

/**
 * @author John Schutz <john@lisedex.com>
 * 
 */
public class SessionHandler {
	public static final String AUTHENTICATED = "authenticated";

	public static void setAuthenticated(HttpSession session,
			boolean authenticated) {
		session.setAttribute(AUTHENTICATED, authenticated);
	}

	public static boolean isAuthenticated(HttpSession session) {
		if (session == null)
			return false;

		Boolean auth = (Boolean) session.getAttribute(AUTHENTICATED);
		if (auth == null) {
			return false;
		} else {
			return auth.booleanValue();
		}
	}
}
