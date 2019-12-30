package com.tcmis.client.dla.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.dla.beans.DlaGasOrderCorrectionsBean;
import com.tcmis.client.dla.beans.DlaInventoryAdjustmentBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;


public class DlaInventoryAdjustmentProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public DlaInventoryAdjustmentProcess(String client, Locale locale) {
		super(client, locale);
	}

	public DlaInventoryAdjustmentProcess(String client) { 
		super(client);
	}
	
	@SuppressWarnings("unchecked")
	public String getUnitOfSale(String catPartNo) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DlaGasOrderCorrectionsBean());
		return factory.selectSingleValue("select trading_partner_uom from tcm_ops.trading_partner_uom where unit_of_sale in ( select unit_of_sale from catalog_part_unit_of_sale where cat_part_no = '" + catPartNo + "')");
	}
	
	public Collection<DlaInventoryAdjustmentBean> getSearchResult(DlaInventoryAdjustmentBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DlaInventoryAdjustmentBean());
		SearchCriteria criteria = new SearchCriteria();
		
		if(!StringHandler.isBlankString(input.getCatPartNo()))
			criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, input.getCatPartNo());
		
		/* Searching for order FROM date */
		if (input.hasDateInsertedFromDate()) {
			criteria.addCriterion("dateInserted", SearchCriterion.FROM_DATE, input.getDateInsertedFromDate());
		}
		/* Searching for order TO date */
		if (input.hasDateInsertedToDate()) {
			criteria.addCriterion("dateInserted", SearchCriterion.TO_DATE, input.getDateInsertedToDate());
		}

		criteria.addCriterion("transactionType", SearchCriterion.EQUALS, "947");

		return factory.select(criteria, null, "customer_mro_pre_stage");
	
	}
	
	public Collection update(Vector<DlaInventoryAdjustmentBean> inputLines) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DlaInventoryAdjustmentBean());

		try {
			for (DlaInventoryAdjustmentBean inputBean : inputLines) {
				if(inputBean.getLineStatus().equalsIgnoreCase("delete"))
				{
						factory.deleteInsertUpdate(	"update customer_mro_pre_stage set transaction_type = '947-D' where company_id = 'USGOV' and TRANSACTION_REF_NUM = '" + inputBean.getTransactionRefNum() + "'");
				}
				else if (inputBean.getLineStatus().equalsIgnoreCase("new"))
				{ 
						String julianDate = factory.selectSingleValue("select to_char(sysdate, 'yddd') from dual");
						String nextVal = factory.selectSingleValue("select customer_po_load_seq.nextval from dual");
						String transactionRefNumCount = factory.selectSingleValue("select count(*) from customer_mro_pre_stage where transaction_ref_num like 'UY9429" + julianDate + "%'");
						Integer curRefNum = Integer.parseInt(transactionRefNumCount);
						transactionRefNumCount = Integer.toString((curRefNum + 1));
						while(transactionRefNumCount.length() < 4)
							transactionRefNumCount = "0" + transactionRefNumCount; 
						factory.deleteInsertUpdate("insert into customer_mro_pre_stage (company_id, load_id, load_line ,transaction_type, status, date_inserted ,transport, transporter_account, trading_partner, trading_partner_id, cat_part_no, quantity, uom, date_issued, storage_area_id, transaction_ref_num, owner_icp_id, transaction_code, doc_id_code, supply_condition_code, storage_area_qualifier, owner_icp_qualifier, date_processed, reason_code, inventory_transaction_code, previous_condition_code, ownership_code, previous_ownership_code, amendment, transaction_ref_line_num) values ('USGOV', " + nextVal + ", 1, '947', '947 CREATED', sysdate, 'VAN', 'DAASC', 'dla', 'DTDN','"+ inputBean.getCatPartNo() +"', "+ inputBean.getQuantity() +", '"+inputBean.getUom() +"', sysdate, 'SXA', 'UY9429" + julianDate + transactionRefNumCount + "', 'SMS', 'NU', '"+inputBean.getDocIdCode()+"', '" + inputBean.getSupplyConditionCode() + "', 'M4', 'M4', sysdate, 'AC', 'DU', '"+inputBean.getPreviousConditionCode()+"', 'A', 'AA', 0, 1)");	
				}
			}
		}
		catch (Exception e) {
			errorMsg = "Error updating status, date_sent, status_detail for release_num ";
			errorMessages.add(errorMsg);
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
}
