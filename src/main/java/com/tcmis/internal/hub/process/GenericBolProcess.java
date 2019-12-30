package com.tcmis.internal.hub.process;

import org.apache.commons.logging.*;
import com.tcmis.common.framework.*;

/******************************************************************************
 * Process for generic BOL
 * @version 1.0
 *****************************************************************************/
public class GenericBolProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public GenericBolProcess(String client) {
    super(client);
  }

}
