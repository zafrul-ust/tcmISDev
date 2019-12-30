package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.supply.beans.NewRemitToAddressInputBean;

/******************************************************************************
 * Process for buy orders
 * @version 1.0
 *****************************************************************************/
public class NewRemitToAddressProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public NewRemitToAddressProcess(String client,Locale locale) {
		super(client,locale);
	}

	public String callCreateRemitTo(NewRemitToAddressInputBean bean,PersonnelBean personnelBean)  throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		Collection inArgs = new Vector(12);
		if (isBlank(bean.getSupplier())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getSupplier());
		}

		if (personnelBean.getPermissionBean().hasFacilityPermission("newRemitTo",null,null))
		{
			inArgs.add("Y");
		}
		else
		{
			inArgs.add("");
		}

		if (isBlank(bean.getRemitToCountryAbbrev())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getRemitToCountryAbbrev());
		}
		if (isBlank(bean.getRemitToStateAbbrev())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getRemitToStateAbbrev());
		}
		if (isBlank(bean.getRemitToAddressLine1())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getRemitToAddressLine1());
		}
		if (isBlank(bean.getRemitToAddressLine2())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getRemitToAddressLine2());
		}
		if (isBlank(bean.getRemitToAddressLine3())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getRemitToAddressLine3());
		}
		if (isBlank(bean.getRemitToCity())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getRemitToCity());
		}
		if ( isBlank(bean.getRemitToZip()) ) {
			inArgs.add("");
		}
		else {
			//if ( isBlank(bean.getRemitToZipPlus()))
				inArgs.add(bean.getRemitToZip());
			/*else
				inArgs.add(bean.getRemitToZip()+"-"+bean.getRemitToZipPlus());*/
		}
		if ( isBlank(bean.getRemitToZipPlus()) ) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getRemitToZipPlus());
		}

		if (isBlank(bean.getSupplierName())) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getSupplierName());
		}

		inArgs.add(personnelId);

		if (personnelBean.getPermissionBean().hasFacilityPermission("newRemitTo",null,null))
		{
			inArgs.add("Approved");
		}
		else
		{
			inArgs.add("Open");
		}

		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		if(log.isDebugEnabled()) {
			log.debug("inArgs:"+inArgs);
		}
		Vector error = (Vector) procFactory.doProcedure("pkg_contract_setup.p_create_remit_to", inArgs, outArgs);

		String errorCode = "";
		if(error.size()>0 && error.get(0) != null)
		{
			errorCode = error.get(0).toString();
		}

		if (!personnelBean.getPermissionBean().hasFacilityPermission("newRemitTo",null,null))
		{
			sendNotificationMail(bean,personnelBean);
		}
		return errorCode;
	}

	private void sendNotificationMail(NewRemitToAddressInputBean bean,PersonnelBean personnelBean) {
		try {
			DbManager dbManager = new DbManager(this.getClient(),getLocale());
			PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
			Collection c = null;
			String subject = null;
			String message = null;
			
			ResourceLibrary resource = new ResourceLibrary("label");
            String hostUrl = resource.getString("label.hosturl");

			c = factory.selectDistinctUserByFacilityPermission("newRemitTo");
			subject = "New Remit To Address for " + bean.getSupplierName() + ", Requested by " + personnelBean.getFullName() + " - Waiting Approval";
			message = "New Remit To Address for " + bean.getSupplierName() + ", Requested by " + personnelBean.getFullName() + " - Waiting Approval \n\n" +
			"Please click on the link below to review and approve.\n\n" +
			hostUrl + "/tcmIS/supply/newremittomain.do\n\n";

			String[] to = new String[c.size()];
			Iterator it = c.iterator();
			for (int i = 0; it.hasNext(); i++) {
				PersonnelBean b = (PersonnelBean) it.next();
				to[i] = b.getEmail();
			}
			String[] cc = new String[0];
			if(log.isDebugEnabled()) {
				for(int i=0; i<to.length;i++) {
					log.debug("Sending email to:" + to[i]);
				}
			}

			//MailProcess.sendEmail("mwennberg@haastcm.com", "", MailProcess.DEFAULT_FROM_EMAIL,subject, message);
			MailProcess.sendEmail(to, cc, subject, message, true);
		}
		catch(Exception e) {
			log.error("Error sending email.", e);
		}
	}

} //end of class