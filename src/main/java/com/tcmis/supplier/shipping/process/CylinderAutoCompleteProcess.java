package com.tcmis.supplier.shipping.process;

import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.supplier.shipping.beans.CylinderManagementBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class CylinderAutoCompleteProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CylinderAutoCompleteProcess(String client) {
    super(client);
  }
	
  public CylinderAutoCompleteProcess(String client, String locale) {
    super(client,locale);
  }
  
  public Collection getSerialNumberColl(AutocompleteInputBean inputBean) throws Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CylinderManagementBean());
	    // This is for O'brien. We need to escape the ' in the name.
	    String escapedSearchText = inputBean.getSearchText().replace("'", "''");
	    escapedSearchText = escapedSearchText.replace(" ", "");
	    StringBuilder query = new StringBuilder("SELECT distinct serial_number from cylinder_tracking");
	    query.append(" where lower(serial_number) like lower('%").append(escapedSearchText).append("%') ");
	  	query.append(" and rownum <= ").append(inputBean.getRowNum());
	    query.append(" order by serial_number ");
	    return factory.selectQuery(query.toString());
  } //end of method

	public Collection getManufacturerColl(AutocompleteInputBean inputBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CylinderManagementBean());
		// This is for O'brien. We need to escape the ' in the name.
		String escapedSearchText = inputBean.getSearchText().replace("'", "''");
		escapedSearchText = escapedSearchText.replace(" ", "");
		StringBuilder query = new StringBuilder("select distinct manufacturer_id_no,manufacturer_name from vv_cylinder_manufacturer ");
		query.append(" where lower(manufacturer_id_no||manufacturer_name) like lower('%").append(escapedSearchText).append("%') ");
		query.append(" and supplier = '").append(inputBean.getSupplier()).append("'");
		query.append(" and rownum <= ").append(inputBean.getRowNum());
		query.append(" order by manufacturer_name ");
		return factory.selectQuery(query.toString());
	} //end of method

	public Collection getVendorPartNoColl(AutocompleteInputBean inputBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CylinderManagementBean());
		// This is for O'brien. We need to escape the ' in the name.
		String escapedSearchText = inputBean.getSearchText().replace("'", "''");
		escapedSearchText = escapedSearchText.replace(" ", "");
		StringBuilder query = new StringBuilder("select distinct vendor_part_no, refurb_category, conversion_group from cylinder_part_list ");
		query.append(" where lower(vendor_part_no) like lower('%").append(escapedSearchText).append("%') ");
		query.append(" and supplier = '").append(inputBean.getSupplier()).append("'");
		query.append(" and rownum <= ").append(inputBean.getRowNum());
		query.append(" order by vendor_part_no ");
		return factory.selectQuery(query.toString());
	} //end of method

	public Collection getCorrespondingNsnColl(AutocompleteInputBean inputBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CylinderManagementBean());
		// This is for O'brien. We need to escape the ' in the name.
		String escapedSearchText = inputBean.getSearchText().replace("'", "''");
		escapedSearchText = escapedSearchText.replace(" ", "");
		StringBuilder query = new StringBuilder("select distinct corresponding_nsn, refurb_category, conversion_group from cylinder_part_list ");
		query.append(" where lower(corresponding_nsn) like lower('%").append(escapedSearchText).append("%') ");
		query.append(" and supplier = '").append(inputBean.getSupplier()).append("'");
		query.append(" and vendor_part_no = '").append(inputBean.getVendorPartNo()).append("'");
		query.append(" and rownum <= ").append(inputBean.getRowNum());
		query.append(" order by vendor_part_no ");
		return factory.selectQuery(query.toString());
	} //end of method
}
