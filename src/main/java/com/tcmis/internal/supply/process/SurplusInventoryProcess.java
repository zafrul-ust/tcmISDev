package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.supply.factory.*;

/******************************************************************************
 * Process for buy orders
 * @version 1.0
 *****************************************************************************/
public class SurplusInventoryProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public SurplusInventoryProcess(String client) {
    super(client);
  }

  public Collection getSurplusData(BigDecimal itemId)  throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    SurplusInventoryViewBeanFactory factory = new SurplusInventoryViewBeanFactory(dbManager);
    return factory.select(new SearchCriteria("itemId", SearchCriterion.EQUALS, itemId.toString()));
  }
  
}