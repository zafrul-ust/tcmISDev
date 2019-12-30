package com.tcmis.internal.print.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.ContainerLabelDetailViewBean;
import com.tcmis.internal.hub.factory.ContainerLabelDetailViewBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

public class UnitLabelPartNumberProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public UnitLabelPartNumberProcess(String client) {
	super(client);
 }

 public UnitLabelPartNumberProcess(String client, String locale) {
	super(client,locale);
 }

 public Collection getSearchData(ContainerLabelDetailViewBean inputBean) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
   ContainerLabelDetailViewBeanFactory factory = new
	 ContainerLabelDetailViewBeanFactory(dbManager);

  SearchCriteria childCriteria = new SearchCriteria();
	 childCriteria.addCriterion("itemId", SearchCriterion.EQUALS,
		inputBean.getItemId().toString());
	 childCriteria.addCriterion("hub", SearchCriterion.EQUALS,
		inputBean.getHub());
	 childCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		inputBean.getInventoryGroup());
	 Collection containerLabelDetailViewBeanColl = factory.selectDistinctPartNumber(childCriteria);
   return containerLabelDetailViewBeanColl;
 }
}