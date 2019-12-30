package com.tcmis.internal.catalog.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.AddUserToEmailListBean;

public class AddUserToEmailListProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public AddUserToEmailListProcess(String client) {
		super(client);
	}

	public AddUserToEmailListProcess(String client, Locale locale) {
		super(client, locale);
	}

	public String getUserGroupDisplay(String userGroupId) throws BaseException, Exception {
		factory.setBean(new AddUserToEmailListBean());

		StringBuilder query = new StringBuilder("select user_group_name from global.vv_user_group");
		query.append(" where user_group_id = ").append(SqlHandler.delimitString(userGroupId));

		String result = factory.selectSingleValue(query.toString());
		return StringHandler.isBlankString(result) ? userGroupId : result;
	}
	
	public Collection getDropDownData(String levelOfControl, String personnelId) throws BaseException, Exception {
		factory.setBean(new AddUserToEmailListBean());
		
		StringBuilder query = new StringBuilder("select");
		if ("company".equalsIgnoreCase(levelOfControl)) {
			query.append(" * from user_company_view where");
			query.append(" personnel_id = ").append(personnelId);
			query.append(" and status = 'A'");
			query.append(" order by company_name asc");
		} else if ("catalog".equalsIgnoreCase(levelOfControl)) {
			query.append(" a.*,c.catalog_id,c.catalog_desc,cc.catalog_company_id");
			query.append(" from user_company_view a, catalog c, catalog_company cc");
			query.append(" where a.personnel_id = ").append(personnelId);
			query.append(" and a.mfr_notification_level in ('Catalog','Company')");
			query.append(" and a.company_id = c.company_id");
			query.append(" and c.company_id = cc.company_id");
			query.append(" and c.catalog_id = cc.catalog_id");
			query.append(" and c.catalog_id <> 'TCM- SALEM'");
			query.append(" and a.status = 'A'");
			query.append(" order by company_name,catalog_desc");
		} else
			return null;
		
		return factory.selectQuery(query.toString());
	}

	public Collection getSearchResult(AddUserToEmailListBean inputBean) throws BaseException, Exception {
		factory.setBean(new AddUserToEmailListBean());

		StringBuilder query = new StringBuilder("select personnel_id, fx_personnel_id_to_name(personnel_id) name, fx_personnel_id_to_email(personnel_id) email");
		if ("company".equalsIgnoreCase(inputBean.getLevelOfControl()) || "Company".equalsIgnoreCase(inputBean.getMfrNotificationLevel())) {
			query.append(" from customer.company_user_group_member");
		} else if ("catalog".equalsIgnoreCase(inputBean.getLevelOfControl())) {
			query.append(" ,catalog_id, catalog_company_id, facility_id, admin");
			query.append(" from customer.user_group_member_catalog");
		} else
			return null;
		query.append(" where user_group_id = ").append(SqlHandler.delimitString(inputBean.getUserGroupId()));
		query.append(" and company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
		if ("catalog".equalsIgnoreCase(inputBean.getLevelOfControl()) && "catalog".equalsIgnoreCase(inputBean.getMfrNotificationLevel()))
			query.append(" and catalog_id = ").append(SqlHandler.delimitString(inputBean.getCatalogId()));

		return factory.selectQuery(query.toString());
	}

	public void insert(AddUserToEmailListBean inputBean) throws BaseException {
		StringBuilder query = new StringBuilder("insert into");
		if ("company".equalsIgnoreCase(inputBean.getLevelOfControl()) || "Company".equalsIgnoreCase(inputBean.getMfrNotificationLevel())) {
			query.append(" customer.company_user_group_member (company_id,user_group_id,personnel_id) values ( ");
			query.append(SqlHandler.delimitString(inputBean.getCompanyId())).append(",");
			query.append(SqlHandler.delimitString(inputBean.getUserGroupId())).append(",");
			query.append(inputBean.getSelectedPersonnelId()).append(")");
		} else if ("catalog".equalsIgnoreCase(inputBean.getLevelOfControl())) {
			query.append(" customer.user_group_member_catalog (company_id,user_group_id,personnel_id,facility_id,admin,catalog_id,catalog_company_id) values (");
			query.append(SqlHandler.delimitString(inputBean.getCompanyId())).append(",");
			query.append(SqlHandler.delimitString(inputBean.getUserGroupId())).append(",");
			query.append(inputBean.getSelectedPersonnelId()).append(",");
			query.append("'All', 'N',");
			query.append(SqlHandler.delimitString(inputBean.getCatalogId())).append(",");
			query.append(SqlHandler.delimitString(inputBean.getCatalogCompanyId())).append(")");
		} else
			return;
		
		factory.deleteInsertUpdate(query.toString());
	}

	public void delete(AddUserToEmailListBean inputBean, AddUserToEmailListBean resultBean) throws BaseException {
		StringBuilder query = new StringBuilder("delete from");
		if ("company".equalsIgnoreCase(inputBean.getLevelOfControl()) || "Company".equalsIgnoreCase(inputBean.getMfrNotificationLevel())) {
			query.append(" customer.company_user_group_member");
			query.append(" where company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(" and user_group_id = ").append(SqlHandler.delimitString(inputBean.getUserGroupId()));
			query.append(" and personnel_id = ").append(resultBean.getPersonnelId());
		} else if ("catalog".equalsIgnoreCase(inputBean.getLevelOfControl())) {
			query.append(" customer.user_group_member_catalog");
			query.append(" where company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(" and user_group_id = ").append(SqlHandler.delimitString(inputBean.getUserGroupId()));
			query.append(" and personnel_id = ").append(resultBean.getPersonnelId());
			query.append(" and facility_id = ").append(SqlHandler.delimitString(resultBean.getFacilityId()));
			query.append(" and admin = ").append(SqlHandler.delimitString(resultBean.getAdmin()));
			query.append(" and catalog_id = ").append(SqlHandler.delimitString(resultBean.getCatalogId()));
			query.append(" and catalog_company_id = ").append(SqlHandler.delimitString(resultBean.getCatalogCompanyId()));
		} else
			return;
		
		factory.deleteInsertUpdate(query.toString());
	}
}
