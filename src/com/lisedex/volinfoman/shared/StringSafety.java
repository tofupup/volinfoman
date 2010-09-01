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

/**
 * @author John Schutz <john@lisedex.com>
 *
 */
public class StringSafety {
	final static char[] BADCHARS = { ';' , '&' };
	
	public static boolean isSafe(String testString) {
		if (testString == null) 
			return true;
		for (int i = 0; i < BADCHARS.length; i++) {
			if (testString.indexOf(BADCHARS[i]) >= 0) {
				return false;
			}
		}
		return true;
	}
}
