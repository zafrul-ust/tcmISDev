package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.common.beans.TestDefinitionInputBean;
import com.tcmis.client.common.beans.CustLabTestRequiredViewBean;
import com.tcmis.client.common.beans.VvTestBean;
import com.tcmis.client.common.beans.SecondaryLabelDataBean;
import com.tcmis.client.common.factory.VvSecLblDataTypCmmntOvBeanFactory;

/******************************************************************************
 * Process for ItemCatalogProcess
 * @version 1.0
 *****************************************************************************/
public class TestDefinitionProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public TestDefinitionProcess(String client,String locale) {
		super(client,locale);
	}

 	public void applyDefaultTests(TestDefinitionInputBean inputBean, BigDecimal personnelId) throws BaseException {
		try {
            DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new TestDefinitionInputBean());
            //this is to handle case where there is already a test associated with part
            //copy frequency data over to default tests
            StringBuilder query = new StringBuilder("insert into cust_lab_test_required ");
            query.append("select distinct a.COMPANY_ID, a.CATALOG_COMPANY_ID, a.CATALOG_ID,'").append(inputBean.getCatPartNo()).append("' CAT_PART_NO,");
            query.append(inputBean.getPartGroupNo()).append(" PART_GROUP_NO, a.TEST_ID,'' NO_SUCCESS_TEST_REQ_FOR_SKIP,");
            query.append("'' TEST_ALLOWED_SKIP,'' TEST_TYPE,").append(personnelId).append(" UPDATED_BY,sysdate UPDATED_ON,b.FREQUENCY_TYPE,b.FREQUENCY_UNIT,b.FREQUENCY,b.REQUIRE_CUSTOMER_RESPONSE,'Y' ACTIVE");
            query.append(" from vv_test a, cust_lab_test_required b where a.company_id = '").append(inputBean.getCompanyId()).append("' and a.catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
            query.append(" and a.catalog_id = '").append(inputBean.getCatalogId()).append("' and a.active = 'Y' and a.default_test = 'Y'");
            query.append(" and not exists (select null from cust_lab_test_required x where x.company_id = a.company_id");
            query.append(" and x.catalog_company_id = a.catalog_company_id and x.test_id = a.test_id and x.cat_part_no = '").append(inputBean.getCatPartNo()).append("'");
            query.append(" and x.part_group_no = ").append(inputBean.getPartGroupNo()).append(")");
            query.append(" and b.company_id = '").append(inputBean.getCompanyId()).append("' and b.catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
            query.append(" and b.catalog_id = '").append(inputBean.getCatalogId()).append("' and b.active = 'Y'");
            query.append(" and b.cat_part_no = '").append(inputBean.getCatPartNo()).append("'");
            query.append(" and b.part_group_no = ").append(inputBean.getPartGroupNo());
            factory.deleteInsertUpdate(query.toString());

            //this is to handle case where there are no test associated with part
            query = new StringBuilder("insert into cust_lab_test_required ");
            query.append("select COMPANY_ID, CATALOG_COMPANY_ID, CATALOG_ID,'").append(inputBean.getCatPartNo()).append("' CAT_PART_NO,");
            query.append(inputBean.getPartGroupNo()).append(" PART_GROUP_NO, TEST_ID,'' NO_SUCCESS_TEST_REQ_FOR_SKIP,");
            query.append("'' TEST_ALLOWED_SKIP,'' TEST_TYPE,").append(personnelId).append(" UPDATED_BY,sysdate UPDATED_ON,'' FREQUENCY_TYPE,'' FREQUENCY_UNIT,'' FREQUENCY,'Y' REQUIRE_CUSTOMER_RESPONSE,'Y' ACTIVE");
            query.append(" from vv_test a where company_id = '").append(inputBean.getCompanyId()).append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
            query.append(" and catalog_id = '").append(inputBean.getCatalogId()).append("' and active = 'Y' and default_test = 'Y'");
            query.append(" and not exists (select null from cust_lab_test_required x where x.company_id = a.company_id");
            query.append(" and x.catalog_company_id = a.catalog_company_id and x.test_id = a.test_id and x.cat_part_no = '").append(inputBean.getCatPartNo()).append("'");
            query.append(" and x.part_group_no = ").append(inputBean.getPartGroupNo()).append(")");
            factory.deleteInsertUpdate(query.toString());

        }catch (Exception e) {
			e.printStackTrace();
		}
	} //end of method

    public Collection getCusLabTestRequiredColl(TestDefinitionInputBean inputBean,boolean activeOnly) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustLabTestRequiredViewBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
			criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, inputBean.getCatalogCompanyId());
			criteria.addCriterion("catalogId", SearchCriterion.EQUALS, inputBean.getCatalogId());
			if(inputBean.getCatPartNo() != null)
				criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, inputBean.getCatPartNo());
			if(inputBean.getPartGroupNo() != null)
				criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, inputBean.getPartGroupNo().toString());
			if (activeOnly)
                criteria.addCriterion("active", SearchCriterion.EQUALS, "Y");
			
			return factory.select(criteria, new SortCriteria(), "CUST_LAB_TEST_REQUIRED_VIEW");
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return result; 
	}
	
	public Collection getVvTestColl(TestDefinitionInputBean inputBean, boolean activeOnly) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VvTestBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
            criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, inputBean.getCatalogCompanyId());
            criteria.addCriterion("catalogId", SearchCriterion.EQUALS, inputBean.getCatalogId());
            if (activeOnly)
                criteria.addCriterion("active", SearchCriterion.EQUALS, "Y");
            
            SortCriteria sort = new SortCriteria();
			sort.addCriterion("shortName");
				
			return factory.select(criteria, sort, "vv_test");
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return result; 
	}

    public Collection getFrequencyUnitColl() throws BaseException {
		Collection result = null;
		try {
            DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new TestDefinitionInputBean());
			SearchCriteria criteria = new SearchCriteria();
            criteria.addCriterion("active", SearchCriterion.EQUALS, "Y");
            SortCriteria sort = new SortCriteria();
			sort.addCriterion("frequencyUnitDisplayOrder");
			return factory.select(criteria, sort, "vv_lab_test_frequency_unit");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    public Collection getFrequencyTypeColl() throws BaseException {
		Collection result = null;
		try {
            DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new TestDefinitionInputBean());
			SearchCriteria criteria = new SearchCriteria();
            criteria.addCriterion("active", SearchCriterion.EQUALS, "Y");
            SortCriteria sort = new SortCriteria();
			sort.addCriterion("frequencyTypeDisplayOrder");
			return factory.select(criteria, sort, "vv_lab_test_frequency_type");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Collection updateLabTests(BigDecimal personnelId,TestDefinitionInputBean inputBean, Collection<CustLabTestRequiredViewBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
        Vector error = new Vector();
        try {
            for (CustLabTestRequiredViewBean bean : beans) {
                try {
                    inArgs = new Vector(15);
                    inArgs.add(inputBean.getCompanyId());
                    inArgs.add(inputBean.getCatalogCompanyId());
                    inArgs.add(inputBean.getCatalogId());
                    inArgs.add(inputBean.getCatPartNo());
                    inArgs.add(inputBean.getPartGroupNo());
                    inArgs.add(bean.getTestId());
                    inArgs.add(bean.getNoSuccessTestReqForSkip());
                    inArgs.add("true".equals(bean.getTestAllowedSkip())?"Y":"N");
                    inArgs.add(bean.getTestType());
                    inArgs.add(personnelId);
                    if (StringHandler.isBlankString(bean.getFrequencyType()) || StringHandler.isBlankString(bean.getFrequencyUnit()) ||
                        bean.getFrequency() == null) {
                        inArgs.add(null);
                        inArgs.add(null);
                        inArgs.add(null);
                    }else {
                        inArgs.add(bean.getFrequencyType());
                        inArgs.add(bean.getFrequencyUnit());
                        inArgs.add(bean.getFrequency());
                    }
                    inArgs.add("true".equals(bean.getRequireCustomerResponse())?"Y":"N");
                    inArgs.add(bean.getActive());
                    outArgs = new Vector(1);
                    outArgs.add(new Integer(java.sql.Types.VARCHAR));
                    if("new".equals(bean.getStatus())) {
                        error = (Vector) factory.doProcedure(connection,"pkg_lab_testing.p_cust_lab_test_req_insert", inArgs, outArgs);
                    } else if("updated".equals(bean.getStatus())) {
                        error = (Vector) factory.doProcedure(connection,"pkg_lab_testing.p_cust_lab_test_req_update", inArgs, outArgs);
                    }
                    if(error.size()>0 && error.get(0) != null) {
                         String errorCode = (String) error.get(0);
                         log.info(" inserting/updating: " + bean.getTestId()+ errorCode);
                         if(!"ok".equalsIgnoreCase(errorCode))
                             errorMessages.add(errorCode);
                    }
                }catch (Exception e) {
                    errorMsg = "Error inserting/updating lab test: " + bean.getTestId();
                    errorMessages.add(errorMsg);
                }
            }
        }catch (Exception ee){
            ee.printStackTrace();
        }finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}

        return (errorMessages.size() > 0 ? errorMessages : null);
	}   //end of method

	public Collection updateTest(BigDecimal personnelId, TestDefinitionInputBean inputBean, Collection<VvTestBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
        Connection connection = dbManager.getConnection();
        GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
        try {
            log.debug("size"+beans.size());
            for (VvTestBean bean : beans) {
                if("new".equals(bean.getStatus())) {
                    try {
                        inArgs = new Vector(9);
                        inArgs.add(inputBean.getCompanyId());
                        inArgs.add(inputBean.getCatalogCompanyId());
                        inArgs.add(inputBean.getCatalogId());
                        inArgs.add(bean.getTestDesc());
                        inArgs.add(bean.getCriteria());
                        inArgs.add(bean.getShortName());
                        inArgs.add(personnelId);
                        inArgs.add("true".equals(bean.getDefaultTest())?"Y":"N");
                        inArgs.add(bean.getActive());
                        outArgs = new Vector(2);
                        outArgs.add(new Integer(java.sql.Types.NUMERIC));
                        outArgs.add(new Integer(java.sql.Types.VARCHAR));
                        Vector error = (Vector) factory.doProcedure(connection,"pkg_lab_testing.p_vv_test_insert", inArgs, outArgs);
                        if(error.size()>0 && error.get(1) != null) {
                             String errorCode = (String) error.get(1);
                             log.info(" inserting: " + bean.getShortName()+ errorCode);
                             if(!"ok".equalsIgnoreCase(errorCode))
                                 errorMessages.add(errorCode);
                        }
                    }catch (Exception e) {
                        errorMsg = "Error inserting test: " + bean.getShortName();
                        errorMessages.add(errorMsg);
                    }
                } else if("updated".equals(bean.getStatus())) {
                    try {
                        inArgs = new Vector(8);
                        inArgs.add(inputBean.getCompanyId());
                        inArgs.add(inputBean.getCatalogCompanyId());
                        inArgs.add(inputBean.getCatalogId());
                        inArgs.add(bean.getTestId());
                        inArgs.add(bean.getShortName());
                        inArgs.add(bean.getCriteria());
                        inArgs.add("true".equals(bean.getDefaultTest())?"Y":"N");
                        inArgs.add(bean.getActive());
                        inArgs.add(bean.getTestDesc());
                        outArgs = new Vector(1);
                        outArgs.add(new Integer(java.sql.Types.VARCHAR));
                        Vector error = (Vector) factory.doProcedure(connection,"pkg_lab_testing.p_vv_test_update", inArgs, outArgs);
                        if(error.size()>0 && error.get(0) != null) {
                             String errorCode = (String) error.get(0);
                             log.info(" updating: " + bean.getShortName()+ errorCode);
                             if(!"ok".equalsIgnoreCase(errorCode))
                                 errorMessages.add(errorCode);
                        }
                    }catch (Exception e) {
                        errorMsg = "Error updating test: " + bean.getShortName();
                        errorMessages.add(errorMsg);
                    }
                }
            }
        }catch (Exception ee){
            ee.printStackTrace();
        }finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}
        return (errorMessages.size() > 0 ? errorMessages : null);
	} //end of method

} //end of class

