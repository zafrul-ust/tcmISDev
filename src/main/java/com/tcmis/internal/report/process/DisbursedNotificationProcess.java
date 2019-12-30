package com.tcmis.internal.report.process;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.internal.report.beans.DisbursedNotificationBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.util.*;

/******************************************************************************
 * Process used by DisbursedNotificationProcess
 * @version 1.0
 *****************************************************************************/

public class DisbursedNotificationProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private String domain = "";


	public DisbursedNotificationProcess(String client, String domain) {
		super(client);
		this.domain = domain;
	}

	public void processData() throws Exception {
		try {
			StringBuilder query = new StringBuilder("select fx_facility_name(cpi.facility_id,cpi.company_id) facility_name,");
			query.append("fx_application_desc(cpi.facility_id,cpi.application,cpi.company_id) application_desc,");
			query.append("cpi.cat_part_no,nvl(cpi.bin_name,cpi.bin_id) bin_name,wabc.disbursed_amount count_quantity,");
			query.append("wabc.date_processed disbursed_datetime,fx_personnel_id_to_name(wabc.personnel_id) disbursed_by,");
			query.append("nvl(fx_personnel_id_to_email(ugma.personnel_id),'tngo@haastcm.com') email_address,cp.web_application_path");
			query.append(",wabc.disbursed_unit_of_measure");
			query.append(" from customer.work_area_bin_count wabc,customer.cabinet_part_inventory cpi,security.connection_pool cp,");
			query.append("inventory_group_definition igd,customer.facility_inventory_group fig,customer.user_group_member_application ugma");
			query.append(" where wabc.date_processed > sysdate -1 and wabc.company_id = cpi.company_id and");
			query.append(" wabc.bin_id = cpi.bin_id and cpi.company_id = fig.company_id and cpi.facility_id = fig.facility_id and");
			query.append(" fig.inventory_group = igd.inventory_group and igd.process_disbursement = 'Y' and cpi.company_id = ugma.company_id(+) and");
			query.append(" cpi.facility_id = ugma.facility_id(+) and cpi.application = decode(ugma.application(+),'All',cpi.application,ugma.application(+))");
			query.append(" and ugma.user_group_id(+) = 'DisbursementNotification' and cp.connection_pool = wabc.company_id");
			query.append(" order by email_address,facility_name,application_desc");
			DbManager dbManager = new DbManager(getClient());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new DisbursedNotificationBean());
			Collection<DisbursedNotificationBean> beans= genericSqlFactory.selectQuery(query.toString());
			//separate email notification per user
			Hashtable emailH = new Hashtable();
			Hashtable applicationPathH = new Hashtable();
			for (DisbursedNotificationBean bean : beans) {
				if (emailH.containsKey(bean.getEmailAddress())) {
					StringBuilder msg = (StringBuilder)emailH.get(bean.getEmailAddress());
					msg.append("\n");
					createMessage(msg,bean);
				}else {
					StringBuilder msg = new StringBuilder("Disbursement activities were performed in your Work Areas:\n\n");
					createMessage(msg,bean);
					emailH.put(bean.getEmailAddress(),msg);
					applicationPathH.put(bean.getEmailAddress(),bean.getWebApplicationPath());
				}
			}  //end of for loop

			//sending out email notification
			if (emailH.size() > 0) {
				Enumeration enu = emailH.keys();
				while(enu.hasMoreElements()) {
					String email = (String)enu.nextElement();
					StringBuilder msg = (StringBuilder)emailH.get(email);
					//additional instructions
					additionalInstruction(msg,(String)applicationPathH.get(email));
					MailProcess.sendEmail(email,null,"tcmis@tcmis.com","Disbursement Notification",msg.toString());
				}
			}
		}catch (Exception e) {
			log.error(e);
		}
	}  //end of method

	private void additionalInstruction(StringBuilder msg, String applicationPath) {
		msg.append("\n\n").append("Please follow the following instructions to get to Acknowledge Disbursement page:").append("\n");
		msg.append("\t- ").append(domain).append(applicationPath).append("/home.do").append("\n");
		msg.append("\t- ").append("Click on Start tcmIS").append("\n");
		msg.append("\t- ").append("Enter your Logon Id and Password then click on Login").append("\n");
		msg.append("\t- ").append("Once you logged in and if Acknowledge Disbursement page is not on your startup pages then").append("\n");
		msg.append("\t- ").append("Click on Supply Chain then Work Area Inventory then click on Acknowledge Disbursement").append("\n");
		msg.append("\t- ").append("On this page you can acknowledge all of the above activities").append("\n");
	}

	private void createMessage(StringBuilder msg, DisbursedNotificationBean bean) {
		msg.append("Facility:\t\t\t").append(bean.getFacilityName()).append("\n");
		msg.append("Work Area:\t\t").append(bean.getApplicationDesc()).append("\n");
		msg.append("Part Number:\t\t").append(bean.getCatPartNo()).append("\n");
		msg.append("Bin:\t\t\t").append(bean.getBinName()).append("\n");
		msg.append("Disbursed Quantity:\t").append(bean.getCountQuantity()).append(" ").append(bean.getDisbursedUnitOfMeasure()).append("\n");
		msg.append("Disbursed Time:\t").append(DateHandler.formatDate(bean.getDisbursedDatetime(),"MM/dd/yyyy HH:mm")).append("\n");
		msg.append("Dusbursed By:\t\t").append(bean.getDisbursedBy()).append("\n");
	}
} //end of class
