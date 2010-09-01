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
package com.lisedex.volinfoman.server.cron;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lisedex.volinfoman.server.Dao;
import com.lisedex.volinfoman.server.DaoGaeDatastore;

/**
 * @author John Schutz <john@lisedex.com>
 *
 */
@SuppressWarnings("serial")
public class ExpireConfirmationCodes extends HttpServlet {
	private Dao dao = new DaoGaeDatastore();
	
    private static final Logger log = Logger.getLogger(ExpireConfirmationCodes.class.getName());

	/**
	 * Adds base application information to datastore.  If sent with the
	 * "delete" query string, empties the entire datastore first.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		
		Calendar now = Calendar.getInstance();
		log.info("Expiring expiration codes at " + Long.toString(now.getTimeInMillis()));

		dao.expireCodesBefore(now.getTimeInMillis());
	}
}