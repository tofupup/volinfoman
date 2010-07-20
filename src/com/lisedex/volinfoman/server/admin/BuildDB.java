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

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.lisedex.volinfoman.server.DAO;
import com.lisedex.volinfoman.shared.User;

/**
 * Populates data store with initial information
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
public class BuildDB extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().println("<head><title>Add initial datastore information</title></head>");
		resp.getWriter().println("<body>");
		
//		resp.getWriter().println("Request:<br />" + req.toString() + "<br />");
//		resp.getWriter().println("Query String:<br />" + req.getQueryString()+ "<br />");
		
		DAO dao = new DAO();
	
		if (req.getQueryString() != null && req.getQueryString().equals("delete")) {
			resp.getWriter().println("<p><h1>DELETING DATA STORE</h1><p>");
			
			resp.getWriter().print("Removing Users...");
			
			Objectify ofy = ObjectifyService.begin();
			// we want all items of User
			Query<User> query = ofy.query(User.class);
			resp.getWriter().println(query.listKeys());
			// now we delete them by getting a list of their keys
			ofy.delete(query.fetchKeys());
			resp.getWriter().println("<p>DONE<p>");
			
		}
		
		resp.getWriter().println("<p>Adding initial data...<p>");

		// add an administrator user
		User user = new User(null, "admin", User.STATUS_CONFIRMED, "Admin", "Istrator", "admin@foobar.nono", "admin");
		dao.putUser(user);
		resp.getWriter().println("Adding User: " + user.toString());
		
		resp.getWriter().println("</body>");
	}
}
