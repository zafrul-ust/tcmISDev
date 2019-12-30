package com.tcmis.client.report.process;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.UserDefUserGroupBean;
import com.tcmis.client.report.beans.AdHocTemplateBean;
import com.tcmis.client.report.beans.PublishTemplateInputBean;
import com.tcmis.client.report.factory.AdHocReportDataMapper;
import com.tcmis.client.report.factory.IAdHocReportDataMapper;
import com.tcmis.common.admin.beans.CoDefUserGroupViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
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

/******************************************************************************
 * Process for publish template
 * @version 1.0
 *****************************************************************************/
public class PublishTemplateProcess extends BaseProcess {
	private final String ENHANCED_REPORTING = "EnhancedReportingServices";
	Log log = LogFactory.getLog(this.getClass());
	Locale locale;
	ResourceLibrary library;
	private IAdHocReportDataMapper dataMapper;

	public PublishTemplateProcess(String client, Locale locale) {
		super(client);
		this.locale = locale;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}
	
	public PublishTemplateProcess(String client, Locale locale, IAdHocReportDataMapper dataMapper) {
		this(client, locale);
		this.dataMapper = dataMapper;
	}
	
	private IAdHocReportDataMapper getDataMapper() {
		if (dataMapper == null) {
			dataMapper = new AdHocReportDataMapper(new DbManager(getClient(),locale.toString()));
		}
		return dataMapper;
	}

	public Collection getTemplatePublishToUsers(BigDecimal templateId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PersonnelBean());
		StringBuilder query = new StringBuilder("select a.*,fx_personnel_id_to_name(personnel_id) full_name from ad_hoc_template_personnel a");
		query.append(" where template_id = ").append(templateId);
		query.append(" order by full_name");
		return factory.selectQuery(query.toString());
	}

	public String getUserGroupDescForTemplate(BigDecimal userGroupId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new UserDefUserGroupBean());
		StringBuilder query = new StringBuilder("select distinct user_group_desc from co_def_user_group_role_view where user_group_id = '").append(userGroupId).append("'");

		return factory.selectSingleValue(query.toString());
	}

	public AdHocTemplateBean getTemplate(BigDecimal templateId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AdHocTemplateBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("templateId",SearchCriterion.EQUALS,templateId.toString());
		Collection c = factory.select(criteria,new SortCriteria(),"ad_hoc_template");
		Iterator iter = c.iterator();
		AdHocTemplateBean bean = new AdHocTemplateBean();
		while (iter.hasNext()) {
			bean = (AdHocTemplateBean)iter.next();
			break;
		}
		return bean;
	}

	public Collection getUserPublishUserGroups(PersonnelBean personnelBean, BigDecimal templateId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CoDefUserGroupViewBean());
		StringBuilder query = new StringBuilder("select * from co_def_user_group_role_view where personnel_id = ").append(personnelBean.getPersonnelId());
		query.append(" and user_group_type = 'ReportPublish' and role = 'publish' and user_group_id not in (select nvl(user_group_id,-1212312) from ad_hoc_template where template_id = ").append(templateId);
		query.append(") order by user_group_desc");
		return factory.selectQuery(query.toString());
	}

	public String publishTemplate(PublishTemplateInputBean inputBean, PersonnelBean personnelBean) {
		String result = library.getString("label.templatesuccessfullypublished");
		try {
			log.debug(inputBean.toString());
			//users
			if ("Y".equalsIgnoreCase(inputBean.getUsersTemplateView())) {
				if (!StringHandler.isBlankString(inputBean.getUserIdList())) {
					String[] users = inputBean.getUserIdList().split(";");
					for (int i = 0; i < users.length; i++) {
                        BigDecimal tmpUserId = new BigDecimal(users[i]);
                        if (tmpUserId.equals(personnelBean.getPersonnelIdBigDecimal())) continue;
                        if (!"Ok".equalsIgnoreCase(shareTemplate(inputBean.getTemplateId(),personnelBean.getCompanyId(),tmpUserId, personnelBean))) {
							result = library.getString("msg.tcmiserror");
							inputBean.setSuccessFlag("Failed");
						}
					}
				}
			}
			//user groups
			if ("Y".equalsIgnoreCase(inputBean.getUserGroupsTemplateView())) {
				if (inputBean.getUserGroupIdArray() != null) {
					for (int i = 0; i < inputBean.getUserGroupIdArray().length; i++) {
						if (!"Ok".equalsIgnoreCase(publishTemplateToUserGroupAndCompany(inputBean,"USER_GROUP_ID",inputBean.getUserGroupIdArray()[i], personnelBean))) {
							result = library.getString("msg.tcmiserror");
							inputBean.setSuccessFlag("Failed");
						}
					}
				}
			}
			//company
			if ("Y".equalsIgnoreCase(inputBean.getCompanyTemplateView())) {
				if (!"Ok".equalsIgnoreCase(publishTemplateToUserGroupAndCompany(inputBean,"COMPANY_ID","", personnelBean))) {
					result = library.getString("msg.tcmiserror");
					inputBean.setSuccessFlag("Failed");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = library.getString("msg.tcmiserror");
			inputBean.setSuccessFlag("Failed");
		}
		return result;
	}

	public String shareTemplate(BigDecimal templateId, String companyId, BigDecimal personnelId, PersonnelBean personnelBean) {
		String result = null;
		if (personnelBean.isFeatureReleasedForMyFacilities(ENHANCED_REPORTING, personnelBean.getCompanyId())) {
			try {
				result = getDataMapper().shareTemplate(templateId, companyId, personnelId);
			} catch(Exception e) {
				log.error(e.getMessage());
				result = "failed";
			}
		}
		else {
			result = shareTemplateLegacy(templateId, companyId, personnelId);
		}
		return result;
	}
	
	private String shareTemplateLegacy(BigDecimal templateId, String companyId, BigDecimal personnelId) {
		String result = "Ok";
		try {
			DbManager dbManager = new DbManager(getClient(),locale.toString());
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			Vector inArgs = new Vector(3);
			Vector outArgs = new Vector(1);
			inArgs.add(templateId);
			inArgs.add(companyId);
			inArgs.add(personnelId);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Collection coll = factory.doProcedure("pkg_ad_hoc_report.p_share_template",inArgs,outArgs);
			if (log.isDebugEnabled()) {
				if (coll.size() == 1) {
					if (((Vector)coll).get(0) != null) {
						log.debug(((Vector)coll).get(0).toString());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "failed";
		}
		return result;
	}

	private String publishTemplateToUserGroupAndCompany(PublishTemplateInputBean inputBean, String owner, String userGroupId, PersonnelBean personnelBean) {
		String result = null;
		if (personnelBean.isFeatureReleasedForMyFacilities(ENHANCED_REPORTING, personnelBean.getCompanyId())) {
			try {
				result = getDataMapper().publishTemplateToUserGroupAndCompany(inputBean, owner, userGroupId, personnelBean, getClient());
			} catch(Exception e) {
				log.error(e.getMessage());
				result = "failed";
			}
		}
		else {
			result = publishTemplateToUserGroupAndCompanyLegacy(inputBean.getTemplateId(), owner, userGroupId);
		}
		return result;
	}
	
	private String publishTemplateToUserGroupAndCompanyLegacy(BigDecimal templateId, String owner, String userGroupId) {
		String result = "Ok";
		try {
			DbManager dbManager = new DbManager(getClient(),locale.toString());
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			Vector inArgs = new Vector(2);
			Vector outArgs = new Vector(1);
			inArgs.add(templateId);
			inArgs.add(owner);
			Vector optArgs = new Vector(1);
			if (!StringHandler.isBlankString(userGroupId)) {
				optArgs.add(userGroupId);
			}else {
				optArgs.add("");
			}
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Collection coll = factory.doProcedure("pkg_ad_hoc_report.p_publish_template",inArgs,outArgs,optArgs);
			if (log.isDebugEnabled()) {
				if (coll.size() == 1) {
					if (((Vector)coll).get(0) != null) {
						log.debug(((Vector)coll).get(0).toString());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "failed";
		}
		return result;
	}

	public String unpublishTemplate(PublishTemplateInputBean inputBean, PersonnelBean personnelBean) {
		String result = library.getString("label.templatesuccessfullyunpublished");
		try {
			log.debug(inputBean.toString());
			//myself
			if ("Y".equalsIgnoreCase(inputBean.getMyTemplateView())) {
				if (!"Ok".equalsIgnoreCase(inactivateTemplate(personnelBean.getCompanyId(),inputBean.getTemplateId(), personnelBean))) {
					result = library.getString("msg.tcmiserror");
					inputBean.setSuccessFlag("Failed");
				}
			}

			//users
			if ("Y".equalsIgnoreCase(inputBean.getUsersTemplateView())) {
				String[] users = inputBean.getUserIdArray();
				for (int i = 0; i < users.length; i++) {
					if (!"Ok".equalsIgnoreCase(unshareTemplate(inputBean.getTemplateId(),personnelBean.getCompanyId(),new BigDecimal(users[i]), personnelBean))) {
						result = library.getString("msg.tcmiserror");
						inputBean.setSuccessFlag("Failed");
					}
				}
			}
			//user groups and company
			if ("Y".equalsIgnoreCase(inputBean.getUserGroupsTemplateView()) || "Y".equalsIgnoreCase(inputBean.getCompanyTemplateView())) {
				if (!"Ok".equalsIgnoreCase(deleteTemplate(personnelBean.getCompanyId(),inputBean.getTemplateId(), personnelBean))) {
					result = library.getString("msg.tcmiserror");
					inputBean.setSuccessFlag("Failed");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = library.getString("msg.tcmiserror");
			inputBean.setSuccessFlag("Failed");
		}
		return result;
	}

	private String unshareTemplate(BigDecimal templateId, String companyId, BigDecimal userId, PersonnelBean personnelBean) {
		String result = "";
		if (personnelBean.isFeatureReleasedForMyFacilities(ENHANCED_REPORTING, personnelBean.getCompanyId())) {
			try {
				result = getDataMapper().unshareTemplate(templateId, companyId, userId);
			} catch(Exception e) {
				e.printStackTrace();
				result = "failed";
			}
		}
		else {
			result = unshareTemplateLegacy(templateId, companyId, userId);
		}
		return result;
	}
	
	private String unshareTemplateLegacy(BigDecimal templateId, String companyId, BigDecimal personnelId) {
		String result = "Ok";
		try {
			DbManager dbManager = new DbManager(getClient(),locale.toString());
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			Vector inArgs = new Vector(3);
			Vector outArgs = new Vector(1);
			inArgs.add(templateId);
			inArgs.add(companyId);
			inArgs.add(personnelId);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Collection coll = factory.doProcedure("pkg_ad_hoc_report.p_unshare_template",inArgs,outArgs);
			if (log.isDebugEnabled()) {
				if (coll.size() == 1) {
					if (((Vector)coll).get(0) != null) {
						log.debug(((Vector)coll).get(0).toString());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "failed";
		}
		return result;
	}

	public String activateTemplate(String companyId, BigDecimal templateId) {
		String result = "Ok";
		try {
			DbManager dbManager = new DbManager(getClient(),locale.toString());
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			Vector inArgs = new Vector(2);
			Vector outArgs = new Vector(1);
			inArgs.add(templateId);
			inArgs.add(companyId);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Collection coll = factory.doProcedure("pkg_ad_hoc_report.p_activate_template",inArgs,outArgs);
			if (log.isDebugEnabled()) {
				if (coll.size() == 1) {
					if (((Vector)coll).get(0) != null) {
						log.debug(((Vector)coll).get(0).toString());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "Failed";
		}
		return result;
	}

	public String inactivateTemplate(String companyId, BigDecimal templateId, PersonnelBean personnelBean) {
		String result = "";
		if (personnelBean.isFeatureReleasedForMyFacilities(ENHANCED_REPORTING, personnelBean.getCompanyId())) {
			try {
                result = getDataMapper().inactivateTemplate(templateId, companyId);
			} catch(Exception e) {
				e.printStackTrace();
				result = "Failed";
			}
		}
		else {
			result = inactivateTemplateLegacy(companyId, templateId);
		}
		return result;
	}
	
	private String inactivateTemplateLegacy(String companyId, BigDecimal templateId) {
		String result = "Ok";
		try {
			DbManager dbManager = new DbManager(getClient(),locale.toString());
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			Vector inArgs = new Vector(2);
			Vector outArgs = new Vector(1);
			inArgs.add(templateId);
			inArgs.add(companyId);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Collection coll = factory.doProcedure("pkg_ad_hoc_report.p_inactivate_template",inArgs,outArgs);
			if (log.isDebugEnabled()) {
				if (coll.size() == 1) {
					if (((Vector)coll).get(0) != null) {
						log.debug(((Vector)coll).get(0).toString());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "Failed";
		}
		return result;
	}

	public String deleteTemplate(String companyId, BigDecimal templateId, PersonnelBean personnelBean) {
		String result = "";
		if (personnelBean.isFeatureReleasedForMyFacilities(ENHANCED_REPORTING, personnelBean.getCompanyId())) {
			try {
				result = getDataMapper().deleteTemplate(templateId, companyId);
			} catch (Exception e) {
				e.printStackTrace();
				result = "Failed";
			}
		}
		else {
			result = deleteTemplateLegacy(companyId, templateId);
		}
		return result;
	}
	
	private String deleteTemplateLegacy(String companyId, BigDecimal templateId) {
		String result = "Ok";
		try {
			DbManager dbManager = new DbManager(getClient(),locale.toString());
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			Vector inArgs = new Vector(2);
			Vector outArgs = new Vector(1);
			inArgs.add(companyId);
			inArgs.add(templateId);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Collection coll = factory.doProcedure("pkg_ad_hoc_report.p_delete_template",inArgs,outArgs);
			if (log.isDebugEnabled()) {
				if (coll.size() == 1) {
					if (((Vector)coll).get(0) != null) {
						log.debug(((Vector)coll).get(0).toString());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "Failed";
		}
		return result;
	}

} //end of class
