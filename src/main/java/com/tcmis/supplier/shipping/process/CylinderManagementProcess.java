package com.tcmis.supplier.shipping.process;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.EdiOrderTrackingBean;
import com.tcmis.supplier.shipping.beans.CylinderManagementBean;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process used by CylinderManagementProcess
 * @version 1.0
 *****************************************************************************/

public class CylinderManagementProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	public final String LOCATION_COLL = "LOCATION_COLL";
	public final String CONDITION_CODE_COLL = "CONDITION_CODE_COLL";
	public final String CYLINDER_STATUS_COLL = "CYLINDER_STATUS_COLL";
	protected DbManager dbManager = null;
	protected GenericSqlFactory factory = null;
	protected Vector validSearchField = new Vector();

	public CylinderManagementProcess(String client, String locale) {
		super(client, locale);
		init(new CylinderManagementBean());
		validSearchField.addElement("serial_number");
		validSearchField.addElement("manufacturer_id_no||' '||manufacturer_name");
		validSearchField.addElement("vendor_part_no");
	}

	private void init(CylinderManagementBean bean) {
		try {
			dbManager = new DbManager(client, this.getLocale());
			factory = new GenericSqlFactory(dbManager, bean);
		}
		catch (Exception ex) {
			log.error("Error initializing CylinderManagementProcess", ex);
		}
	}


	public Collection getCylinderDetail (CylinderManagementBean inputBean) throws Exception {
		Collection result = new Vector();
		try {
			//get all subcategory data
			StringBuilder query = new StringBuilder("select a.*,b.refurb_subcategory,b.date_serviced from cylinder_transaction_view a, cylinder_refurb_trans b where a.serial_number = '").append(inputBean.getSerialNumber()).append("'");
			query.append(" and a.manufacturer_id_no = '").append(inputBean.getManufacturerIdNo()).append("'");
			query.append(" and a.cylinder_tracking_id = b.cylinder_tracking_id(+)");
			query.append(" order by a.cylinder_tracking_id,b.refurb_subcategory");
			result = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}  //end of method

	public String submitSubcategory (CylinderManagementBean inputBean, Collection<CylinderManagementBean> data, PersonnelBean personnelBean) throws Exception {
		String result = "";
		Connection connection = dbManager.getConnection();
		try {
			for (CylinderManagementBean bean : data) {
				if (bean.getSelectedRow()) {
					Vector cin = new Vector();
					cin.addElement(inputBean.getCylinderTrackingId());
					cin.addElement(bean.getCylinderRefurbTransId());
					cin.addElement(inputBean.getRefurbCategory());
					cin.addElement(bean.getRefurbSubcategory());
					cin.addElement(bean.getDateServiced());
					cin.addElement(personnelBean.getPersonnelId());
					Collection cout = new Vector();
					cout.add(new Integer(java.sql.Types.VARCHAR));
					Collection resultData = factory.doProcedure(connection,"pkg_cylinder_tracking.p_cylinder_refurb_upsert", cin, cout);
					Iterator i11 = resultData.iterator();
					String val = "";
					while (i11.hasNext()) {
						val = (String) i11.next();
					}
					if (!StringHandler.isBlankString(val) && !"OK".equals(val)) {
						StringBuilder esub = new StringBuilder("Error while calling store procedure to add/update cylinder refurb subcategory data");
						StringBuilder emsg = new StringBuilder("Procedure: pkg_cylinder_tracking.p_cylinder_refurb_upsert has an error.\n").append(val).append("\n");
						MailProcess.sendEmail("tngo@haastcm.com", null, "deverror@tcmis.com", esub.toString(), emsg.toString());
						result = "An error occurred while adding or updating cylinder refurb activity, please check data and try again.";
					}
				}else if (bean.getCylinderRefurbTransId() != null) {
					Vector cin = new Vector();
					cin.addElement(bean.getCylinderRefurbTransId());
					Collection cout = new Vector();
					cout.add(new Integer(java.sql.Types.VARCHAR));
					Collection resultData = factory.doProcedure(connection,"pkg_cylinder_tracking.p_cylinder_refurb_delete", cin, cout);
					Iterator i11 = resultData.iterator();
					String val = "";
					while (i11.hasNext()) {
						val = (String) i11.next();
					}
					if (!StringHandler.isBlankString(val) && !"OK".equals(val)) {
						StringBuilder esub = new StringBuilder("Error while calling store procedure to add/update cylinder refurb subcategory data");
						StringBuilder emsg = new StringBuilder("Procedure: pkg_cylinder_tracking.p_cylinder_refurb_delete has an error.\n").append(val).append("\n");
						MailProcess.sendEmail("tngo@haastcm.com", null, "deverror@tcmis.com", esub.toString(), emsg.toString());
						result = "An error occurred while adding or updating cylinder refurb activity, please check data and try again.";
					}
				}
			}
		}catch(Exception e) {
			log.error(e);
			result = "An error occurred while adding or updating cylinder refurb activity, please check data and try again.";
		}finally {
			dbManager.returnConnection(connection);
		}

		return result;
	}  //end of method

	public Collection getEditCategory (CylinderManagementBean inputBean) throws Exception {
		Collection result = new Vector();
		try {
			//get all subcategory data
			StringBuilder query = new StringBuilder("select a.supplier,a.refurb_category,a.refurb_subcategory,b.cylinder_refurb_trans_id,b.date_serviced");
			query.append(" from vv_cylinder_refurb_subcategory a, cylinder_refurb_trans b where a.supplier = '").append(inputBean.getSupplier()).append("'");
			query.append(" and a.refurb_category = '").append(inputBean.getRefurbCategory()).append("' and a.status = 'A'");
			query.append(" and a.refurb_category = b.refurb_category(+) and a.refurb_subcategory = b.refurb_subcategory(+)");
			query.append(" and b.cylinder_tracking_id(+) = ").append(inputBean.getCylinderTrackingId());
			query.append(" order by a.display_order");
			result = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}  //end of method

	public String addNewEditCylinder (CylinderManagementBean inputBean, PersonnelBean personnelBean) throws Exception {
		String result = "";
		try {
			//warn user that he/she cannot add this cylinder because it's already existed in our system
			//and it's being work on
			if (inputBean.getCylinderTrackingId() == null) {
				StringBuilder query = new StringBuilder("select count(*) from cylinder_tracking where serial_number = ").append(SqlHandler.delimitString(inputBean.getSerialNumber()));
				query.append(" and manufacturer_id_no = '").append(inputBean.getManufacturerIdNo()).append("'");
				query.append(" and cylinder_status <> 'Out'");
				if ((new BigDecimal(factory.selectSingleValue(query.toString())).intValue() > 0))
					result = "Existed";
			}

			if (StringHandler.isBlankString(result)) {
				Vector cin = new Vector();
				cin.addElement(inputBean.getCylinderTrackingId());
				cin.addElement(inputBean.getSupplier());
				if (StringHandler.isBlankString(inputBean.getCompanyId()))
					inputBean.setCompanyId("SUPPLIER");
				cin.addElement(inputBean.getCompanyId());
				cin.addElement(inputBean.getSerialNumber());
				cin.addElement(inputBean.getManufacturerIdNo());
				cin.addElement(inputBean.getVendorPartNo());
				cin.addElement(inputBean.getCorrespondingNsn());
				cin.addElement(inputBean.getRefurbCategory());
				cin.addElement(inputBean.getConversionGroup());
				cin.addElement(inputBean.getLatestHydroTestDate());
				cin.addElement(inputBean.getStatus());
				cin.addElement(inputBean.getLocationId());
				cin.addElement(inputBean.getCylinderConditionCode());
				cin.addElement(inputBean.getCylinderStatus());
				cin.addElement(inputBean.getReturnFromLocation());
				cin.addElement(inputBean.getLastShippedDodaac());
				cin.addElement(personnelBean.getPersonnelId());
				cin.addElement(inputBean.getDocumentNumber());
				cin.addElement(inputBean.getNotes());
				Collection cout = new Vector();
				cout.add(new Integer(java.sql.Types.VARCHAR));
				Collection resultData = factory.doProcedure("pkg_cylinder_tracking.p_cylinder_tracking_upsert", cin, cout);
				Iterator i11 = resultData.iterator();
				String val = "";
				while (i11.hasNext()) {
					val = (String) i11.next();
				}
				if (!StringHandler.isBlankString(val) && !"OK".equals(val)) {
					StringBuilder esub = new StringBuilder("Error while calling store procedure to add/update cylinder data");
					StringBuilder emsg = new StringBuilder("Procedure: pkg_cylinder_tracking.p_cylinder_tracking_upsert has an error.\n").append(val).append("\n");
					MailProcess.sendEmail("tngo@haastcm.com", null, "deverror@tcmis.com", esub.toString(), emsg.toString());
					result = "An error occurred while adding or updating cylinder, please check data and try again.";
				}
			}
		}catch(Exception e) {
			log.error(e);
			result = "An error occurred while adding or updating cylinder, please check data and try again.";
		}

		return result;
	}  //end of method

	public Hashtable getVvCylinderData(CylinderManagementBean inputBean, PersonnelBean personnelBean) throws Exception {
		Hashtable result = new Hashtable();
		Connection connection = dbManager.getConnection();
		try {
			//get location
			getLocationColl(personnelBean,inputBean,connection,result);
			//get condition code
			getConditionCodeColl(inputBean,connection,result);
			//get cylinder status
			getCylinderStatus(inputBean,connection,result);
		}catch (Exception e) {
			log.error(e);
			throw e;
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}  //end of method

	public Hashtable getCylinderStatus(CylinderManagementBean inputBean) throws Exception {
		Hashtable result = new Hashtable();
		Connection connection = dbManager.getConnection();
		try {
			//get cylinder status
			getCylinderStatus(inputBean,connection,result);
		}catch (Exception e) {
			log.error(e);
			throw e;
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}  //end of method

	private void getCylinderStatus(CylinderManagementBean inputBean, Connection connection, Hashtable result) {
		try {
			StringBuilder query = new StringBuilder("");
			if ("All".equals(inputBean.getSupplier()))
				query.append("select distinct cylinder_status,display_order from vv_cylinder_status where");
			else
				query.append("select * from vv_cylinder_status where supplier = '").append(inputBean.getSupplier()).append("' and");
			query.append(" status = 'A' order by display_order");
			result.put(CYLINDER_STATUS_COLL,factory.selectQuery(query.toString(),connection));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}  //end of method

	private void getConditionCodeColl(CylinderManagementBean inputBean, Connection connection, Hashtable result) {
		try {
			StringBuilder query = new StringBuilder("select * from vv_cylinder_condition_code where supplier = '").append(inputBean.getSupplier()).append("'");
			query.append(" order by display_order");
			result.put(CONDITION_CODE_COLL,factory.selectQuery(query.toString(),connection));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}  //end of method

	private void getLocationColl(PersonnelBean personnelBean, CylinderManagementBean inputBean, Connection connection, Hashtable result) {
		try {
			StringBuilder query = new StringBuilder("select l.location_id,l.location_desc from cylinder_tracking_location ctl, location l, user_supplier_location usl where ctl.supplier = '").append(inputBean.getSupplier()).append("'");
			query.append(" and ctl.status = 'A' and ctl.company_id = l.company_id and ctl.location_id = l.location_id");
			query.append(" and ctl.supplier = usl.supplier and ctl.company_id = usl.company_id and ctl.location_id = usl.location_id and usl.personnel_id = ").append(personnelBean.getPersonnelId());
			query.append(" order by l.location_desc");
			result.put(LOCATION_COLL,factory.selectQuery(query.toString(),connection));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}  //end of method

	public Collection getCylinderLocation(PersonnelBean personnelBean, CylinderManagementBean inputBean) throws Exception {
		Collection results = new Vector();
		try {
			results = getSupplierLocationData(personnelBean,inputBean.getSupplier());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return results;
	}  //end of method

	private Collection getSupplierLocationData(PersonnelBean bean, String supplier) throws Exception {
		Collection results = new Vector();
		try {
			StringBuilder query = new StringBuilder("select s.supplier,s.supplier_name,l.location_id,l.location_desc");
			query.append(" from supplier s, user_supplier us, user_supplier_location usl, cylinder_tracking_location ctl, location l ");
			query.append(" where s.supplier = us.supplier and us.personnel_id = ").append(bean.getPersonnelId());
			if (!StringHandler.isBlankString(supplier))
				query.append(" and s.supplier = '").append(supplier).append("'");
			query.append(" and s.supplier = ctl.supplier and us.company_id = usl.company_id and us.supplier = usl.supplier and us.personnel_id = usl.personnel_id");
			query.append(" and usl.company_id = ctl.company_id and usl.location_id = ctl.location_id ");
			query.append(" and l.company_id = ctl.company_id and l.location_id = ctl.location_id ");
			query.append(" order by s.supplier_name,l.location_desc");
			results = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return results;
	}  //end of method

	public Vector buildSupplierLocationObj (PersonnelBean personnelBean) throws Exception {
		Vector results = new Vector();
		Vector<CylinderManagementBean> data = (Vector) getSupplierLocationData(personnelBean,"");
		String currentSupplier = "";
		for (CylinderManagementBean bean : data) {
			if (currentSupplier.equals(bean.getSupplier())) {
				CylinderManagementBean supplierBean = (CylinderManagementBean) results.lastElement();
				Vector<CylinderManagementBean> locationList = (Vector) supplierBean.getLocationList();
				CylinderManagementBean locationBean = new CylinderManagementBean();
				locationBean.setLocationId(bean.getLocationId());
				locationBean.setLocationDesc(bean.getLocationDesc());
				locationList.add(locationBean);
			} else {
				CylinderManagementBean supplierBean = new CylinderManagementBean();
				supplierBean.setSupplier(bean.getSupplier());
				supplierBean.setSupplierName(bean.getSupplierName());
				Collection locationList = new Vector();
				CylinderManagementBean locationBean = new CylinderManagementBean();
				locationBean.setLocationId(bean.getLocationId());
				locationBean.setLocationDesc(bean.getLocationDesc());
				locationList.add(locationBean);
				supplierBean.setLocationList(locationList);
				results.add(supplierBean);
			}
			currentSupplier = bean.getSupplier();
		}
		return results;
	}  	//end of method


	public Collection getSearchResult(CylinderManagementBean inputBean) throws Exception {
		Collection results = new Vector();
		try {
			StringBuilder whereClause = new StringBuilder("");
			if (!StringHandler.isBlankString(inputBean.getCylinderStatus())) {
				if (!StringHandler.isBlankString(whereClause.toString()))
					whereClause.append(" and");
				whereClause.append(" cylinder_status = ").append(SqlHandler.delimitString(inputBean.getCylinderStatus()));
			}

			if (!StringHandler.isBlankString(inputBean.getSupplier())) {
				if (!StringHandler.isBlankString(whereClause.toString()))
					whereClause.append(" and");
				whereClause.append(" supplier = ").append(SqlHandler.delimitString(inputBean.getSupplier()));
			}

			if (!StringHandler.isBlankString(inputBean.getLocationId())) {
				if (!StringHandler.isBlankString(whereClause.toString()))
					whereClause.append(" and");
				whereClause.append(" location_id = ").append(SqlHandler.delimitString(inputBean.getLocationId()));
			}

			if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
				String mode = inputBean.getSearchMode();
				String field = inputBean.getSearchField();
				if (!validSearchField.contains(inputBean.getSearchField()))
					field = "serial_number";
				if (mode.equals("is")) {
					if (!StringHandler.isBlankString(whereClause.toString()))
						whereClause.append(" and ");
					whereClause.append(field).append(" = ").append(SqlHandler.delimitString(inputBean.getSearchArgument()));
				}

				if (mode.equals("contains")) {
					if (!StringHandler.isBlankString(whereClause.toString()))
						whereClause.append(" and");
					whereClause.append(" lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(inputBean.getSearchArgument())).append("%')");
				}
			}
			StringBuilder query = new StringBuilder("select * from cylinder_transaction_view where (cylinder_tracking_id,serial_number,manufacturer_id_no) in ");
			query.append("(select max(cylinder_tracking_id),serial_number,manufacturer_id_no from cylinder_tracking");
			//inner clause
			if (!StringHandler.isBlankString(whereClause.toString()))
				query.append(" where").append(whereClause);
			query.append(" group by serial_number,manufacturer_id_no)");
			//outter clause
			if (!StringHandler.isBlankString(whereClause.toString()))
				query.append(" and").append(whereClause);
			query.append(" order by supplier_name,location_desc,serial_number");

			results = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}

		return results;
	}  //end of method

	public ExcelHandler createExcelReport(CylinderManagementBean inputBean) throws NoDataException, BaseException, Exception {
		Vector<CylinderManagementBean> data = (Vector)getSearchResult(inputBean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.supplier")+":"+inputBean.getSupplierName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.location")+":"+inputBean.getLocationDesc(),1,3);
		pw.addRow();
		String tmpVal = "";
		if (!StringHandler.isBlankString(inputBean.getSearchArgument()))
			tmpVal = inputBean.getSearchFieldName() + " " + inputBean.getSearchModeName() + " "+ inputBean.getSearchArgument();
		pw.addTdRegionBold(library.getString("label.search")+tmpVal,1,3);

		if (inputBean.isIncludeInactive()) {
			pw.addRow();
			pw.addTdRegionBold(library.getString("label.onlydisplaycatalogadd"), 1, 3);
		}

		pw.addRow();
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.datereceived",
				"label.serialnumber",
				"label.cylinderstatus",
				"label.dotrating",
				"label.dimensions",
				"label.gastype",
				"label.nsn8120",
				"label.nsn6830",
				"label.conversiongroup",
				"label.conditioncode",
				"label.capacity",
				"label.manufacturer",
				"label.latesthydrotestdate",
				"label.returnfromlocation",
				"label.lastshippeddodaac",
				"label.datesentout",
				"label.datedisposed",
				"label.location",
				"label.receivedby",
				"label.documentnumber",
				"label.lastupdated",
				"label.lastupdatedby",
				"label.notes"
				};
		/*This array defines the type of the excel column.
 		  0 means default depending on the data type. */
		int[] types = {
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		/*This array defines the default width of the column when the Excel file opens.
 		  0 means the width will be default depending on the data type.*/
		int[] widths = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		/*This array defines the horizontal alignment of the data in a cell.
 		  0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

		for(CylinderManagementBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getDateReceived());
			pw.addCell(bean.getSerialNumber());
			pw.addCell(bean.getCylinderStatus());
			pw.addCell(bean.getDotRating());
			pw.addCell(bean.getDimensions());
			pw.addCell(bean.getGasType());
			pw.addCell(bean.getVendorPartNo());
			pw.addCell(bean.getCorrespondingNsn());
			pw.addCell(bean.getConversionGroup());
			pw.addCell(bean.getCylinderConditionCode());
			pw.addCell(bean.getCapacity());
			pw.addCell(bean.getManufacturerName());
			pw.addCell(bean.getLatestHydroTestDate());
			pw.addCell(bean.getReturnFromLocation());
			pw.addCell(bean.getLastShippedDodaac());
			pw.addCell(bean.getDateSentOut());
			pw.addCell(bean.getDateDisposed());
			pw.addCell(bean.getLocationDesc());
			pw.addCell(bean.getReceivedByName());
			pw.addCell(bean.getDocumentNumber());
			pw.addCell(bean.getLastUpdated());
			pw.addCell(bean.getLastUpdatedByName());
			pw.addCell(bean.getNotes());
		}
		return pw;
	}  //end of method

} //end of class