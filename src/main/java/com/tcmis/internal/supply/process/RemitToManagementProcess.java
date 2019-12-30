package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.RemitToManagementInputBean;
import com.tcmis.internal.supply.beans.SupplierBillingLocationViewBean;

public class RemitToManagementProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static final String[] statusSpecificOptions = new String[] {"'Approved'", "'Rejected'", "'Inactive'"};
	private static GenericSqlFactory factory;
	
	public RemitToManagementProcess(String client) {
		super(client);
		DbManager dbManager = new DbManager(getClient(),getLocale());		
		factory = new GenericSqlFactory(dbManager);
	}  
	
	public RemitToManagementProcess(String client,String locale) {
	    super(client,locale);
		DbManager dbManager = new DbManager(getClient(),getLocale());		
		factory = new GenericSqlFactory(dbManager);
    }

	public Collection <SupplierBillingLocationViewBean> getRemitToAddresses(RemitToManagementInputBean bean) throws BaseException, Exception {		
		factory.setBean(new SupplierBillingLocationViewBean());
		
		StringBuilder query = new StringBuilder("select sbl.SUPPLIER, sbl.BILLING_LOCATION_ID, sbl.SAP_VENDOR_CODE, sbl.STATUS, loc.*");
		query.append(" from supplier_billing_location sbl, customer.location loc");
		query.append(" where sbl.supplier = ").append(SqlHandler.delimitString(bean.getSupplier()));
		query.append(" and sbl.billing_location_id = loc.location_id");
		
		if (!StringHandler.isBlankString(bean.getStatus())) {
			String[] statuses = bean.getStatus().split(",");
			if (!statuses[0].equalsIgnoreCase("All")) {
				StringBuilder statusStr = new StringBuilder("(");
				for (String s : statuses) {
					if (statusStr.length() > 1)
						statusStr.append(" or");
					if (s.equalsIgnoreCase("Other"))
						statusStr.append(" nvl(sbl.status, 'X') not in (").append(String.join(",", statusSpecificOptions)).append(")");
					else
						statusStr.append(" nvl(sbl.status, 'X') = ").append(SqlHandler.delimitString(s));
				}
				statusStr.append(")");
				query.append(" and ").append(statusStr.toString());
			}
		}
		
		query.append(" order by nvl(status,'Q'),location_desc,city,sap_vendor_code");
		
		return factory.selectQuery(query.toString());
	}
	
	public void updateStatus(SupplierBillingLocationViewBean bean, BigDecimal personnelId) {
		try {
			boolean notificationNeeded = false;
			StringBuilder query = new StringBuilder("update supplier_billing_location set status = ");
			if ("Approved".equalsIgnoreCase(bean.getStatus())) {
				query.append(SqlHandler.delimitString("Inactive"));
				query.append(", last_updated_date = sysdate");
			} else {
				if (StringHandler.isBlankString(bean.getSapVendorCode())) {
					query.append(SqlHandler.delimitString("Open"));
					query.append(", request_date = sysdate");
					notificationNeeded = true;
				} else {
					query.append(SqlHandler.delimitString("Approved"));
					query.append(", last_updated_date = sysdate");
				}
			}
			query.append(", last_updated_by = ").append(personnelId.toPlainString());
			query.append(" where supplier = ").append(SqlHandler.delimitString(bean.getSupplier()));
			query.append(" and billing_location_id = ").append(SqlHandler.delimitString(bean.getBillingLocationId()));
			
			factory.deleteInsertUpdate(query.toString());
			
			if (notificationNeeded) {
				try {
					sendStatusUpdatedNotificationEmail(bean.getBillingLocationId());
				} catch (BaseException e) {
					
				}
			}
		} catch (Exception e) {}
	}

	@SuppressWarnings("unchecked")
	public void sendStatusUpdatedNotificationEmail (String billingLocationId) throws BaseException {
		StringBuilder query;
		
		//get information about the updated location
		factory.setBean(new SupplierBillingLocationViewBean());
		query = new StringBuilder("select * from SUPPLIER_BILLING_LOCATION_VIEW");
		query.append(" where billing_location_id = ").append(SqlHandler.delimitString(billingLocationId));
		SupplierBillingLocationViewBean billingLocationBean = ((Vector<SupplierBillingLocationViewBean>) factory.selectQuery(query.toString())).firstElement();
		String subject = "New Remit To address entered for supplier " + billingLocationBean.getSupplierName() + " waiting approval";
		StringBuilder msg = new StringBuilder("");
		msg.append("New Remit To address entered for supplier ").append(billingLocationBean.getSupplierName()).append(" waiting approval.");
		msg.append("\n\n");
		msg.append("Address: ").append(billingLocationBean.getTcmSupplierBillAddress());
		msg.append("\n\n");
		msg.append("Please click on the link below.");
		msg.append("\n\n\n");
		msg.append("https://www.tcmis.com/tcmIS/supply/newremittomain.do");
		msg.append("\n\n\n\n\n");
		msg.append(billingLocationId);
		
		//get users to be notified
		factory.setBean(new PersonnelBean());
		query = new StringBuilder("select distinct a.personnel_id,b.email from user_group_member a, personnel b where a.personnel_id = b.personnel_id and a.user_group_id = 'newRemitTo'");
		Vector<PersonnelBean> beanColl = (Vector<PersonnelBean>) factory.selectQuery(query.toString());		
		StringBuilder emailsStr = new StringBuilder("");
		for (PersonnelBean personnelBean : beanColl)
			if (!StringHandler.isBlankString(personnelBean.getEmail()))
				emailsStr.append(personnelBean.getEmail()).append(",");
		String[] emails = emailsStr.substring(0, emailsStr.length() - 1).split(",");
		
		MailProcess.sendEmail(emails, new String[0], subject, msg.toString(), false);
	}
}




