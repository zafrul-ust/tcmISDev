package com.tcmis.internal.hub.process;

import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.factory.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for last time cabinet was scanned
 * @version 1.0
 *****************************************************************************/
public class CabinetLastScannedProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CabinetLastScannedProcess(String client) {
    super(client);
  }

  public Collection getFacilityBeanCollection() throws BaseException, Exception {
    DbManager dbManager = new DbManager(getClient());
    FacilityBeanFactory factory = new FacilityBeanFactory(dbManager);
    return factory.select(new SearchCriteria());
  }

  public Collection getHubCabinetViewBeanCollection() throws BaseException,
      Exception {
    DbManager dbManager = new DbManager(getClient());
    HubCabinetViewBeanFactory factory = new HubCabinetViewBeanFactory(dbManager);
    return factory.select(new SearchCriteria(),null);
  }

  public Collection getCabLastScannedViewBeanCollection() throws BaseException,
      Exception {
    DbManager dbManager = new DbManager(getClient());
    CabLastScannedViewBeanFactory factory = new CabLastScannedViewBeanFactory(
        dbManager);
    return factory.select(new SearchCriteria());
  }

}
