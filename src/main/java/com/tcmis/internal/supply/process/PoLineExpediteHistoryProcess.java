package com.tcmis.internal.supply.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.supply.beans.PoLineExpediteHistoryViewBean;
import com.tcmis.internal.supply.factory.PoLineExpediteHistoryViewBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

/**
 * ***************************************************************************
 * Process tcreate the .
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class PoLineExpediteHistoryProcess extends BaseProcess {
Log log = LogFactory.getLog(this.getClass());

public PoLineExpediteHistoryProcess(String client, String locale) {
    super(client, locale);
}

public PoLineExpediteHistoryProcess(String client) {
    super(client);
}

public Collection<PoLineExpediteHistoryViewBean> getSearchResult(PoLineExpediteHistoryViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
    DbManager dbManager = new DbManager(getClient(), getLocale());
    PoLineExpediteHistoryViewBeanFactory factory = new PoLineExpediteHistoryViewBeanFactory(dbManager);

    SearchCriteria criteria = new SearchCriteria();

    if (inputBean.getRadianPo()!=null) {
        criteria.addCriterion("radianPo", SearchCriterion.EQUALS,""+inputBean.getRadianPo()+"");
    }
    if (inputBean.getPoLine() != null) {
        criteria.addCriterion("poLine", SearchCriterion.EQUALS, ""+inputBean.getPoLine()+"");
    }
    if (inputBean.getItemId() != null) {
        criteria.addCriterion("itemId", SearchCriterion.EQUALS, ""+inputBean.getItemId()+"");
    }

    if (inputBean.getOpsEntityId()!=null) {
        criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS,""+inputBean.getOpsEntityId()+"");
    }

    String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
   	criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);

    SortCriteria scriteria = new SortCriteria();
    scriteria.setSortAscending(false);
    scriteria.addCriterion("dateFirstConfirmed");
    Collection<PoLineExpediteHistoryViewBean> c = factory.select(criteria, scriteria);
    return c;
}
}
