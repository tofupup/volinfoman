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
package com.lisedex.volinfoman.server.admin;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.lisedex.volinfoman.server.Dao;
import com.lisedex.volinfoman.server.DaoGaeDatastore;
import com.lisedex.volinfoman.shared.User;

/**
 * Populates data store with initial information
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
public class BuildDB extends HttpServlet {
	// Specific Dao implementation injected by Guice
	@Inject
	private Dao dao;
	
	/**
	 * Adds base application information to datastore.  If sent with the
	 * "delete" query string, empties the entire datastore first.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		
		// build HTML response page
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().println("<head><title>Add initial datastore information</title></head>");
		resp.getWriter().println("<body>");
		
//		resp.getWriter().println("Request:<br />" + req.toString() + "<br />");
//		resp.getWriter().println("Query String:<br />" + req.getQueryString()+ "<br />");
		
		// if we got the query string "delete"
		if (req.getQueryString() != null && req.getQueryString().equals("delete")) {
			// notify user that the datastore is now gone
			resp.getWriter().println("<p><h1>DELETING DATA STORE</h1><p>");
			resp.getWriter().print("Removing Users...");
			
			// delete everything in the datastore
			dao.deleteAllUsers();
			
			resp.getWriter().println("DONE<p>");
		}
		
		resp.getWriter().println("<p>Adding initial data...<p>");

		// add an administrator user
		User user = new User(null, "admin", User.STATUS_CONFIRMED, "Admin", "Istrator", "admin@foobar.nono", "admin");
		dao.putUser(user);
		resp.getWriter().println("Adding User: " + user.toString());
		
		resp.getWriter().println("</body>");
	}
}
