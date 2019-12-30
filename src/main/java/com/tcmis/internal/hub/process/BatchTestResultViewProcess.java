package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.hub.beans.BatchTestResultViewBean;
import com.tcmis.internal.hub.factory.BatchTestResultViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import java.sql.Timestamp;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.admin.factory.PersonnelNameUserGroupViewBeanFactory;
import java.math.BigDecimal;

/******************************************************************************
 * Process for BatchTestResultView
 * @version 1.0
 *****************************************************************************/
public class BatchTestResultViewProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public BatchTestResultViewProcess(String client) {
		super(client);
	}

	public Collection getSearchResult(BatchTestResultViewBean bean,
			boolean readonly) throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		BatchTestResultViewBeanFactory factory = new BatchTestResultViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("batchId", SearchCriterion.EQUALS, bean
				.getBatchId().toString());
		if (!readonly) // its actually reversed, readonly = true means can update.
		{
			criteria.addCriterion("testDate", SearchCriterion.IS_NOT, "null");
		}
		return factory.select(criteria);
	}

	public Collection getNameList(BatchTestResultViewBean bean)
			throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		PersonnelNameUserGroupViewBeanFactory factory = new PersonnelNameUserGroupViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("userGroupId", SearchCriterion.EQUALS,
				"BatchTesting");
		return factory.select(criteria);
	}

	public void updateValue(BatchTestResultViewBean bean, BigDecimal personnelId)
			throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(personnelId);
		if (bean.getTestId() != null) {
			inArgs.add(bean.getTestId());
		} else {
			inArgs.add("");
		}
		if (bean.getTestUserId() != null) {
			inArgs.add(bean.getTestUserId());
		} else {
			inArgs.add("");
		}
		if (bean.getTestDate() != null) {
			try {
				inArgs.add(new Timestamp(bean.getTestDate().getTime()));
			} catch (Exception ex) {
			}
		} else {
			inArgs.add("");
		}

		if (bean.getBatchId() != null) {
			inArgs.add(bean.getBatchId());
		} else {
			inArgs.add("");
		}
		if (bean.getTestParameter() != null) {
			inArgs.add(bean.getTestParameter());
		} else {
			inArgs.add("");
		}
		if (bean.getTestResult() != null) {
			inArgs.add(bean.getTestResult());
		} else {
			inArgs.add("");
		}
		if (bean.getTestResultUnit() != null) {
			inArgs.add(bean.getTestResultUnit());
		} else {
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Vector inArgs2 = new Vector();
		inArgs2.add(""); // commit default 'Y'

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		Collection output = procFactory.doProcedure(
				"p_update_batch_test_result", inArgs, outArgs, inArgs2);
		if (output.size() == 1) {
			System.out.println("Output [1] is " + output.toArray()[0]);
		}
	}

}
