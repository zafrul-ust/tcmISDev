package com.tcmis.client.cal.process;

import java.io.File;
import java.io.FilenameFilter;

/******************************************************************************
 * File filter to get .xml files.
 * @version 1.0
 *****************************************************************************/

public class FmcFileFilter
    implements FilenameFilter {
  public FmcFileFilter() {
  }

  public boolean accept(File dir, String name) {
    boolean flag = false;
//    if (name.endsWith(".xls")) {
    if (name.endsWith(".xml")) {
      flag = true;
    }
    return flag;
  }

}