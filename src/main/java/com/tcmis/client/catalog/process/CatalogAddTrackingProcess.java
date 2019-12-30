package com.tcmis.client.catalog.process;

import java.util.*;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogAddTrackingInputBean;
import com.tcmis.client.catalog.beans.NewChemTrackingViewBean;
import com.tcmis.client.catalog.beans.FacilityApprovalRolesBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;

/**
 * ******************************************************************
 * Process for the CatalogAddTrackingProcess
 * 
 * @version 1.0
 * *****************************************************************
 */

public class CatalogAddTrackingProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public CatalogAddTrackingProcess(String client, String locale) {
		super(client, locale);
	}

	public CatalogAddTrackingProcess(String client) {
		super(client);
	}

    public Collection getUserFacilityApprovalRoles(PersonnelBean personnelBean) throws BaseException {
        Vector result = new Vector();
        DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FacilityApprovalRolesBean());
        StringBuilder query = new StringBuilder("select distinct a.facility_id,a.approval_role from vv_chemical_approval_role a, user_facility uf");
        query.append(" where a.company_id = uf.company_id and a.facility_id = uf.facility_id and a.active = 'Y'");
        query.append(" and uf.company_id = '").append(personnelBean.getCompanyId()).append("' and uf.personnel_id = ").append(personnelBean.getPersonnelId());
        query.append(" order by a.facility_id,a.approval_role");
        Iterator iter =  factory.selectQuery(query.toString()).iterator();
        String lastFacilityId = "";
        while(iter.hasNext()) {
            FacilityApprovalRolesBean bean = (FacilityApprovalRolesBean)iter.next();
            if (lastFacilityId.equals(bean.getFacilityId())) {
                FacilityApprovalRolesBean parentBean = (FacilityApprovalRolesBean)result.lastElement();
                Collection approvalRoleColl = (Collection)parentBean.getApprovalRoleColl();
                FacilityApprovalRolesBean childBean = new FacilityApprovalRolesBean();
                childBean.setApprovalRole(bean.getApprovalRole());
                approvalRoleColl.add(childBean);
            }else {
                FacilityApprovalRolesBean parentBean = new FacilityApprovalRolesBean();
                parentBean.setFacilityId(bean.getFacilityId());
                Collection approvalRoleColl = new Vector();
                FacilityApprovalRolesBean childBean = new FacilityApprovalRolesBean();
                childBean.setApprovalRole(bean.getApprovalRole());
                approvalRoleColl.add(childBean);
                parentBean.setApprovalRoleColl(approvalRoleColl);
                result.addElement(parentBean);
            }
            lastFacilityId = bean.getFacilityId();
        }

        return result;
    }  //end of method

    public Collection getApproverRoles(PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new NewChemTrackingViewBean());
		StringBuilder query = new StringBuilder("select distinct ars.request_status from chemical_approver ca,vv_chemical_approval_role car,vv_catalog_add_request_status ars");
		query.append(" where ca.facility_id = car.facility_id and ca.active = 'y' and ca.approval_role = car.approval_role and ca.catalog_id = car.catalog_id and");
		query.append(" ca.catalog_company_id = car.catalog_company_id and car.active = 'Y' and car.approval_group = ars.approval_group and ca.personnel_id = ").append(personnelBean.getPersonnelId());
		return factory.selectQuery(query.toString());
	}

	public Collection<NewChemTrackingViewBean> getSearchResult(	CatalogAddTrackingInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
        //the reason that I added distinct here is because of catalog_add_user_group
        StringBuilder hQuery = new StringBuilder("select distinct quality_id_label,quality_id,cat_part_attribute_header, cat_part_attribute,company_id, request_id, requestor, name, request_date, request_status_desc, request_status,");
		hQuery.append("facility_id, facility_name, cat_part_no, material_desc, material_type, manufacturer, packaging, part_id,");
		hQuery.append("mfg_catalog_id, customer_request_id, item_id,");
		hQuery.append("engineering_evaluation, line_item, line_status, starting_view,approval_list,aging_time,approval_group_seq_start_time,");
        hQuery.append("work_area_list,starting_view_desc,submit_date,last_updated,customer_msds_number,customer_mixture_number,starting_view_desc_jsp, request_status_desc_jsp");
        hQuery.append(",netwt,netwt_unit");

        StringBuilder query = new StringBuilder("");
		GenericSqlFactory newChemTrackingFactory = new GenericSqlFactory(dbManager,new NewChemTrackingViewBean());
		if (inputBean.getRequestNeedingMyApproval() != null) {
			query.append(" from new_chem_web_to_approve_view where personnel_id = ").append(personnelBean.getPersonnelId());
			query.append(" and (material_desc <> 'Material added from POSS' or material_desc is null) and request_status in (");

			Iterator iter = getApproverRoles(personnelBean).iterator();
			int count = 0;
			while (iter.hasNext()) {
				NewChemTrackingViewBean bean = (NewChemTrackingViewBean)iter.next();
				if (count == 0) {
					query.append(bean.getRequestStatus().toString());
				}else {
					query.append(",").append(bean.getRequestStatus().toString());
				}
				count++;
			}
			if (count > 0) {
				query.append(") order by request_id,line_item,part_id");
                //put it together
                hQuery.append(query);
                return newChemTrackingFactory.selectQuery(hQuery.toString());
			}else {
				return new Vector(0);
			}
		}else {
			if (inputBean.getApprover() == null) {
				query.append(" from new_chem_tracking_web_view nctv where");
			}else {
				query.append(" from new_chem_web_to_approve_view nctv where");
			}
            boolean hasConstraint = false;
            //approval role
            //note this is the first constraint "and" is needed
            if (!StringHandler.isBlankString(inputBean.getPendingApprovalRole())) {
                query.append(" lower(approval_list) like lower('%").append(SqlHandler.validQuery(inputBean.getPendingApprovalRole())).append("%')");
                hasConstraint = true;
            }

            //by approver id
            if(null != inputBean.getApprover()) {
                if (hasConstraint) {
                    query.append(" and");
                }
                query.append(" approver_list like '%").append(inputBean.getApprover()).append("%'");
                hasConstraint = true;
            }
            if (hasConstraint) {
                query.append(" and");
            }
            //facility and work area
            if ("My Facilities".equalsIgnoreCase(inputBean.getFacilityId())) {
				query.append(" facility_id in (select facility_id from user_facility where personnel_id = ").append(personnelBean.getPersonnelId()+")");
			}else {
				query.append(" facility_id = '").append(inputBean.getFacilityId()+"'");
				if ("My Work Areas".equalsIgnoreCase(inputBean.getApplicationId())) {
					query.append(" and nvl(application,'All') in (select decode(ua.application,'All',nvl(nctv.application,'All'),ua.application) from user_application ua where facility_id = '").append(inputBean.getFacilityId()).append("'");
					query.append(" and personnel_id = ").append(personnelBean.getPersonnelId()+")");
				}else {
					query.append(" and application = '").append(inputBean.getApplicationId()+"'");
				}
			}
            //requestor
            if(null!=inputBean.getRequestor())
				query.append(" and requestor = ").append(inputBean.getRequestor());
            //statuses - if user selected an approval role then don't limit by status
            if (StringHandler.isBlankString(inputBean.getPendingApprovalRole())) {
                if (inputBean.getRequestStatusChkbxArray() != null && inputBean.getRequestStatusChkbxArray().length > 0) {
                    if(inputBean.getRequestStatusChkbxArray().length == 1 ) {
                        query.append(" and request_status in (").append(inputBean.getRequestStatusChkbxArray()[0].toString()).append(")");
                    }else {
                        query.append(" and request_status in (");
                        for (int i = 0; i <inputBean.getRequestStatusChkbxArray().length; i++) {
                            if (inputBean.getRequestStatusChkbxArray()[i].length() > 0) {
                                if(i > 0) {
                                    query.append(",");
                                }
                                query.append(inputBean.getRequestStatusChkbxArray()[i].toString());
                            }
                        }
                        query.append(")");
                    }
                }
            }

            String s = null;
			s = inputBean.getSearchArgument();
			if (s != null && !s.equals("")) {
				String mode = inputBean.getSearchMode();
				String field = inputBean.getSearchField();
                if (mode.equals("is")) {
                    if ("customer_msds_mixture_number".equals(field)) {
                        query.append(" and (customer_msds_number = ").append(SqlHandler.delimitString(s));
                        query.append(" or customer_mixture_number = ").append(SqlHandler.delimitString(s)).append(")");
                    }else {
                        query.append(" and ").append(field).append(" = ").append(SqlHandler.delimitString(s));
                    }
                }else if (mode.equals("like")) {
                    if ("customer_msds_mixture_number".equals(field)) {
                        query.append(" and (lower(customer_msds_number) like lower('%").append(SqlHandler.validQuery(s)).append("%')");
                        query.append(" or lower(customer_mixture_number) like lower('%").append(SqlHandler.validQuery(s)).append("%'))");
                    }else {
                        query.append(" and lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(s)).append("%')");
                    }
                }else if (mode.equals("startsWith")) {
                    if ("customer_msds_mixture_number".equals(field)) {
                        query.append(" and (lower(customer_msds_number) like lower('").append(SqlHandler.validQuery(s)).append("%')");
                        query.append(" or lower(customer_mixture_number) like lower('").append(SqlHandler.validQuery(s)).append("%'))");
                    }else {
                        query.append(" and lower(").append(field).append(") like lower('").append(SqlHandler.validQuery(s)).append("%')");
                    }
                }else if (mode.equals("endsWith")) {
                    if ("customer_msds_mixture_number".equals(field)) {
                        query.append(" and (lower(customer_msds_number) like lower('%").append(SqlHandler.validQuery(s)).append("')");
                        query.append(" or lower(customer_mixture_number) like lower('%").append(SqlHandler.validQuery(s)).append("'))");
                    }else {
                        query.append(" and lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(s)).append("')");
                    }
                }
            }
            //date range
            if (inputBean.getBeginDateJsp() != null && inputBean.getEndDateJsp() != null) {
                query.append(" and ").append(inputBean.getDateType()).append(" >= ").append(DateHandler.getOracleToDateFunction(inputBean.getBeginDateJsp()));
                query.append(" and ").append(inputBean.getDateType()).append(" <= ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDateJsp()));
            }else if (inputBean.getBeginDateJsp() != null) {
                query.append(" and ").append(inputBean.getDateType()).append(" >= ").append(DateHandler.getOracleToDateFunction(inputBean.getBeginDateJsp()));
            }else if (inputBean.getEndDateJsp() != null) {
                query.append(" and ").append(inputBean.getDateType()).append(" <= ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDateJsp()));
            }
            query.append(" order by request_id,line_item,part_id");
            //put it together
            hQuery.append(query);
            return newChemTrackingFactory.selectQuery(hQuery.toString());
		}

	} //end of method

	public Object[] createRelationalObjects(Vector<NewChemTrackingViewBean> bv) {
		// calculate m1 and m2
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		String requestId = null;

		HashSet<String> qualityIdLabelSet = new HashSet();
		HashSet<String> catPartAttrHeaderSet = new HashSet();
//		Vector<NewChemTrackingViewBean> bv = (Vector<NewChemTrackingViewBean>) data;
		for (NewChemTrackingViewBean pbean:bv) {
			if( !StringUtils.isEmpty(pbean.getQualityIdLabel()) )
				qualityIdLabelSet.add(pbean.getQualityIdLabel());
			if( !StringUtils.isEmpty(pbean.getCatPartAttributeHeader()) )
				catPartAttrHeaderSet.add(pbean.getCatPartAttributeHeader());
		}
		
		// if Hide, then no show column
		// if empty then appened ( header ) to value and header is empty
		// else if all the same, used as header.
		String qualityIdLabelColumnHeader = "--Hide--"; 
		String catPartAttrColumnHeader = "--Hide--"; 
		if( qualityIdLabelSet.size() == 1 ) {
			for(String s:qualityIdLabelSet) 
				qualityIdLabelColumnHeader = s;
		}
		if( qualityIdLabelSet.size() > 1 ) {
			qualityIdLabelColumnHeader = " "; 		//adding empty space because grid will not display column if empty
			for (NewChemTrackingViewBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getQualityIdLabel() ) && StringUtils.isNotEmpty(pbean.getQualityId() )) 
						pbean.setQualityId(pbean.getQualityId() +" ("+pbean.getQualityIdLabel()+")");
			}
		}
		if( catPartAttrHeaderSet.size() == 1 )
			for(String s:catPartAttrHeaderSet) 
				catPartAttrColumnHeader = s;
		if( catPartAttrHeaderSet.size() > 1 ) {
			catPartAttrColumnHeader = " "; 		//adding empty space because grid will not display column if empty
			for (NewChemTrackingViewBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getCatPartAttributeHeader() ) && StringUtils.isNotEmpty(pbean.getCatPartAttribute() ))  
						pbean.setCatPartAttribute(pbean.getCatPartAttribute()+" ("+pbean.getCatPartAttributeHeader()+")");
			}
		}

		
		for (NewChemTrackingViewBean tmpBean:bv) {
			requestId = tmpBean.getRequestId().toString();

			if (m1.get(requestId) == null) {
				i1 = new Integer(0);
				m1.put(requestId, i1);
				map = new HashMap();
				m2.put(requestId, map);
			}
			i1 = (Integer) m1.get(requestId);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(requestId, i1);

			String lineItem = "lineItem" + tmpBean.getLineItem().toString();

			if (map.get(lineItem) == null) {
				i2 = new Integer(0);
				map.put(lineItem, i2);
			}
			i2 = (Integer) map.get(lineItem);
			i2 = new Integer(i2.intValue() + 1);
			map.put(lineItem, i2);
		}
		Object[] objs = {bv,m1,m2,qualityIdLabelColumnHeader,catPartAttrColumnHeader};
		return objs;
	} //end of method

	public Object[] getExtraInfo(Collection c) {
		//		Vector bv = (Vector) this.getSearchResult(inputBean);
		// calculate m1 and m2
		Vector<NewChemTrackingViewBean> bv = (Vector<NewChemTrackingViewBean>) c; 
		HashSet<String> qualityIdLabelSet = new HashSet();
		HashSet<String> catPartAttrHeaderSet = new HashSet();
	
		for (NewChemTrackingViewBean pbean:bv) {
			if( !StringUtils.isEmpty(pbean.getQualityIdLabel()) )
				qualityIdLabelSet.add(pbean.getQualityIdLabel());
			if( !StringUtils.isEmpty(pbean.getCatPartAttributeHeader()) )
				catPartAttrHeaderSet.add(pbean.getCatPartAttributeHeader());
		}
		// if Hide, then no show column
		// if empty then appened ( header ) to value and header is empty
		// else if all the same, used as header.
		String qualityIdLabelColumnHeader = "--Hide--"; 
		String catPartAttrColumnHeader = "--Hide--"; 
		if( qualityIdLabelSet.size() == 1 ) {
			for(String s:qualityIdLabelSet) 
				qualityIdLabelColumnHeader = s;
		}
		if( qualityIdLabelSet.size() > 1 ) {
			qualityIdLabelColumnHeader = " "; 		//adding empty space because grid will not display column if empty
			for (NewChemTrackingViewBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getQualityIdLabel() ) && StringUtils.isNotEmpty(pbean.getQualityId() )) 
						pbean.setQualityId(pbean.getQualityId() +" ("+pbean.getQualityIdLabel()+")");
			}
		}
		if( catPartAttrHeaderSet.size() == 1 )
			for(String s:catPartAttrHeaderSet) 
				catPartAttrColumnHeader = s;
		if( catPartAttrHeaderSet.size() > 1 ) {
			catPartAttrColumnHeader = " "; 			//adding empty space because grid will not display column if empty
			for (NewChemTrackingViewBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getCatPartAttributeHeader() ) && StringUtils.isNotEmpty(pbean.getCatPartAttribute() ))  
						pbean.setCatPartAttribute(pbean.getCatPartAttribute()+" ("+pbean.getCatPartAttributeHeader()+")");
			}
		}
		Object[] objs = {qualityIdLabelColumnHeader,catPartAttrColumnHeader};
		return objs;
	}

	public ExcelHandler getExcelReport(CatalogAddTrackingInputBean bean, PersonnelBean personnelBean, Locale locale)	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(	"com.tcmis.common.resources.CommonResources", locale);

		Collection<NewChemTrackingViewBean> data = getSearchResult(bean,personnelBean);

		ExcelHandler pw = new ExcelHandler(library);

        // write column headers
		pw.addRow();

		String[] headerkeys =
		{
			 "label.requestid","label.requesttype","label.status","label.requestor","label.submitted",
             "label.approvalrequested","label.approvalRole","label.durationbydays",
             "label.facility","label.workarea","label.partno","label.itemid","label.materialdesc",
			 "label.manufacturer","label.packaging","label.mfgpartno","label.customerrequest",
		};
		int[] widths = {12,12,12,12,12,12,12,12,12,12,12,12,20,14,12,12,12 };
		int[] types =
		{
				pw.TYPE_NUMBER,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_CALENDAR,
                pw.TYPE_CALENDAR,pw.TYPE_STRING,pw.TYPE_NUMBER,
                pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,ExcelHandler.TYPE_PARAGRAPH,
				ExcelHandler.TYPE_PARAGRAPH,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING
		};

		int[] aligns = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		pw.applyColumnHeader(headerkeys, types, widths, aligns);
		   Object[] results = getExtraInfo(data);
		   String qualityIdLabelColumnHeader = (String)results[0];
		   String catPartAttrColumnHeader = (String)results[1];

			   if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
					pw.addCellBold(qualityIdLabelColumnHeader);
			   if(!"--Hide--".equals(catPartAttrColumnHeader) )
					pw.addCellBold(catPartAttrColumnHeader);

		for (NewChemTrackingViewBean member : data) {
			pw.addRow();
			if(StringHandler.isBlankString(member.getStartingViewDescJsp()))
				pw.addCell(member.getRequestId());
			else
				pw.addCell(member.getRequestId().toPlainString() + " (" + library.getString(member.getStartingViewDescJsp()) + ")");
            pw.addCell(StringHandler.isBlankString(member.getStartingViewDesc())?"":library.getString(member.getStartingViewDescJsp()));
            pw.addCell(StringHandler.isBlankString(member.getRequestStatusDesc())?"":library.getString(member.getRequestStatusDescJsp()));
			pw.addCell(member.getName()); 
			pw.addCell(member.getSubmitDate());
            pw.addCell(member.getApprovalGroupSeqStartTime());
            pw.addCell(member.getApprovalList());
            pw.addCell(member.getAgingTime());
            pw.addCell(member.getFacilityName());
            pw.addCell(member.getWorkAreaList());
            pw.addCell(member.getCatPartNo());
			pw.addCell(member.getItemId());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getManufacturer());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getMfgCatalogId());
			pw.addCell(member.getCustomerRequestId());
			if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
				pw.addCell(member.getQualityId());//"<TD ROWSPAN=" + mainRowSpan + ">" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getQtyOfUomPerItem()) + "</TD>");
			if(!"--Hide--".equals(catPartAttrColumnHeader) )
				pw.addCell(member.getCatPartAttribute());

		}
		return pw;

	}
}