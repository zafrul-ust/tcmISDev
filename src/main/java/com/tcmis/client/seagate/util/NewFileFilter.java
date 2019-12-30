/*
 * Created on Dec 21, 2004
 */
package com.tcmis.client.seagate.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author chuckls
 */
public class NewFileFilter implements FileFilter {
  private static Logger log = Logger.getLogger(NewFileFilter.class);

  private List oldfiles;
  private File folder, history;
  private String historyPath;

  /**
   * Construct a NewFileFilter that checks Files against a
   * list of old Files. The list of old files is constructed
   * from a folder file and a history file containing a list
   * of filenames with one filename per line.
   *
   * @param history is a File containing a list of oldfiles, one pathname per line.
   * @throws Exception
   */
  public NewFileFilter(File folder, File history) throws Exception {
    super();
    this.oldfiles = new ArrayList();
    this.folder = folder;
    this.history = history;
    this.historyPath = history.getCanonicalPath();
    if(! history.exists()) {
      throw new IllegalArgumentException("History file does not exist: "+historyPath);
    }
    BufferedReader buf = new BufferedReader(new FileReader(history));
    String filename;
    while((filename = buf.readLine()) != null) {
      oldfiles.add(new File(folder, filename));
    }
  }

  /**
   * If file is not in the list of oldfiles then accept it and
   * add its filename to the history file.
   *
   * @param candidate is the pathname of the file
   * @return <tt>TRUE</tt> if the file is new.
   * @throws
   */
  public boolean accept(File candidate) {
    Iterator it = oldfiles.iterator();
    while(it.hasNext()) {
      File old = (File) it.next();
      if(old.compareTo(candidate) == 0) {
        return false;
      }
    }
    oldfiles.add(candidate);
    return true;
  }

  /**
   * @return Returns the oldfiles.
   */
  public List getOldfiles() {
    return oldfiles;
  }
  /**
   * @param oldfiles The oldfiles to set.
   */
  public void setOldfiles(List oldfiles) {
    this.oldfiles = oldfiles;
  }
}
