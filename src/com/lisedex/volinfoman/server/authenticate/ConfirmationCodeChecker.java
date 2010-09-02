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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.lisedex.volinfoman.server.Dao;
import com.lisedex.volinfoman.shared.ConfirmationCode;
import com.lisedex.volinfoman.shared.StringSafety;
import com.lisedex.volinfoman.shared.User;

/**
 * Check a submitted confirmation code with the ConfirmationCode table in the
 * datastore, to see if we have a match with usernamee. If so, we can mark the
 * user as confirmed and they can continue to log in
 * 
 * @author John Schutz <john@lisedex.com>
 * 
 */
@SuppressWarnings("serial")
public class ConfirmationCodeChecker extends HttpServlet {
	/**
	 * 
	 */
	private static final String ACCOUNT_NEW = "Sorry, the account you're trying to confirmed has not reached the point where it can be confirmed.  Unfortunately, the only way to resolve this is to contact our support department, or <a href=\"/register.html\">apply for a new account</a>.  We apologize for the inconvenience.";

	/**
	 * 
	 */
	private static final String ACCOUNT_INVALID = "Sorry, the account you are trying to confirm is marked as invalid by the system.  The only way to rectify this is to contact our support department, or <a href=\"/register.html\">apply for a new account</a>.  We apologize for the inconvenience.";

	/**
	 * 
	 */
	private static final String ALREADY_CONFIRMED = "The account you're trying to confirm has already been confirmed.  This is good news: just jump right to our front page, log in, and get started!";

	/**
	 * 
	 */
	private static final String ACCOUNT_CLOSED = "Sorry, but the account you're trying to confirm has been marked as closed.  You can either <a href=\"/register.html\">apply for a new account</a>, or contact support to discuss why the account has been closed.";

	/**
	 * 
	 */
	private static final String BAD_CONFIRMATION_CODE = "Sorry, but your confirmation code is not valid.  It is possible that it has expired, if the email in which you received it is more than a week old.  Please <a href=\"/register.html\">apply for a new account</a>.";

	/**
	 * 
	 */
	private static final String CONFIRMATION_SUCCESS = "Thank you for confirming your VolunteerIM account information.  You are now ready to <a href=\"/\">return to the main page</a>, log in as your self, and get started!";

	@Inject
	private Dao dao;

	private static final Logger log = Logger
			.getLogger(ConfirmationCodeChecker.class.getName());

	private static final String UNSAFE_ERROR = "<span style=\"color: #ff0000;\">There is a problem with the link used to confirm your user account.  Please try clicking the link again, and if the problem continues, return to the home page and request that the email is sent again.  Or you can contact support.  Sorry for the inconvenience!</span>";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		PrintWriter output = resp.getWriter();

		// build HTML response page
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		output.println("<head><title>VolunteerIM account confirmation</title></head>");
		output.println("<body>");

		output.println("<h2>VolunteerIM account confirmation</h2><p>");

		String username = req.getParameter("username");
		String code = req.getParameter("code");

		log.info("Confirmation request with username " + username
				+ " and code " + code);

		if (!StringSafety.isSafe(username)) {
			output.println(UNSAFE_ERROR + "</body>");
			return;
		}

		if (!StringSafety.isSafe(code)) {
			output.println(UNSAFE_ERROR + "</body>");
			return;
		}

		ConfirmationCode testCode = dao.getConfirmationCode(code);
		if ((testCode == null) || (testCode.getUsername() == null)
				|| (testCode.getCode() == null)) {
			output.println(BAD_CONFIRMATION_CODE + "</body>");
			return;
		}

		User fetched = dao.getUser(testCode.getUsername());

		if (fetched.getStatus() == User.STATUS_UNCONFIRMED) {
			fetched.setStatus(User.STATUS_CONFIRMED);
			dao.putUser(fetched);

			// eliminate confirmation code from datastore, as we're
			// done with it
			dao.deleteConfirmationCode(testCode);

			output.println(CONFIRMATION_SUCCESS + "</body>");
			log.info("Confirmed username " + fetched.getUsername());
			return;
		}

		if (fetched.getStatus() == User.STATUS_CLOSED) {
			output.println(ACCOUNT_CLOSED + "</body>");
			return;
		}

		if (fetched.getStatus() == User.STATUS_CONFIRMED) {
			output.println(ALREADY_CONFIRMED + "</body>");
			return;
		}

		if (fetched.getStatus() == User.STATUS_INVALID) {
			output.println(ACCOUNT_INVALID + "</body>");
			return;
		}

		if (fetched.getStatus() == User.STATUS_NEW) {
			output.println(ACCOUNT_NEW + "</body>");
			return;
		}
	}
}
