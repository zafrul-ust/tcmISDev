package com.tcmis.client.report.process;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.client.report.beans.SingleSelectBean;
import com.tcmis.common.framework.GenericSqlFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;

/******************************************************************************
 * Process for single selection
 * @version 1.0
 *****************************************************************************/
public class SingleSelectProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public SingleSelectProcess(String client) {
    super(client);
  }

  public Collection getData(SingleSelectBean inputBean) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SingleSelectBean());
    if (inputBean.isWorkAreaData()) {
        StringBuilder query = new StringBuilder("select application id, application_desc description from fac_loc_app");
        query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and facility_id = '").append(inputBean.getFacilityId()).append("' and status = 'A'");
        query.append(" order by application_desc");
        return factory.selectQuery(query.toString());
    }else
      return new Vector(0);
  }  //end of method

} //end of class