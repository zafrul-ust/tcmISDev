package com.tcmis.client.seagate.util;

import java.io.File;
import java.io.FileFilter;

public class FileTypeFilter implements FileFilter {

	/**
	 * @see FileFilter#accept(File)
	 */
	public boolean accept(File file) {
		if( file.isFile() )
			return true;
		return false;
	}

}
