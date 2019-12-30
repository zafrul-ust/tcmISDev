package com.tcmis.internal.supply.process;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;

public class PoUpdatePaymentTermsProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PoUpdatePaymentTermsProcess(String client, Locale locale) {
		super(client, locale);
	}

	public PoUpdatePaymentTermsProcess(String client) {
		super(client);
	}
	
	public String getPaymentTerms(String inventoryGroup, String supplierId) 
		throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory sqlFactory = new GenericSqlFactory(dbManager);
		String results = "";
		
		try {			
			StringBuilder query = new StringBuilder();
			query.append(" select fx_supplier_ig_payment_term" );
			query.append("(").append(SqlHandler.delimitString(supplierId)).append(",").append(SqlHandler.delimitString(inventoryGroup)).append(")");
			query.append(" PAYMENT_TERMS from dual");
			
			String paymentTerms = sqlFactory.selectSingleValue(query.toString());
			results = paymentTerms;
		}
		catch (Exception e) {
			String errorMsg = "Error getting the supplier payment method: " + supplierId + "-" + inventoryGroup + "";
		}
		
		return results;	
	}
}
