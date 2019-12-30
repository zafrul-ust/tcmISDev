package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.UseApprovalDetailViewBean;
import com.tcmis.client.catalog.beans.ApplicationUseGroupBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

import radian.tcmis.common.util.SqlHandler;

import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class ApprovedWorkAreasProcess extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());

    public ApprovedWorkAreasProcess(String client) {
        super(client);
    }
    
    public Collection getApprovalCodeDefinition(UseApprovalDetailViewBean inputBean) throws BaseException {
        DbManager dbManager = new DbManager(getClient());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ApplicationUseGroupBean());
        SearchCriteria criteria = new SearchCriteria();
        if (!StringHandler.isBlankString(inputBean.getCompanyId())) {
            criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
        }
        if (!StringHandler.isBlankString(inputBean.getFacilityId())) {
            criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputBean.getFacilityId());
        }
        SortCriteria sort = new SortCriteria();
        sort.addCriterion("applicationUseGroupName");
        return factory.select(criteria,sort,"application_use_group");
    }

    public Collection getsearchResult(UseApprovalDetailViewBean inputBean) throws BaseException {
        DbManager dbManager = new DbManager(getClient());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager,new UseApprovalDetailViewBean());
        SearchCriteria criteria = new SearchCriteria();
        if (!StringHandler.isBlankString(inputBean.getCompanyId())) {
            criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
        }
        if (!StringHandler.isBlankString(inputBean.getCatalogCompanyId())) {
            criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, inputBean.getCatalogCompanyId());
        }
        if (!StringHandler.isBlankString(inputBean.getFacilityId()) && !inputBean.isAllCatalog()) {
            criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputBean.getFacilityId());
        }
        if (!StringHandler.isBlankString(inputBean.getCatalogId())) {
            criteria.addCriterion("catalogId", SearchCriterion.EQUALS, inputBean.getCatalogId());
        }
        if (!StringHandler.isBlankString(inputBean.getFacPartNo())) {
            criteria.addCriterion("facPartNo", SearchCriterion.EQUALS, inputBean.getFacPartNo());
        }
        if (inputBean.getPartGroupNo() != null) {
            criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, inputBean.getPartGroupNo().toString());
        }

        Collection result = new Vector();
        Vector allWorkAreasDataV = new Vector();
        Collection<UseApprovalDetailViewBean> beans = factory.select(criteria,null,"use_approval_detail_view");
        for (UseApprovalDetailViewBean bean : beans) {
            if ("All".equals(bean.getApplication())) {
                StringBuilder allWorkAreasData = new StringBuilder("");
                allWorkAreasData.append(bean.getFacilityId()).append(bean.getApplication()).append(bean.getUserGroupId()).append(bean.getLimit1()).append(bean.getLimit2());
                allWorkAreasData.append(bean.getApplicationUseGroupName()).append(bean.getStartDate()).append(bean.getExpireDate());
                allWorkAreasDataV.add(allWorkAreasData.toString());
                result.add(bean);
            }else {
                //if the specific work area has the same key data as All Work Areas then don't need to show the specific work area
                StringBuilder currentWorkAreaData = new StringBuilder("");
                currentWorkAreaData.append(bean.getFacilityId()).append("All").append(bean.getUserGroupId()).append(bean.getLimit1()).append(bean.getLimit2());
                currentWorkAreaData.append(bean.getApplicationUseGroupName()).append(bean.getStartDate()).append(bean.getExpireDate());
                if (!allWorkAreasDataV.contains(currentWorkAreaData.toString()))
                    result.add(bean);
            }
        }

        return result;
    } //end of method
    
    public boolean isUseCodeRequired(UseApprovalDetailViewBean inputBean) throws BaseException {
    	DbManager dbManager = new DbManager(getClient());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager,new UseApprovalDetailViewBean());
        StringBuilder query = new StringBuilder();
        query.append("select count(*) from facility where use_code_required = 'Y'");
        query.append(" and company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
        if ( ! inputBean.isAllCatalog()) {
        	query.append(" and facility_id = ").append(SqlHandler.delimitString(inputBean.getFacilityId()));
        }
        String facCount = factory.selectSingleValue(query.toString());
        try {
	        if (Integer.parseInt(facCount) > 0) {
	        	return true;
	        }
        }
        catch(NumberFormatException e) {
        	log.error(e);
        }
        return false;
    }
}