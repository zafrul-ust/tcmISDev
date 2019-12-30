package com.tcmis.internal.catalog.process;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.ApprovalCodeExpiringBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process used by ApprovalCodeExpiringProcess
 * @version 1.0
 *****************************************************************************/

public class ApprovalCodeExpiringProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());


	public ApprovalCodeExpiringProcess(String client) {
		super(client);
	}

	public void processData() throws Exception {
		DbManager dbManager = new DbManager(getClient());
		Connection connection = dbManager.getConnection();
		try {

			StringBuilder query = new StringBuilder("select a.company_id,a.facility_id,a.catalog_company_id,a.catalog_id,a.cat_part_no,a.part_group_no,a.start_date,a.end_date,");
			query.append(" b.application_use_group_id,b.usage_subcategory_name,c.application,c.status cabinet_part_inventory_status,f.facility_name");
			query.append(",nvl(fx_application_desc(c.facility_id,c.application,c.company_id),c.application) application_desc");
			query.append(" from fac_part_use_code a, vv_usage_subcategory b, cabinet_part_inventory c, customer.facility f");
			query.append(" where a.end_date > sysdate -1 and a.end_date < sysdate + 14");
			query.append(" and a.company_id = b.company_id and a.facility_id = b.facility_id and a.usage_subcategory_id = b.usage_subcategory_id");
			query.append(" and a.company_id = c.company_id(+) and a.facility_id = c.facility_id(+) and a.catalog_company_id = c.catalog_company_id(+)");
			query.append(" and a.catalog_id = c.catalog_id(+) and a.cat_part_no = c.cat_part_no(+) and a.part_group_no = c.part_group_no(+)");
			query.append(" and c.status(+) = 'A' and a.company_id = f.company_id and a.facility_id = f.facility_id and f.use_code_required = 'Y'");
			query.append(" order by a.company_id,a.facility_id,a.cat_part_no,a.end_date");
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new ApprovalCodeExpiringBean());
			Collection<ApprovalCodeExpiringBean> beans= genericSqlFactory.selectQuery(query.toString(),connection);

			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar calendar = Calendar.getInstance();
			//expired today
			String expiredToday = dateFormat.format(calendar.getTime());
			//expiring in 1 day
			calendar.add(Calendar.DAY_OF_YEAR,1);
			String day1 = dateFormat.format(calendar.getTime());
			//expiring in 7 days
			calendar.add(Calendar.DAY_OF_YEAR,6);
			String day7 = dateFormat.format(calendar.getTime());
			//expiring in 14 days
			calendar.add(Calendar.DAY_OF_YEAR,7);
			String day14 = dateFormat.format(calendar.getTime());
			StringBuilder expiredMsg = new StringBuilder("");
			StringBuilder expireIn1DayMsg = new StringBuilder("");
			StringBuilder expireIn7DaysMsg = new StringBuilder("");
			StringBuilder expireIn14DaysMsg = new StringBuilder("");
			String lastCompanyId = "";
			String lastFacilityId = "";
			String lastFacilityName = "";
			boolean firstTime = true;
			Vector partApprovalOnList = new Vector();
			for (ApprovalCodeExpiringBean bean : beans) {
				String expiredDate = dateFormat.format(bean.getEndDate());

				//because there can be multiple facilities that have this setup
				//I am designing this to send an email by facility
				if (!(lastCompanyId+lastFacilityId).equals(bean.getCompanyId()+bean.getFacilityId())) {
					if (!firstTime) {
						//send email and reset variable for next facility
						//putting message together
						StringBuilder msg = new StringBuilder("");
						if (expiredMsg.length() > 0)
							msg.append("\n\n").append(expiredMsg);
						if (expireIn1DayMsg.length() > 0)
							msg.append("\n\n").append(expireIn1DayMsg);
						if (expireIn7DaysMsg.length() > 0)
							msg.append("\n\n").append(expireIn7DaysMsg);
						if (expireIn14DaysMsg.length() > 0)
							msg.append("\n\n").append(expireIn14DaysMsg);
						//sending out email notification
						if (msg.length() > 0) {
							Vector usersEmail = getUsersEmail(lastCompanyId,lastFacilityId,connection,genericSqlFactory);
							if (usersEmail.size() == 0)
								usersEmail.add("tngo@haastcm.com");
							String[] usersEmailA = new String[usersEmail.size()];
							for (int i = 0; i < usersEmail.size(); i++)
								usersEmailA[i] = (String)usersEmail.elementAt(i);

							String[] cc = {"deverror@tcmis.com"};
							MailProcess.sendEmail(usersEmailA,cc,"tcmis@tcmis.com","Approval Codes Expiring ("+lastFacilityName+")", msg.toString());
						}

						expiredMsg = new StringBuilder("");
						expireIn1DayMsg = new StringBuilder("");
						expireIn7DaysMsg = new StringBuilder("");
						expireIn14DaysMsg = new StringBuilder("");
						partApprovalOnList = new Vector();
					}else
						firstTime = false;
				}
				lastCompanyId = bean.getCompanyId();
				lastFacilityId = bean.getFacilityId();
				lastFacilityName = bean.getFacilityName();

				if(expiredToday.equals(expiredDate)) {
					if (expiredMsg.length() == 0)
						expiredMsg.append("The following parts were deactivated because approval codes expired today:");
					if (!partApprovalOnList.contains(bean.getCatPartNo() + bean.getApplicationUseGroupId())) {
						expiredMsg.append("\n\tPart:\t\t\t").append(bean.getCatPartNo());
						expiredMsg.append("\n\tApproval Code:\t\t").append(bean.getApplicationUseGroupId()).append(": ").append(bean.getUsageSubcategoryName());
					}
					//if part associated with an active bin then deactivate it
					if (bean.hasApplication() && bean.isCabinetPartInventoryStatusActive()) {
						expiredMsg.append("\n\tWork Area:\t\t").append(bean.getApplicationDesc());
						deactivateBaseline(bean, connection, genericSqlFactory);
					}
				}else if (day1.equals(expiredDate)) {
					if (expireIn1DayMsg.length() == 0)
						expireIn1DayMsg.append("The following parts are expiring in 1 day:");
					if (!partApprovalOnList.contains(bean.getCatPartNo() + bean.getApplicationUseGroupId())) {
						expireIn1DayMsg.append("\n\tPart:\t\t\t").append(bean.getCatPartNo());
						expireIn1DayMsg.append("\n\tApproval Code:\t\t").append(bean.getApplicationUseGroupId()).append(" - ").append(bean.getUsageSubcategoryName());
					}
					//if part associated with an active bin then deactivate it
					if (bean.hasApplication() && bean.isCabinetPartInventoryStatusActive()) {
						expireIn1DayMsg.append("\n\tWork Area:\t\t").append(bean.getApplicationDesc());
					}
				}else if (day7.equals(expiredDate)) {
					if (expireIn7DaysMsg.length() == 0)
						expireIn7DaysMsg.append("The following parts are expiring in 7 days:");
					if (!partApprovalOnList.contains(bean.getCatPartNo() + bean.getApplicationUseGroupId())) {
						expireIn7DaysMsg.append("\n\tPart:\t\t\t").append(bean.getCatPartNo());
						expireIn7DaysMsg.append("\n\tApproval Code:\t\t").append(bean.getApplicationUseGroupId()).append(": ").append(bean.getUsageSubcategoryName());
					}
					//if part associated with an active bin then deactivate it
					if (bean.hasApplication() && bean.isCabinetPartInventoryStatusActive()) {
						expireIn7DaysMsg.append("\n\tWork Area:\t\t").append(bean.getApplicationDesc());
					}
				}else if (day14.equals(expiredDate)) {
					if (expireIn14DaysMsg.length() == 0)
						expireIn14DaysMsg.append("The following parts are expiring in 14 days:");
					if (!partApprovalOnList.contains(bean.getCatPartNo() + bean.getApplicationUseGroupId())) {
						expireIn14DaysMsg.append("\n\tPart:\t\t\t").append(bean.getCatPartNo());
						expireIn14DaysMsg.append("\n\tApproval Code:\t\t").append(bean.getApplicationUseGroupId()).append(": ").append(bean.getUsageSubcategoryName());
					}
					//if part associated with an active bin then deactivate it
					if (bean.hasApplication() && bean.isCabinetPartInventoryStatusActive()) {
						expireIn14DaysMsg.append("\n\tWork Area:\t\t").append(bean.getApplicationDesc());
					}
				}
				partApprovalOnList.add(bean.getCatPartNo()+bean.getApplicationUseGroupId());
			}  //end of while loop

			//sending out the last facility
			//putting message together
			StringBuilder msg = new StringBuilder("");
			if (expiredMsg.length() > 0)
				msg.append("\n\n").append(expiredMsg);
			if (expireIn1DayMsg.length() > 0)
				msg.append("\n\n").append(expireIn1DayMsg);
			if (expireIn7DaysMsg.length() > 0)
				msg.append("\n\n").append(expireIn7DaysMsg);
			if (expireIn14DaysMsg.length() > 0)
				msg.append("\n\n").append(expireIn14DaysMsg);
			//sending out email notification
			if (msg.length() > 0) {
				Vector usersEmail = getUsersEmail(lastCompanyId,lastFacilityId,connection,genericSqlFactory);
				if (usersEmail.size() == 0)
					usersEmail.add("tngo@haastcm.com");
				String[] usersEmailA = new String[usersEmail.size()];
				for (int i = 0; i < usersEmail.size(); i++)
					usersEmailA[i] = (String)usersEmail.elementAt(i);

				String[] cc = {"deverror@tcmis.com"};
				MailProcess.sendEmail(usersEmailA,cc,"tcmis@tcmis.com","Approval Codes Expiring ("+lastFacilityName+")", msg.toString());
			}


		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
		}
	}  //end of method

	private Vector getUsersEmail(String companyId, String facilityId, Connection connection, GenericSqlFactory genericSqlFactory) {
		Vector usersEmail = new Vector();
		try {
			StringBuilder query = new StringBuilder("select fx_personnel_id_to_email(personnel_id) email_address from user_group_member");
			query.append(" where company_id = '").append(companyId).append("' and facility_id = '").append(facilityId).append("'");
			query.append(" and user_group_id = 'ApprovalCodeExpNotification'");
			genericSqlFactory.setBeanObject(new ApprovalCodeExpiringBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(), connection).iterator();
			while (iter.hasNext()) {
				ApprovalCodeExpiringBean bean = (ApprovalCodeExpiringBean)iter.next();
				if (!usersEmail.contains(bean.getEmailAddress()))
					usersEmail.add(bean.getEmailAddress());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return usersEmail;
	}

	private void deactivateBaseline(ApprovalCodeExpiringBean inputBean, Connection connection, GenericSqlFactory genericSqlFactory) {
		try {
			StringBuilder query = new StringBuilder("update cabinet_part_inventory set status = 'I', reorder_point = 0, stocking_level = 0");
			query.append(",remarks = 'deactivated because approval codes expired', modified_by = -1, date_modified = sysdate");
			query.append(" where company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" and facility_id = '").append(inputBean.getFacilityId()).append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
			query.append(" and catalog_id = '").append(inputBean.getCatalogId()).append("' and cat_part_no = '").append(inputBean.getCatPartNo()).append("'");
			query.append(" and part_group_no = ").append(inputBean.getPartGroupNo());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
		}
	}  //end of method

} //end of class
