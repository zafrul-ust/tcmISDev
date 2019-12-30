package com.tcmis.internal.distribution.process;

import java.io.File;
import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.AddSpecsBean;


public class NewDetailProcess extends GenericProcess {
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public NewDetailProcess(String client) {
		super(client);
	}

	public NewDetailProcess(String client, String locale) {
		super(client, locale);
	}
	
	public Collection<AddSpecsBean> getSpecDetails(AddSpecsBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new AddSpecsBean());
		SearchCriteria criteria = new SearchCriteria();
		
		StringBuilder query = new StringBuilder("select pkg_spec_utility.FX_SPEC_DETAIL_CONCAT(spec_detail_type, spec_detail_class,spec_detail_form,spec_detail_group, spec_detail_grade, spec_detail_style, spec_detail_finish, spec_detail_size, spec_detail_color, spec_detail_other) detail from Spec_detail where spec_id='").append(inputBean.getSpecId()).append("'");
		
		if(inputBean.getSpecLibrary() != null)
			query.append(" and spec_library = '").append(inputBean.getSpecLibrary()).append("'");
		
		Collection<AddSpecsBean> c = factory.selectQuery(query.toString());
		
		return c;
	}

	public String createNewDetail(AddSpecsBean bean) throws
	BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		Vector results = null;
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		try {
			Collection inArgs = buildProcedureInput(
								bean.getSpecId(),
								bean.getSpecLibrary(),
								bean.getSpecDetailType(),
								bean.getSpecDetailClass(),
								bean.getSpecDetailForm(),
								bean.getSpecDetailGroup(),
								bean.getSpecDetailGrade(),
								bean.getSpecDetailStyle(),
								bean.getSpecDetailFinish(),
								bean.getSpecDetailSize(),
								bean.getSpecDetailColor(),
								bean.getSpecDetailOther()
						);
			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			
			if(log.isDebugEnabled()) {
				log.debug("Input Args for PKG_SPEC_UTILITY.P_CREATE_SPEC_DETAIL:" + inArgs);
			}
			
			results = (Vector)factory.doProcedure("PKG_SPEC_UTILITY.P_CREATE_SPEC_DETAIL", inArgs, outArgs);
			
			if( results.size()>0 && results.get(0) != null && !"ok".equalsIgnoreCase((String)results.get(0)))
				errorMsg = (String) results.get(0);
					
		} catch (Exception e) {
				errorMsg = "Error Adding New Spec Detail: "+ bean.getSpecId();
		}

		return errorMsg;
	}
	
	public AddSpecsBean getSpecQCDetails(AddSpecsBean inputBean, String requestId, String companyId) throws BaseException {
		AddSpecsBean specQCDetails = new AddSpecsBean();
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, specQCDetails);
		SearchCriteria criteria = new SearchCriteria();
		SortCriteria sortCriteria = new SortCriteria();
				
		try{
			criteria.addCriterion("requestId", SearchCriterion.EQUALS, ""+requestId);
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, ""+companyId);
			criteria.addCriterion("specId", SearchCriterion.EQUALS, ""+inputBean.getSpecId());
        	
			if(inputBean.getSpecLibrary() != null)
				criteria.addCriterion("specLibrary", SearchCriterion.EQUALS, ""+inputBean.getSpecLibrary());
			
			Vector<AddSpecsBean> results = (Vector)factory.select(criteria, sortCriteria,connection,"CATALOG_ADD_SPEC_DETAIL");
			if(results.size() > 0)
				specQCDetails = results.get(0);
		}
		catch  (Exception e){
			throw new BaseException(e);
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}
		
		return specQCDetails;
	}
	
	public String createNewDetailQC(AddSpecsBean bean, String requestId, String companyId) throws
	BaseException, Exception {
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new AddSpecsBean());
		
        try {
        	// delete
        	StringBuilder deleteQuery = new StringBuilder("delete from CATALOG_ADD_SPEC_DETAIL where" );
        	deleteQuery.append(" request_id = " + requestId);
        	deleteQuery.append(" and company_id = " + SqlHandler.delimitString(companyId));
        	deleteQuery.append(" and spec_id = " + SqlHandler.delimitString(bean.getSpecId()));
        	
        	if(bean.getSpecLibrary() != null)
        		deleteQuery.append("and spec_library = " + SqlHandler.delimitString(bean.getSpecLibrary()));
        	
        	// insert 	
        	StringBuilder detail = new StringBuilder("");
        	StringBuilder insertQuery = new StringBuilder("insert into CATALOG_ADD_SPEC_DETAIL" );
        	insertQuery.append("(request_id,company_id,spec_id,spec_library,");
        	insertQuery.append("spec_detail_type,spec_detail_class,spec_detail_form,spec_detail_group,spec_detail_grade,");
        	insertQuery.append("spec_detail_style,spec_detail_finish,spec_detail_size,spec_detail_color,spec_detail_other,detail) values (");
            
        	insertQuery.append(requestId + ",");
        	insertQuery.append(SqlHandler.delimitString(companyId) + ",");
        	insertQuery.append(SqlHandler.delimitString(bean.getSpecId()) +",");
        	insertQuery.append(SqlHandler.delimitString(bean.getSpecLibrary()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailType()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailClass()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailForm()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailGroup()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailGrade()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailStyle()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailFinish()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailSize()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailColor()) +",");
            insertQuery.append(SqlHandler.delimitString(bean.getSpecDetailOther()) + ",");
            
            if(bean.getSpecDetailType() != null && !bean.getSpecDetailType().equals(""))
            	detail.append("Ty " + bean.getSpecDetailType());
            if(bean.getSpecDetailClass() != null && !bean.getSpecDetailClass().equals(""))
            	detail.append(" Cl " + bean.getSpecDetailClass());
            if(bean.getSpecDetailForm() != null && !bean.getSpecDetailForm().equals(""))
            	detail.append(" Frm " + bean.getSpecDetailForm());
            if(bean.getSpecDetailGroup() != null && !bean.getSpecDetailGroup().equals(""))
            	detail.append(" Grp " + bean.getSpecDetailGroup());
            if(bean.getSpecDetailGrade() != null && !bean.getSpecDetailGrade().equals(""))
            	detail.append(" Gr " + bean.getSpecDetailGrade());
            if(bean.getSpecDetailStyle() != null && !bean.getSpecDetailStyle().equals(""))
            	detail.append(" Sty " + bean.getSpecDetailStyle());
            if(bean.getSpecDetailFinish() != null && !bean.getSpecDetailFinish().equals(""))
            	detail.append(" Fin " + bean.getSpecDetailFinish());
            if(bean.getSpecDetailSize() != null && !bean.getSpecDetailSize().equals(""))
            	detail.append(" Sz " + bean.getSpecDetailSize());
            if(bean.getSpecDetailColor() != null && !bean.getSpecDetailColor().equals(""))
            	detail.append(" Clr " + bean.getSpecDetailColor());
            if(bean.getSpecDetailOther() != null && !bean.getSpecDetailOther().equals(""))
            	detail.append(" Other " + bean.getSpecDetailOther());
            
            insertQuery.append(SqlHandler.delimitString(detail.toString().trim()));
            insertQuery.append(")");
            
        	factory.deleteInsertUpdate(deleteQuery.toString(),connection);		
            factory.deleteInsertUpdate(insertQuery.toString(),connection);			
		} catch (Exception e) {
				errorMsg = "Error Adding New Spec Detail: "+ bean.getSpecId();
				e.printStackTrace();
		}
        finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}

		return errorMsg;
	}
}
