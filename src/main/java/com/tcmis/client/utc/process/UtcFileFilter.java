package com.tcmis.client.utc.process;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class UtcFileFilter
    implements FilenameFilter {
  public UtcFileFilter() {
  }

  public boolean accept(File dir, String name) {
    boolean flag = false;
    if(name.endsWith(".txt") && name.startsWith("Invoices_HAAS_")) {
      flag = true;
    }
    return flag;
  }

}
