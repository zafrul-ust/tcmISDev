package com.tcmis.internal.hub.process;

import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for repackaging
 * @version 1.0
 *****************************************************************************/
public class RepackagingProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public RepackagingProcess(String client) {
    super(client);
  }

  public Collection getInventoryGroupDefinitions() throws
      BaseException {
    DbManager dbManager = new DbManager(getClient());
    InventoryGroupDefinitionBeanFactory factory =
        new InventoryGroupDefinitionBeanFactory(dbManager);
    return factory.getInventoryGroups(new SearchCriteria());
  }

}
