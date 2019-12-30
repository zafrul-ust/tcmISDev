package com.tcmis.internal.hub.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:08:29 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.admin.factory.VvFreightAdviceStatusBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.BatchTestResultViewBean;
import com.tcmis.internal.hub.beans.PolchemNsnVerificationBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.factory.PolchemNsnVerificationBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptDocumentViewBeanFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/******************************************************************************
 * Process for ItemManagement
 * @version 1.0
 *****************************************************************************/

public class ItemWeightProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public ItemWeightProcess(String client) {
	super(client);
 }
 
 public ItemWeightProcess(String client,String locale) {
	    super(client,locale);
}

public Collection<PolchemNsnVerificationBean> getSearchData(PolchemNsnVerificationBean inputBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());

	PolchemNsnVerificationBeanFactory factory = new PolchemNsnVerificationBeanFactory(dbManager);
	SearchCriteria searchCriteria = new SearchCriteria();
    searchCriteria.addCriterion("nsn",
							 SearchCriterion.LIKE,
							 inputBean.getNsn(),SearchCriterion.IGNORE_CASE);
    return factory.select(searchCriteria, null);
 }

 public String updateValue(PolchemNsnVerificationBean bean)
 throws BaseException {
	 Collection inArgs = new Vector();


	 inArgs.add(bean.getNsn());
	 inArgs.add(bean.getUi());
	 if( bean.getGrossWeightLbs() != null ) {
		 inArgs.add(bean.getGrossWeightLbs());
	 }
	 else	{
		 inArgs.add("");
	 }
	 if(bean.getCubicFeet()!= null ) {
		 inArgs.add(bean.getCubicFeet());
	 }
	 else	{
		 inArgs.add("");
	 }
	 if(bean.getContainersPerUi()!= null ) {
		 inArgs.add(bean.getContainersPerUi());
	 }
	 else	{
		 inArgs.add("");
	 }
	 if(bean.getMaxNsnPerBox()!= null ) {
		 inArgs.add(bean.getMaxNsnPerBox());
	 }
	 else	{
		 inArgs.add("");
	 }
	 if(bean.getMaxNsnPerPallet()!= null ) {
		 inArgs.add(bean.getMaxNsnPerPallet());
	 }
	 else	{
		 inArgs.add("");
	 }
	 inArgs.add(bean.getExternalContainer());
	 inArgs.add(bean.getVerifiedBy());
	 Vector outArgs = new Vector();
	 outArgs.add(new Integer(java.sql.Types.VARCHAR));

	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 GenericProcedureFactory procFactory = new GenericProcedureFactory(
			 dbManager);
	 Collection output = procFactory.doProcedure(
			 "p_verification_update_by_nsn", inArgs, outArgs);
	 return (String) output.toArray()[0];
 }

 public String update(Collection beans) throws
	BaseException {
	String errMsg = "";
	
	for(PolchemNsnVerificationBean bean: (Collection<PolchemNsnVerificationBean>) beans) {
	 if ("ok".equals(bean.getOk())) {
		 errMsg += StringHandler.emptyIfNull(updateValue(bean));
	 }
	}
	return errMsg;
 }
 
}
