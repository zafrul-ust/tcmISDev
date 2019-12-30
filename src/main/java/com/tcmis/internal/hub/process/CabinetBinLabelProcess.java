package com.tcmis.internal.hub.process;

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
 * Process for cabinet labels
 * @version 1.0
 *****************************************************************************/
public class CabinetBinLabelProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CabinetBinLabelProcess(String client) {
    super(client);
  }

  public Collection getBinData(CabinetLabelInputBean bean) throws BaseException, Exception {
    DbManager dbManager = new DbManager(getClient());
    CabinetBinLabelViewBeanFactory factory = new CabinetBinLabelViewBeanFactory(dbManager);
    return factory.select(new SearchCriteria("cabinetId", SearchCriterion.EQUALS, bean.getCabinetId().toString()), new SortCriteria("binId"));
  }

}