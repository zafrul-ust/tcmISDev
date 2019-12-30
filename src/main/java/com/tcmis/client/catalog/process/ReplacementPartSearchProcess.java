package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import java.util.HashMap;
import java.math.BigDecimal;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvPkgStyleBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.catalog.beans.ReplacementPartSearchInputBean;
import com.tcmis.client.catalog.beans.FacItemBean;
import com.tcmis.client.catalog.beans.FacItemCpigUseApprovalBean;

/******************************************************************************
 * Process used by ReplacementPartSearchProcess
 * @version 1.0
 *****************************************************************************/

public class ReplacementPartSearchProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public ReplacementPartSearchProcess(String client)
	{
		super(client);
	}

	public Object[] createRelationalObjects(Vector<FacItemCpigUseApprovalBean> bv) {
		// calculate m1 and m2
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		String catPartNo = null;

		for (FacItemCpigUseApprovalBean tmpBean:bv) {
			catPartNo = tmpBean.getCatPartNo();

			if (m1.get(catPartNo) == null) {
				i1 = new Integer(0);
				m1.put(catPartNo, i1);
				map = new HashMap();
				m2.put(catPartNo, map);
			}
			i1 = (Integer) m1.get(catPartNo);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(catPartNo, i1);

			String item = "item" + tmpBean.getItemId().toString();

			if (map.get(item) == null) {
				i2 = new Integer(0);
				map.put(item, i2);
			}
			i2 = (Integer) map.get(item);
			i2 = new Integer(i2.intValue() + 1);
			map.put(item, i2);
		}
		Object[] objs = {bv,m1,m2};
		return objs;
	} //end of method

	public Collection getReplacementPartBeanCollection(ReplacementPartSearchInputBean inputBean, PersonnelBean personnelBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
        Connection connection = dbManager.getConnection();
        GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FacItemCpigUseApprovalBean());
        Collection result = new Vector(0);
        try {
            StringBuilder query = new StringBuilder("select fcpv.cat_part_no,fcpv.part_description,fcpv.item_id,fcpv.packaging,fcpv.part_group_no,fcpv.tier_II_temperature_code");
            query.append(" from facility_catalog_part_view fcpv");
            query.append(" where fcpv.company_id = '").append(inputBean.getCompanyId()).append("'");
            query.append(" and fcpv.facility_id = '").append(inputBean.getFacilityId()).append("'");
            query.append(" and fcpv.catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
            query.append(" and fcpv.catalog_id = '").append(inputBean.getCatalogId()).append("'");
            if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
                query.append(" and (").append(doSearchLogic(inputBean.getSearchArgument())).append(")");
            }

            if ("clientCabinetBin".equalsIgnoreCase(inputBean.getSourcePage()) && isUseApprovalRequired(factory,connection,inputBean)) {
                //if approval code required
                if (isUseCodeRequired(factory,connection,inputBean)) {
                    query.append(" and exists (select ua.company_id from use_approval ua,application_use_group_member augm");
                    query.append(" where fcpv.company_id = ua.company_id and fcpv.facility_id = ua.facility_id");
                    query.append(" and fcpv.catalog_company_id = ua.catalog_company_id and fcpv.catalog_id = ua.catalog_id and fcpv.cat_part_no = ua.fac_part_no");
                    query.append(" and fcpv.part_group_no = ua.part_group_no and ua.approval_status in ('approved','Super Approved')");
                    query.append(" and ua.company_id  = augm.company_id and ua.facility_id = augm.facility_id and");
                    //check to see if work area is "controlled"
                    if (isWorkAreaControlled(factory,connection,inputBean)) {
                        query.append(" ua.application = augm.application and ua.application_use_group_id = augm.application_use_group_id and");
                        query.append(" ua.application = '").append(inputBean.getApplication()).append("' and");
                    }else {
                        query.append(" ((((ua.application = 'All') and (augm.application = '").append(inputBean.getApplication()).append("')) and");
                        query.append(" (ua.application_use_group_id = augm.application_use_group_id)) or");
                        query.append(" ((ua.application = augm.application) and (ua.application_use_group_id = augm.application_use_group_id) and");
                        query.append(" (ua.application = '").append(inputBean.getApplication()).append("'))) and");
                    }
                    query.append(" nvl(ua.start_date, sysdate - 1) < sysdate and nvl(ua.expire_date, sysdate + 1) > sysdate");
                    query.append(")");
                //else approval code not required
                }else {
                    query.append(" and exists (select ua.company_id from use_approval ua");
                    query.append(" where fcpv.company_id = ua.company_id and fcpv.facility_id = ua.facility_id");
                    query.append(" and fcpv.catalog_company_id = ua.catalog_company_id and fcpv.catalog_id = ua.catalog_id and fcpv.cat_part_no = ua.fac_part_no");
                    query.append(" and fcpv.part_group_no = ua.part_group_no and ua.approval_status in ('approved','Super Approved')");
                    if (!StringHandler.isBlankString(inputBean.getApplication())) {
                        query.append(" and ua.application in ('All','").append(inputBean.getApplication()).append("'");
                        if(personnelBean.isFeatureReleased("WorkAreaGroupUseApproval", inputBean.getFacilityId(), inputBean.getCompanyId())){
                            query.append(", (select reporting_entity_id from fac_loc_app where facility_id = '").append(inputBean.getFacilityId()).append("'");
                            query.append(" and application = '").append(inputBean.getApplication()).append("')");
                        }
                        query.append(")");
                    }
                    query.append(")");
                }
            }
            query.append(" order by fcpv.cat_part_no,fcpv.part_description,fcpv.item_id");
            result = factory.selectQuery(query.toString(),connection);
        }catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}
        return result;
    } //end of method


    private boolean isUseApprovalRequired(GenericSqlFactory factory, Connection connection, ReplacementPartSearchInputBean inputBean) {
        boolean result = false;
        try {
            StringBuilder query = new StringBuilder("select count(*) from ");
            if ("TCM_OPS".equalsIgnoreCase(getClient()))
                query.append("customer.catalog_facility");
            else
                query.append("catalog_facility");
            query.append(" where nvl(cabinet_bin_requires_use_app,'N') = 'Y' and company_id = '").append(inputBean.getCompanyId()).append("'");
            query.append(" and facility_id = '").append(inputBean.getFacilityId()).append("'");
            query.append(" and catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
            query.append(" and catalog_id = '").append(inputBean.getCatalogId()).append("'");
            BigDecimal tmpCount = new BigDecimal(factory.selectSingleValue(query.toString(),connection));
            if (tmpCount.intValue() > 0)
                result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    } //end of method

    private boolean isWorkAreaControlled(GenericSqlFactory factory, Connection connection,  ReplacementPartSearchInputBean inputBean) {
		boolean result = false;
		try {
            StringBuilder query = new StringBuilder("select count(*) from fac_loc_app where company_id = '").append(inputBean.getCompanyId());
            query.append("' and facility_id = '").append(inputBean.getFacilityId()).append("' and application = '").append(inputBean.getApplication());
            query.append("' and nvl(specific_use_approval_required,'N') = 'Y'");
            BigDecimal tmpCount = new BigDecimal(factory.selectSingleValue(query.toString(),connection));
            if (tmpCount.intValue() > 0)
                result = true;
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

    private boolean isUseCodeRequired(GenericSqlFactory factory, Connection connection, ReplacementPartSearchInputBean inputBean) {
        boolean result = false;
        try {
            StringBuilder query = new StringBuilder("select count(*) from ");
            if ("TCM_OPS".equalsIgnoreCase(getClient()))
                query.append("customer.facility");
            else
                query.append("facility");
            query.append(" where use_code_required = 'Y' and company_id = '").append(inputBean.getCompanyId()).append("' and");
            query.append(" facility_id = '").append(inputBean.getFacilityId()).append("'");
            BigDecimal tmpCount = new BigDecimal(factory.selectSingleValue(query.toString(),connection));
            if (tmpCount.intValue() > 0)
                result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    } //end of method

    public String doSearchLogic(String search) {
		Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " lower(fcpv.cat_part_no||' '||fcpv.part_description||' '||fcpv.item_id) like lower('%" + SqlHandler.validQuery(search) + "%')";
			return result;
		}

		//contains operation in search text
		result += " lower(fcpv.cat_part_no||' '||fcpv.part_description||' '||fcpv.item_id) like lower('%" + SqlHandler.validQuery(likes.elementAt(0).toString().trim()) + "%') ";
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			result += " " + op + " lower(fcpv.cat_part_no||' '||fcpv.part_description||' '||fcpv.item_id) " + lk + " lower('%" + SqlHandler.validQuery(searchS) + "%') ";
		}

		return result;
	}

 public ExcelHandler getExcelReport(ReplacementPartSearchInputBean inputBean, PersonnelBean personnelBean) throws NoDataException, BaseException, Exception {
	Vector<FacItemBean> data = (Vector<FacItemBean>) getReplacementPartBeanCollection(inputBean, personnelBean);
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();

	//	 write column headers
	pw.addRow();
	pw.addCell(library.getString("label.search"));
	pw.addCell(inputBean.getSearchArgument());
	pw.addRow();
	pw.addRow();
	 
	 /*Pass the header keys for the Excel.*/
	 String[] headerkeys = {
			   "label.part",
			   "label.description"
	 			};
	/*This array defines the type of the excel column.
 	0 means default depending on the data type. */
	int[] types = {0,0};
	/*This array defines the default width of the column when the Excel file opens.
 	0 means the width will be default depending on the data type.*/
	int[] widths = null;
	/*This array defines the horizontal alignment of the data in a cell.
 	0 means excel defaults the horizontal alignemnt by the data type.*/
	int[] horizAligns = null;
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

	//	 now write data
	//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	for(FacItemBean bean: data) {
		 pw.addRow();
		 pw.addCell(bean.getFacPartNo());
		 pw.addCell(bean.getPartDescription());
	}
	return pw;
} //end of method
 
} //end of class
