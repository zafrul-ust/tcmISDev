package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.SpecDisplayViewBean;
import com.tcmis.client.catalog.factory.SpecDisplayViewBeanFactory;
import com.tcmis.client.common.beans.DocViewerInputBean;
import com.tcmis.client.common.beans.MsdsSearchInputBean;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for SearchMsdsProcess
 * @version 1.0
 *****************************************************************************/
public class DocViewerProcess extends GenericProcess {

	Log log = LogFactory.getLog(this.getClass());

	public DocViewerProcess(String client) {
		super(client);
	}
	
    public SpecDisplayViewBean getSpec(DocViewerInputBean input) throws BaseException {
        StringBuilder query = new StringBuilder("select distinct s.* from spec s, fac_spec fs, catalog_facility cf where");
    	query.append(" cf.company_id = '").append(input.getCompanyId()).append("'");
        query.append(" and cf.catalog_id = '").append(input.getCatalogId()).append("'");
        if (!"N".equals(input.getHasSpecificFacility()) && input.hasFacility()) {
            query.append(" and cf.facility_id = '").append(input.getFacility()).append("'");
        }
    	query.append(" and fs.company_id = cf.catalog_company_id");
    	query.append(" and fs.facility_id = cf.catalog_id");
    	query.append(" and fs.fac_part_no = '").append(input.getCatpartno()).append("'");
    	query.append(" and s.company_id = fs.company_id");
    	query.append(" and s.spec_id = fs.spec_id");
        query.append(" and s.spec_library = fs.spec_library");
        query.append(" and s.spec_id = '").append(input.getSpec()).append("'");
        query.append(" and s.content is not null");
        factory.setBean(new SpecDisplayViewBean());
    	Collection<SpecDisplayViewBean> results = factory.selectQuery(query.toString()); 
    	return results.isEmpty() ? null : results.iterator().next();     			
	}

    public SpecDisplayViewBean getSpec(String specId) throws BaseException {
    	factory.setBean(new SpecDisplayViewBean());
    	SearchCriteria query = new SearchCriteria("specId", SearchCriterion.EQUALS, specId);
    	Collection<SpecDisplayViewBean> results = factory.select(query, null, "spec");
    	return results.isEmpty() ? null : results.iterator().next();     			
	}

    public SpecDisplayViewBean getSpecForHaas(DocViewerInputBean input) throws BaseException {
    	factory.setBean(new SpecDisplayViewBean());
    	SearchCriteria query = new SearchCriteria("specId", SearchCriterion.EQUALS, input.getSpec());
        query.addCriterion("specLibrary",SearchCriterion.EQUALS, input.getSpecLibrary());
        Collection<SpecDisplayViewBean> results = factory.select(query, null, "customer.spec_definition");
    	return results.isEmpty() ? null : results.iterator().next();
	}
    
    public String getOpsEntityId (String inventorygroup) throws BaseException {
    	StringBuilder query = new StringBuilder("select OPS_ENTITY_ID from inventory_group_definition where inventory_group = '").append(inventorygroup).append("'");
    	return factory.selectSingleValue(query.toString());     			
    }

    public SpecDisplayViewBean getCatalogAddSpec(DocViewerInputBean inputBean, String tableName) throws BaseException {
    	factory.setBean(new SpecDisplayViewBean());
    	SearchCriteria criteria = new SearchCriteria("specId", SearchCriterion.EQUALS, inputBean.getSpec());
        criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
        criteria.addCriterion("requestId", SearchCriterion.EQUALS, inputBean.getCatalogAddRequestId().toString());
        Collection<SpecDisplayViewBean> results = factory.select(criteria, null, tableName);
    	return results.isEmpty() ? null : results.iterator().next();
	}
} //end of class

