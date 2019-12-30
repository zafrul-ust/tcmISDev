package com.tcmis.internal.distribution.process;

import java.io.File;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.Globals;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.AddSpecsBean;
import com.tcmis.internal.distribution.beans.OrderCsrViewBean;


public class AddSpecsProcess extends GenericProcess {
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public AddSpecsProcess(String client) {
		super(client);
	}

	public AddSpecsProcess(String client, String locale) {
		super(client, locale);
	}
	
	public Collection<AddSpecsBean> getSpecDetails(String detail) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new AddSpecsBean());
		Collection<AddSpecsBean> c = null;
		String query = "select * from vv_spec_detail_"+detail;
		c = factory.selectQuery(query.toString());
		
		return c;
	}

	public Collection<AddSpecsBean> getSearchResult(AddSpecsBean inputBean, PersonnelBean personnelBean) throws BaseException {

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AddSpecsBean());
		DbManager dbManager = new DbManager(getClient(),getLocale());
		Collection<AddSpecsBean> c = null;
		
		StringBuilder query= new StringBuilder("select * from table (tcm_ops.pkg_spec_utility.fx_spec_not_used_for_item(");

		query.append( "'" ).append(
				StringHandler.emptyIfNull(inputBean.getSearchArgument()) ).append((null!=inputBean.getItemId()?"',":"'" )).append(

						StringHandler.emptyIfNull(inputBean.getItemId())
				).append( "))");

		c = factory.selectQuery(query.toString());
		
	

		return c;
	}

	public Object[] createNewSpec(AddSpecsBean bean) throws
	BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		Vector errorMessages = new Vector();
		boolean isValid = false;
		Vector results = null;
		Vector specDetailResults = null;
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		
		String content = "";
		if(bean.getTheFile() != null && bean.getTheFile().length() > 0)
			content = bean.getContent();

		if(bean!=null )
		{

			try {
				if( !StringHandler.isBlankString(bean.getSpecName()))
				{
					isValid = true;
					Collection inArgs =
						buildProcedureInput(
								bean.getSpecName() ,
								bean.getSpecVersion(),
								bean.getSpecAmendment(),
								bean.getSpecDate(),
								content,
								bean.getSpecOrg(),
								bean.getSpecDetailType(),
								bean.getSpecDetailClass(),
								bean.getSpecDetailForm(),
								bean.getSpecDetailGroup(),
								bean.getSpecDetailGrade(),
								bean.getSpecDetailStyle(),
								bean.getSpecDetailFinish(),
								bean.getSpecDetailSize(),
								bean.getSpecDetailColor(),
								//bean.getSpecDetailMethod(),
								//bean.getSpecDetailCondition(),
								//bean.getSpecDetailDash(),
								//bean.getSpecDetailNote(),
								bean.getSpecDetailOther()
						);
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					
					Collection inArgs2 = new Vector(2);
					if(bean.getSpecLibrary() != null && bean.getSpecLibrary().length() > 0)
						inArgs2.add(bean.getSpecLibrary());
					else
						inArgs2.add("temporary");
					inArgs2.add(null);
					
					if(log.isDebugEnabled()) {
						log.debug("Input Args for pkg_spec_utility.p_create_spec  :" + inArgs);
					}
					results = (Vector)factory.doProcedure("pkg_spec_utility.p_create_spec", inArgs, outArgs, inArgs2);
					if( results.size()>0 && results.get(1) != null && !"ok".equalsIgnoreCase((String)results.get(1)))
					{
						errorCode = (String) results.get(1);
						log.info("Error in Procedure pkg_spec_utility.p_create_spec: "+bean.getSpecName()+" Error Code "+errorCode+" ");
						errorMessages.add(errorCode);

					}
					
				}
			} catch (Exception e) {
				errorMsg = "Error Adding New Spec: "+ bean.getSpecName();
				errorMessages.add(errorMsg);
			}
		}

		Object[] objs = {errorMessages,isValid,results.get(0)};
		return objs;
	}


	public String uploadFile(AddSpecsBean inputBean) throws BaseException {
		String errorMessage = "";
		String extension ="";
		if (inputBean.getTheFile() != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			extension = inputBean.getTheFile().getName().substring(inputBean.getTheFile().getName().indexOf("."),inputBean.getTheFile().getName().length());
			DbManager dbManager = new DbManager(getClient());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			// reading the file and putting the values in a bean
			try {
				// For linux server tetsing use the following
				FileHandler.copy(inputBean.getTheFile(), new File("/webdata/html/"+inputBean.getLocation()+"/"+inputBean.getSpecName()));

				// For local testing use the following
				//FileHandler.copy(inputBean.getFile(), new File("c:\\"+inputBean.getLocation()+"\\"+inputBean.getSpecName()+extension));
			}
			catch (Exception e) {
				e.printStackTrace();
				errorMessage = "Error upload file:" + e.getMessage();
				DbSelectException ex = new DbSelectException(Globals.DB_UPDATE_EXCEPTION);
				ex.setRootCause(e);
				throw ex;
			}
		}
		else {
			log.debug("file is null");
		}
		return errorMessage;
	}

	public Object[] getSpecId(String specLibrary, String specName, String specVersion, String specAdmentment) throws BaseException {

		Object[] obj = new Object[2];
		Vector inArgs = buildProcedureInput(specLibrary, specName, specVersion, specAdmentment);

		DbManager dbManager = new DbManager(getClient(),getLocale());
		Collection outArgs = new Vector(2);
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );

		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		if (log.isDebugEnabled()) {
			log.debug("pkg_spec_utility.p_spec_id"+inArgs);
		}
		try {
			Vector v = (Vector) procFactory.doProcedure("pkg_spec_utility.p_spec_id", inArgs, outArgs);
			obj[0] = v.get(0);
			obj[1] = v.get(1);
			return obj;
		}catch(Exception ex){
			obj[1] = library.getString("generic.error");
			return obj;
		}

	}
	
	public Collection getSpecDetails(String specId, String specLibrary, String detail) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AddSpecsBean());

		SearchCriteria criteria = new SearchCriteria();
		
		criteria.addCriterion("specId", SearchCriterion.EQUALS, specId);
		criteria.addCriterion("specLibrary", SearchCriterion.EQUALS, specLibrary);
		criteria.addCriterion("detail", SearchCriterion.EQUALS, detail);

		return factory.select(criteria, new SortCriteria(), "spec_detail");

	}
}
