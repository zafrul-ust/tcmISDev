package com.tcmis.internal.supply.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.supply.beans.PoLineBean;

/******************************************************************************
 * Process for Find Supplier
 * @version 1.0
 *****************************************************************************/
public class FindPoLineProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FindPoLineProcess(String client) {
    super(client);
  }
	
  public FindPoLineProcess(String client, String locale) {
    super(client,locale);
  }
  
  public Collection getPoLine(PoLineBean poLineBean) throws BaseException, Exception {
	  	DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoLineBean());
	    
	    StringBuilder query = new StringBuilder("SELECT distinct radian_po, po_line, quantity, unit_price ");
	    query.append(" FROM po_line WHERE radian_po = ").append(poLineBean.getRadianPo()).append(" and po_line = ").append(poLineBean.getPoLine());
	    
	    return factory.selectQuery(query.toString());
  }
  
  public String getBuyOrderPoLineQty(MaterialRequestInputBean inputBean) throws BaseException {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoLineBean());
	    
	    StringBuilder query = new StringBuilder("SELECT nvl(sum(p.quantity), 0) ")
	    	 .append(" FROM po_line p")
	         .append(" WHERE exists (select * from buy_order b where mr_number = ").append(inputBean.getPrNumber())
	    	 .append("       and mr_line_item = '").append(inputBean.getLineItem()).append("' and p.item_id = b.ITEM_ID and p.radian_po = b.radian_po)");
	    
	    return factory.selectSingleValue(query.toString());
	    
  }

}
