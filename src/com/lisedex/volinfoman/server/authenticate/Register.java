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
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.lisedex.volinfoman.server.Dao;
import com.lisedex.volinfoman.shared.ConfirmationCode;
import com.lisedex.volinfoman.shared.StringSafety;
import com.lisedex.volinfoman.shared.User;

/**
 * Parse data submitted by user on the registration form.  If user can be 
 * created, fire off an email with a confirmation code link they can use
 * to confirm the account.  Put this code in the ConfirmationCode table in 
 * the datastore.
 * 
 * @author John Schutz <john@lisedex.com>
 * 
 */
@SuppressWarnings("serial")
public class Register extends HttpServlet {
	/**
	 * 
	 */
	private static final String EMAIL_SUBJECT = "VolunteerIM account confirmation";

	/**
	 * 
	 */
	private static final String EMAIL_FROM_NAME = "VolunteerIM Confirmation";

	/**
	 * 
	 */
	private static final String EMAIL_FROM_ADDRESS = "admin@lisedex.com";

	/**
	 * 
	 */
	private static final String EMAIL = "email";

	/**
	 * 
	 */
	private static final String LAST_NAME = "lastName";

	/**
	 * 
	 */
	private static final String FIRST_NAME = "firstName";

	/**
	 * 
	 */
	private static final String USERNAME = "username";

	/**
	 * 
	 */
	private static final String PASSWORD = "password";

	// Specific Dao implementation injected by Guice
	@Inject
	private Dao dao;

    private static final Logger log = Logger.getLogger(Register.class.getName());

    public static final int EXPIRATION_FIELD = Calendar.DATE;
    public static final int EXPIRATION_INCREMENT = 7;
        
	/**
	 * 
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		PrintWriter output = resp.getWriter();

		// build HTML response page
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		output.println("<head><title>Add initial datastore information</title></head>");
		output.println("<body>");

		String username = req.getParameter(USERNAME);
		String firstName = req.getParameter(FIRST_NAME);
		String lastName = req.getParameter(LAST_NAME);
		String password = req.getParameter(PASSWORD);
		String email = req.getParameter(EMAIL);

		if (!StringSafety.isSafe(username)) {
			output.println("<span style=\"color: #ff0000;\">Username bad, please go back and enter it again</span>");
			output.println("</body>");
			return;
		}

		if (!StringSafety.isSafe(firstName)) {
			output.println("<span style=\"color: #ff0000;\">First name bad, please go back and enter it again</span>");
			output.println("</body>");
			return;
		}

		if (!StringSafety.isSafe(lastName)) {
			output.println("<span style=\"color: #ff0000;\">Last name bad, please go back and enter it again</span>");
			output.println("</body>");
			return;
		}

		if (!StringSafety.isSafe(password)) {
			output.println("<span style=\"color: #ff0000;\">Password bad, please go back and enter it again</span>");
			output.println("</body>");
			return;
		}

		if (!StringSafety.isSafe(email)) {
			output.println("<span style=\"color: #ff0000;\">Email bad, please go back and enter it again</span>");
			output.println("</body>");
			return;
		}

		if (dao.getUser(username) != null) {
			output.println("<span style=\"color: #ff0000;\">Username already exists, please go back and enter it again</span>");
			output.println("</body>");
			return;
		}
		
		// Need to put user in database to reserve it
		User user = new User(null, username, User.STATUS_UNCONFIRMED, firstName, lastName, email, null);
		dao.changeUserPassword(user, password);
		
		Random r = new Random();
		String code = Long.toString(Math.abs(r.nextLong()), 36);
		Calendar expirationTime = Calendar.getInstance();
		expirationTime.add(EXPIRATION_FIELD, EXPIRATION_INCREMENT);
		
		
		ConfirmationCode confCode = new ConfirmationCode(null, username, code, expirationTime.getTimeInMillis());
		dao.putConfirmationCode(confCode);
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "Thank you for registering a VolunteerIM account!  Please follow the link below to confirm your account:\n\n";
		msgBody += "http://lisedexvolinfomantest.appspot.com/volinfoman/emailConfirm?username=" + username + "&code=" + code + "\n\n";
		msgBody += "Note: Please do not reply to this address, as email is thrown away.  If you did not set up a VolunteerIM account, please ignore this email, as the account will be removed automatically in a week.\n";
		
		try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(EMAIL_FROM_ADDRESS, EMAIL_FROM_NAME));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(email, firstName + " " + lastName));
            msg.setSubject(EMAIL_SUBJECT);
            msg.setText(msgBody);
            Transport.send(msg);
    
        } catch (AddressException e) {
        	output.println("Bad email address.  Please try again. " + e.toString() + "</body>");
        	log.info("AddressException sending confirmation email: " + e.toString());
        	dao.deleteUser(user.getId());
        	return;
        } catch (MessagingException e) {
            output.println("Error sending confirmation email.  Please try again. " + e.toString() + "</body>");
            log.info("MessagingException sending confirmation email: " + e.toString());
            dao.deleteUser(user.getId());
            return;
		}

        log.info("Mailed confirmation email to " + email + " using code " + code + " for username " + username);
        output.println("We have sent a confirmation email to " + email + ".  It should arrive shortly.  As soon as you receive it, please <a href=\"/\">return to the front page to log in.</a>");
		output.println("</body>");
	}
}
