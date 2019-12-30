package com.tcmis.client.fec.process;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;
import com.tcmis.common.util.DateHandler;

/******************************************************************************
 * File filter to get chemical onhand files.
 * @version 1.0
 *****************************************************************************/

public class ChemicalMovementFileFilter
    implements FilenameFilter {
  public ChemicalMovementFileFilter() {
  }

  public boolean accept(File dir, String name) {
    boolean flag = false;
    if (name.equalsIgnoreCase(DateHandler.formatDate(new Date(), "yyyyMMdd") + "_chemical_movements.csv")) {
      flag = true;
    }
    return flag;
  }

}