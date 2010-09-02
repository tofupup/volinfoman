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
package com.lisedex.volinfoman.server.authenticate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.lisedex.volinfoman.server.Dao;
import com.lisedex.volinfoman.server.util.Session;
import com.lisedex.volinfoman.shared.StringSafety;
import com.lisedex.volinfoman.shared.User;

/**
 * Authenticate user, matching username and password, and user
 * status as being confirmed.  Generates a session, and passes it back
 * to the client to be used as a cookie
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
@SuppressWarnings("serial")
public class Login extends HttpServlet {

	@Inject 
	Dao dao;
	
    private static final Logger log = Logger.getLogger(Login.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PrintWriter output = resp.getWriter();

		// build HTML response page
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if ((username == null) || (password == null)) {
			output.println("<head><title>VolunteerIM login</title></head>");
			output.println("<body>Please fill out both username and password fields.</body>");
			return;
		}
		
		if (!StringSafety.isSafe(username)) {
			output.println("<head><title>VolunteerIM login</title></head>");
			output.println("<body>Username invalid, please go back and try again.</body>");
			return;
		}
		
		if (!StringSafety.isSafe(password)) {
			output.println("<head><title>VolunteerIM login</title></head>");
			output.println("<body>Password invalid, please go back and try again.</body>");
			return;
		}
		
		if (dao.checkUserPassword(username, password)) {
			if (dao.getUser(username).getStatus() == User.STATUS_CONFIRMED) {
				HttpSession session = req.getSession();
				resp.sendRedirect(resp.encodeRedirectURL("/Volinfoman.html"));
				session.setAttribute(Session.AUTHENTICATEDUSER, username);
				return;
			} else {
				output.println("<head><title>VolunteerIM login</title></head>");
				output.println("<body>Sorry, your account has not yet been confirmed.  Please check the email account you used during sign up for a link you must visit to confirm your account.  Thank you!</body>");
				return;
			}
		} else {
			output.println("<head><title>VolunteerIM login</title></head>");
			output.println("<body>Username and password do not match.  Please try again.</body>");
			return;
		}
	}
}
