package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.client.common.beans.DeliveryScheduleChangeDataBean;
import com.tcmis.client.common.beans.DeliveryScheduleChangeHeaderViewBean;
import com.tcmis.client.common.beans.DeliveryScheduleFacilityBean;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

public class DeliveryScheduleChangeProcess extends BaseProcess {

	public DeliveryScheduleChangeProcess(String client) {
		super(client);
	}  
  
	public DeliveryScheduleChangeProcess(String client,String locale) {
	    super(client,locale);
    }
	
	public Collection<DeliveryScheduleChangeDataBean> getDeliveryView(String prNumber, String lineItem) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
	    StringBuilder query = new StringBuilder();
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
	    query.append("select distinct x.pr_number,x.line_item,x.date_to_deliver,yy.original_qty, zz.revised_qty, x.expedite_approval, x.csr_approval, x.updated_date, fx_personnel_id_to_name(x.updated_by) updated_by_name, ");
	    query.append("fx_personnel_id_to_name(x.expedite_approval) expedite_approval_name , x.expedite_approval_date, ");
		query.append("fx_personnel_id_to_name(x.csr_approval) csr_approval_name, x.csr_approval_date ");
	    query.append("from ").append(prefix).append("delivery_schedule_change x, ");
	    query.append("(select xx.pr_number, xx.line_item, xx.original_qty, xx.date_to_deliver from ").append(prefix).append("delivery_schedule_change xx, ");
	    query.append("(select pr_number,line_item,date_to_deliver,min(updated_date) updated_date from ").append(prefix).append("delivery_schedule_change ");
	    query.append("where pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
	    query.append(" group by pr_number,line_item,date_to_deliver) y ");
	    query.append("where xx.pr_number=y.pr_number and xx.line_item=y.line_item and xx.updated_date=y.updated_date and xx.date_to_deliver=y.date_to_deliver) yy, ");
	    query.append("(select xx.pr_number, xx.line_item, xx.revised_qty, xx.date_to_deliver from ").append(prefix).append("delivery_schedule_change xx, ");
	    query.append("(select pr_number,line_item,date_to_deliver,max(updated_date) updated_date from ").append(prefix).append("delivery_schedule_change ");
	    query.append("where pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
	    query.append(" group by pr_number,line_item, date_to_deliver) z ");
	    query.append("where xx.pr_number=z.pr_number and xx.line_item=z.line_item and xx.updated_date=z.updated_date and xx.date_to_deliver=z.date_to_deliver) zz ");
	    query.append("where x.pr_number = yy.pr_number and x.line_item = yy.line_item ");
	    query.append("and x.pr_number = zz.pr_number and x.line_item = zz.line_item ");
	    query.append("and x.date_to_deliver=yy.date_to_deliver ");
	    query.append("and x.date_to_deliver=zz.date_to_deliver ");
	    query.append("and yy.original_qty <> zz.revised_qty ");
	    query.append("order by x.date_to_deliver, yy.original_qty");
	    
	    Collection<DeliveryScheduleChangeDataBean> deliveryColl = factory.selectQuery(query.toString());
	    
	    return deliveryColl;
	}
	
	public String getReviewMessage(String prNumber, String lineItem) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
	    StringBuilder query = new StringBuilder("select count(*) from ").append(prefix).append("delivery_schedule_change where (csr_approval is null or lower(csr_approval) != 'y') ");
	    query.append("and pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
	    
	    BigDecimal csr = new BigDecimal(factory.selectSingleValue(query.toString()));
	    
	    query = new StringBuilder("select count(*) from ").append(prefix).append("delivery_schedule_change where (expedite_approval is null or lower(expedite_approval) != 'y') ");
	    query.append("and pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
	    
	    BigDecimal expedite = new BigDecimal(factory.selectSingleValue(query.toString()));
	    
	    String message = "";
	    if (csr.intValue() > 0) {
	    	//needs csr
	    	if (expedite.intValue() > 0) {
	    		message = "This schedule needs Buyer and CSR review.";
	    	}
	    	else {
	    		message = "This schedule needs Buyer review.";
	    	}
	    }
	    else if (expedite.intValue() > 0) {
	    	message = "This schedule needs CSR review.";
	    }
	    
	    return message;
	}
	
	public Collection<DeliveryScheduleChangeHeaderViewBean> getHeaderInfo(String prNumber, String lineItem) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeHeaderViewBean());
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
	    StringBuilder query = new StringBuilder();
	    query.append("select pr_number, line_item, item_id, fac_part_no,quantity,item_desc, packaging, facility_name, application_display, full_name, phone, email ");
	    query.append("from ").append(prefix).append("schedule_change_header_view where pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
	    
	    Collection<DeliveryScheduleChangeHeaderViewBean> headerBeanColl = factory.selectQuery(query.toString());
	    
	    return headerBeanColl;
	}
	
	public Collection<DeliveryScheduleChangeDataBean> getDeliveryScheduleFinal(DeliveryScheduleChangeHeaderViewBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
	    StringBuilder query = new StringBuilder();
	    query.append("select pr_number, line_item, date_to_deliver,qty from ").append(prefix).append("delivery_schedule ");
	    query.append("where pr_number=").append(inputBean.getPrNumber()).append(" and line_item=").append(inputBean.getLineItem());
	    query.append(" order by date_to_deliver");
	    
	    Collection<DeliveryScheduleChangeDataBean> deliveryColl = factory.selectQuery(query.toString());
	    
	    return deliveryColl;
	}
	
	public boolean isMember(BigDecimal pid, String facId, String groupID) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
	    boolean flag = false;
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
		String query = "select count(*) from "+prefix+"user_group_member where personnel_id = "+pid+" and user_group_id = '"+groupID+"'"+
				" and facility_id in ('"+facId+"','All')";
		String count = factory.selectSingleValue(query.toString());
		BigDecimal numCount = new BigDecimal(count);
		if (numCount.intValue() > 0) {
			flag = true;
		}
		
		return flag;
	}
	
	public String getPreferredWarehouse(DeliveryScheduleChangeHeaderViewBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
		StringBuilder query = new StringBuilder();
		query.append("select preferred_warehouse from ").append(prefix).append("facility f, request_line_item rli, purchase_request pr ");
		query.append("where f.facility_id = pr.facility_id and rli.pr_number = pr.pr_number and pr.pr_number = ").append(inputBean.getPrNumber());
		
		String preferredWarehouse = factory.selectSingleValue(query.toString());
		
		return preferredWarehouse;
	}
	
	public void expediterApprove(DeliveryScheduleChangeHeaderViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
        String query = "update "+prefix+"delivery_schedule_change set expedite_approval = "+personnelBean.getPersonnelId()+
                       ",expedite_approval_date = sysdate where pr_number = "+inputBean.getPrNumber()+" and line_item = '"+inputBean.getLineItem()+"'";

       factory.deleteInsertUpdate(query);
	}
	
	public void csrApprove(DeliveryScheduleChangeHeaderViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
	    String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
	    
        String query = "update "+prefix+"delivery_schedule_change set csr_approval = "+personnelBean.getPersonnelId()+
                       ",csr_approval_date = sysdate where pr_number = "+inputBean.getPrNumber()+" and line_item = '"+inputBean.getLineItem()+"'";

        factory.deleteInsertUpdate(query);
	}
}
