package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

//import java.io.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.SpecialChargeManagementInputBean;
import com.tcmis.internal.supply.beans.SpecialChargePoLookupBean;

import com.tcmis.common.util.SqlHandler;

import com.tcmis.common.exceptions.BaseException;

public class SpecialChargeManagementProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	
	public SpecialChargeManagementProcess(String client) {
		super(client);
	}  
	
	public SpecialChargeManagementProcess(String client,String locale) {
	    super(client,locale);
    }
	
	public Collection<SpecialChargePoLookupBean> getSearchResult(SpecialChargeManagementInputBean inputBean) {
		StringBuilder query = new StringBuilder("SELECT scpl.*, s.supplier_name, fx_personnel_id_to_name(scpl.inserted_by) inserted_by_name, fx_personnel_id_to_name(scpl.last_updated_by) last_updated_by_name FROM SPECIAL_CHARGE_PO_LOOKUP scpl, supplier s");
		query.append(" WHERE scpl.supplier = s.supplier");
		
		if (inputBean.getFromDate() != null)
			query.append(" AND date_inserted >= ").append(getSqlString(inputBean.getFromDate()));
		if (inputBean.getToDate() != null)
			query.append(" AND date_inserted <= ").append(getSqlString(inputBean.getToDate()));
		
		String arg = inputBean.getSearchArgument();
		if (!StringHandler.isBlankString(arg)) {
			String mode = inputBean.getSearchMode();
			String field = "scpl.".concat(inputBean.getSearchField());
			if (mode.equals("is"))
				query.append(" AND ").append(field).append(" = ").append(SqlHandler.delimitString(arg));
			if (mode.equals("contains"))
				query.append(" AND lower(").append(field).append(") like LOWER('%").append(SqlHandler.validQuery(arg)).append("%')");
			if (mode.equals("startsWith"))
				query.append(" AND lower(").append(field).append(") like LOWER('").append(SqlHandler.validQuery(arg)).append("%')");
			if (mode.equals("endsWith"))
				query.append(" AND lower(").append(field).append(") like LOWER('%").append(SqlHandler.validQuery(arg)).append("')");
		}
		query.append(" order by date_inserted desc");
		
		factory.setBean(new SpecialChargePoLookupBean());
		try {
			return factory.selectQuery(query.toString());
		} catch (BaseException e) {
			log.error(e);
			return null;
		}
	}
	
	public void changePoStatus(Collection<SpecialChargePoLookupBean> beanColl, BigDecimal personnelId) {
		Iterator<SpecialChargePoLookupBean> itr = beanColl.iterator();
		while (itr.hasNext()) {
			SpecialChargePoLookupBean bean = itr.next();
			StringBuilder query = new StringBuilder("UPDATE SPECIAL_CHARGE_PO_LOOKUP SET");
			query.append(" status = ").append(SqlHandler.delimitString("I".equalsIgnoreCase(bean.getStatus()) ? "A" : "I"));
			query.append(" ,last_updated_by = ").append(personnelId.toPlainString());
			query.append(" ,last_updated_date = sysdate");
			query.append(" WHERE radian_po = ").append(bean.getRadianPo());
			query.append(" AND supplier = ").append(SqlHandler.delimitString(bean.getSupplier()));
			
			try {
				factory.deleteInsertUpdate(query.toString());
			} catch (BaseException e) {
				log.error(e);
			}
		}
	}
}




