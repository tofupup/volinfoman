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
package com.lisedex.volinfoman.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.lisedex.volinfoman.server.Dao;
import com.lisedex.volinfoman.server.DaoGaeDatastore;
import com.lisedex.volinfoman.server.UserServiceImpl;
import com.lisedex.volinfoman.server.admin.BuildDB;
import com.lisedex.volinfoman.server.admin.CacheStats;

/**
 * Configure Guice injection for the server side
 * 
 * @author John Schutz <john@lisedex.com>
 *
 */
public class VolinfomanGuiceModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		// Servlets are Singletons
		bind(UserServiceImpl.class).in(Singleton.class);
		bind(BuildDB.class).in(Singleton.class);
		bind(CacheStats.class).in(Singleton.class);
		
		// Data providers
		bind(Dao.class).to(DaoGaeDatastore.class).in(Singleton.class);
	}

}
