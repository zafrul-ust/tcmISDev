package com.tcmis.internal.supply.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.internal.supply.beans.BuyPageViewBean;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class PoShelfLifeQuantityCheckProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PoShelfLifeQuantityCheckProcess(String client) {
		super(client);
	}

	public PoShelfLifeQuantityCheckProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection<BuyPageViewBean> getType1(String po, String shelfLife, String itemId) throws BaseException {
		factory.setBean(new BuyPageViewBean());
		String query = "select rli.PR_NUMBER MR_NUMBER, rli.LINE_ITEM MR_LINE_ITEM,fx_personnel_id_to_name (pr.customer_service_rep_id) csr_name from mr_allocation mra, request_line_item rli, purchase_request pr"
		+ " where mra.company_id = pr.company_Id and mra.pr_number = pr.pr_number and mra.company_id = rli.company_Id and mra.pr_number = rli.pr_number and mra.line_item = rli.line_item and mra.doc_type = 'PO' and mra.doc_num = "
				+ po + " and mra.item_id = " + itemId + " and rli.REMAINING_SHELF_LIFE_PERCENT > " + shelfLife; 
		return factory.selectQuery(query);
	}
	
	public Collection<BuyPageViewBean> getType2(String po, String shelfLife,String itemId) throws BaseException {
		factory.setBean(new BuyPageViewBean());
		//String query = "select MR_NUMBER, MR_LINE_ITEM, CSR_NAME from associate_pr_view where REMAINING_SHELF_LIFE_PERCENT > "+shelfLife+" and radian_po = "+po+" and item_id = "+itemId+" and status not in ('Closed','Cancel')";       
        String query = " select rli.PR_NUMBER MR_NUMBER, rli.LINE_ITEM MR_LINE_ITEM,fx_personnel_id_to_name (pr.customer_service_rep_id) csr_name from "
         + "mr_allocation mra, request_line_item rli, purchase_request pr , (select pr_number,item_id from buy_order where REMAINING_SHELF_LIFE_PERCENT > "+shelfLife+" and radian_po = "+po+" and item_id = "+itemId+" and status not in ('Closed','Cancel')) bo "
         + "where mra.doc_type = 'PR' and mra.doc_num = BO.pr_number and  mra.item_id = bo.item_id "
         + "and mra.company_id = pr.company_Id and mra.pr_number = pr.pr_number and mra.company_id = rli.company_Id and mra.pr_number = rli.pr_number and mra.line_item = rli.line_item "
         + "and rli.REMAINING_SHELF_LIFE_PERCENT > "+shelfLife+"";
        return factory.selectQuery(query);
	}
}
