package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

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
import com.tcmis.client.common.beans.CommentBean;
import com.tcmis.client.common.beans.MaterialBean;
import com.tcmis.client.common.beans.SecLblIconsInLabelViewBean;
import com.tcmis.client.common.beans.SecondaryLabelDataBean;
import com.tcmis.client.common.factory.VvSecLblDataTypCmmntOvBeanFactory;

/******************************************************************************
 * Process for ItemCatalogProcess
 * @version 1.0
 *****************************************************************************/
public class SecondaryLabelInformationProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public SecondaryLabelInformationProcess(String client,String locale) {
		super(client,locale);
	}

	public MaterialBean searchMaterialInfo(String materialId, String facilityId) {
		MaterialBean c = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialBean());
			StringBuilder query = new StringBuilder("select fx_mfg_desc_from_material(g.material_id) manufacturer, material_desc, trade_name, ");
			query.append("fx_company_catalog_msds_id(g.material_id, '").append(facilityId).append("') customer_msds_no "); 
			query.append("from material g where material_Id =").append(materialId);
			Vector<MaterialBean>  result  = (Vector<MaterialBean>) factory.selectQuery(query.toString());
			return result.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public Collection getTypeNameDropdowns(String facilityId) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			VvSecLblDataTypCmmntOvBeanFactory factory = new VvSecLblDataTypCmmntOvBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
				
			return factory.selectObject(criteria, new SortCriteria());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Collection getNameColl(String facilityId,String typeId) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			VvSecLblDataTypCmmntOvBeanFactory factory = new VvSecLblDataTypCmmntOvBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
			criteria.addCriterion("typeId", SearchCriterion.EQUALS, typeId);
				
			return factory.selectObject(criteria, new SortCriteria());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Collection getSecondaryLabelData(String materialId, String facilityId) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SecondaryLabelDataBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("materialId", SearchCriterion.EQUALS, materialId);
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
				
			return factory.select(criteria, new SortCriteria(), "SECONDARY_LABEL_DATA");
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return result; 
	}
	
    public Collection getPrintSecondaryLabelData(String materialId, String facilityId) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SecondaryLabelDataBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("materialId", SearchCriterion.EQUALS, materialId);
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
			
			 SortCriteria sort = new SortCriteria();
			 sort.addCriterion("commentId");

			return factory.select(criteria, sort, "SECONDARY_LABEL_DATA_VIEW");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    public Collection getIcons(String materialId, String facilityId) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SecLblIconsInLabelViewBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("materialId", SearchCriterion.EQUALS, materialId);
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);

            SortCriteria sort = new SortCriteria();

			return factory.select(criteria, sort, "SEC_LBL_ICONS_IN_LABEL_VIEW");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    public Collection getPrintLabelIcons(String materialId, String facilityId) throws BaseException {
		Collection result = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SecLblIconsInLabelViewBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("materialId", SearchCriterion.EQUALS, materialId);
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
            criteria.addCriterion("iconInLabelFlag", SearchCriterion.EQUALS, "Y");

            SortCriteria sort = new SortCriteria();

			return factory.select(criteria, sort, "SEC_LBL_ICONS_IN_LABEL_VIEW");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Collection getDefaultIcons() throws BaseException {
		Collection<SecLblIconsInLabelViewBean>  results = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SecLblIconsInLabelViewBean());
			results  = factory.select(new SearchCriteria(), new SortCriteria(), "VV_label_hazard_icon");
			
			if(results.size() > 0){
				for (SecLblIconsInLabelViewBean bean : results) {
					bean.setIconInLabelFlag("N");
				}
			}
				
			return results;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public Collection updateMaterialInfo(BigDecimal personnelId,String materialId, String facilityId, Collection<SecondaryLabelDataBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			   String query  = "delete from SECONDARY_LABEL_DATA " +
							   "where facility_id = '" + facilityId + "' and material_id = " + materialId;
				genericSqlFactory.deleteInsertUpdate(query);
		}catch (Exception e) {
				e.printStackTrace();
		}

		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		for (SecondaryLabelDataBean bean : beans) {
				try {
					inArgs = new Vector(7);
					inArgs.add("");
					inArgs.add(facilityId);
					inArgs.add(materialId);
					inArgs.add(bean.getTypeId());
					inArgs.add(bean.getCommentId());
					inArgs.add(bean.getCommentAltTxt());
					inArgs.add(personnelId);
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));

					Vector error = (Vector) factory.doProcedure("PKG_SECONDARY_LABEL.P_INS_LABEL_DATA", inArgs, outArgs);

					if(error.size()>0 && error.get(0) != null)
					{
						 String errorCode = (String) error.get(0);
				     	 log.info(" inserting: " + bean.getTypeId()+ errorCode);
				     	 if(!"ok".equalsIgnoreCase(errorCode))
				     		 errorMessages.add(errorCode);
					}  
				}
				catch (Exception e) {
					errorMsg = "Error inserting type: " + bean.getTypeId();
					errorMessages.add(errorMsg);
				}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	
	public Collection updateIcons(BigDecimal personnelId, String materialId, String facilityId, Collection<SecLblIconsInLabelViewBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);

		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		for (SecLblIconsInLabelViewBean bean : beans) {
			if("true".equals(bean.getOk()) && "N".equalsIgnoreCase(bean.getIconInLabelFlag())) {
				try {
					inArgs = new Vector(5);
					inArgs.add("");
					inArgs.add(facilityId);
					inArgs.add(materialId);
					inArgs.add(bean.getIconId());
					inArgs.add(personnelId);
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));

					Vector error = (Vector) factory.doProcedure("PKG_SECONDARY_LABEL.P_INS_LABEL_ICONS_DATA", inArgs, outArgs);

					if(error.size()>0 && error.get(0) != null)
					{
						 String errorCode = (String) error.get(0);
				     	 log.info(" inserting: " + bean.getIconName()+ errorCode);
				     	 if(!"ok".equalsIgnoreCase(errorCode))
				     		 errorMessages.add(errorCode);
					}  
				}
				catch (Exception e) {
					errorMsg = "Error inserting icon: " + bean.getIconName();
					errorMessages.add(errorMsg);
				}
			} else if(!"true".equals(bean.getOk()) && "Y".equalsIgnoreCase(bean.getIconInLabelFlag())) {
				try {
					inArgs = new Vector(6);
					inArgs.add("");
					inArgs.add(facilityId);
					inArgs.add(materialId);
					inArgs.add(bean.getIconId());
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));

					Vector error = (Vector) factory.doProcedure("PKG_SECONDARY_LABEL.P_DEL_LABEL_ICONS_DATA", inArgs, outArgs);

					if(error.size()>0 && error.get(0) != null)
					{
						 String errorCode = (String) error.get(0);
				     	 log.info(" inserting: " + bean.getIconName()+ errorCode);
				     	 if(!"ok".equalsIgnoreCase(errorCode))
				     		 errorMessages.add(errorCode);
					}  
				}
				catch (Exception e) {
					errorMsg = "Error inserting icon: " + bean.getIconName();
					errorMessages.add(errorMsg);
				}
			}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	


} //end of class

