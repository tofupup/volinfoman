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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;
import net.sf.jsr107cache.CacheStatistics;

import com.allen_sauer.gwt.log.client.Log;

/**
 * Populates data store with initial information
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
@SuppressWarnings("serial")
public class CacheStats extends HttpServlet {
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
		resp.getWriter().println("<head><title>Cache Statistics</title></head>");
		resp.getWriter().println("<body>");
		
//		resp.getWriter().println("Request:<br />" + req.toString() + "<br />");
//		resp.getWriter().println("Query String:<br />" + req.getQueryString()+ "<br />");
				
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = dateFormat.format(new Date());
		resp.getWriter().println("<p>Cache statistics at " + date + " <p>");

		Cache cache;
		
		try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
        	Log.fatal("Could not get cache instance.", new CacheException(e.toString()));
        	resp.getWriter().println("FAILED.<p></body>");
        	return;
        }
	
        CacheStatistics stats = cache.getCacheStatistics();
        resp.getWriter().println("Hits: " + stats.getCacheHits() 
        		+ "  Misses: " + stats.getCacheMisses());
		
		resp.getWriter().println("</body>");
	}
}
