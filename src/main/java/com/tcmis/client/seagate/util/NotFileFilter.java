/*
 * Created on May 7, 2003
 */
package com.tcmis.client.seagate.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author chuckls
 */
public class NotFileFilter implements FilenameFilter {
	public final String[] names;

	public NotFileFilter(String[] names) {
		this.names = names;
	}

	public boolean accept(File dir, String filename) {
		if (filename != null) {
			for (int i = 0; i < names.length; i++) {
				if (filename.equals(names[i]))
					return false;
			}
			return true;
		}
		return false;
	}

}