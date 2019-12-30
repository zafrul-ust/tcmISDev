package com.tcmis.internal.hub.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jul 8, 2008
 * Time: 10:54:55 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for
 * @version 1.0
 *****************************************************************************/
public class IataProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public IataProcess(String client) {
    super(client);
  }

  public Collection getHubDropDownData(BigDecimal personnelId)  throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    HubInventoryGroupFacOvBeanFactory factory = new HubInventoryGroupFacOvBeanFactory(dbManager);
    return factory.selectObject(new SearchCriteria("personnelId", SearchCriterion.EQUALS, personnelId.toString()), new SortCriteria("priority,facilityId"));
  }
}
