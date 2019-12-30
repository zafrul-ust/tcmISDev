package com.tcmis.client.common.process;

import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.client.common.beans.UserDefUserGroupBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

/******************************************************************************
 * Process for UserDefineUserGroupProcess
 * @version 1.0
 *****************************************************************************/
public class UserDefineUserGroupProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public UserDefineUserGroupProcess(String client,String locale)  {
		super(client,locale);
	}

	public Collection getUserGroupData () throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory lineItemFac = new GenericSqlFactory(dbManager,new UserDefUserGroupBean());
		SearchCriteria searchCriteria = new SearchCriteria();
		//searchCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, inputBean.getPrNumber().toString());
		return lineItemFac.select(searchCriteria, null, "line_item_ii_view");
	}

	/*
	select udug.company_id,udug.facility_id,udug.user_group_id,udug.user_group_desc,udug.user_group_type,
udugm.personnel_id,udugm.role,fx_personnel_id_to_name(udugm.personnel_id) member_name
from customer.user_def_user_group udug, customer.user_def_user_group_member udugm
where --udug.user_group_type = 'ReportPublish' and
udug.company_id = udugm.company_id and udug.facility_id = udugm.facility_id
and udug.user_group_id = udugm.user_group_id and udugm.personnel_id = 86030
--and udugm.role = 'publish'
	 */

	
} //end of class