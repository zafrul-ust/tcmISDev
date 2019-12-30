package com.tcmis.client.openCustomer.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.EncryptHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.openCustomer.beans.UserCustomerAdminViewBean;

/**
 * ***************************************************************************
 * Process for Personnel
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class UserProfileProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public UserProfileProcess(String client) {
		super(client);
	}

	public UserProfileProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getSearchResult(PersonnelBean
			bean) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		PersonnelBeanFactory factory = new
		PersonnelBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (bean.getPersonnelId() != 0) {
			criteria.addCriterion("personnelId", SearchCriterion.EQUALS, "" + bean.getPersonnelId());
		}

		return factory.select(criteria);
	}

	public String createNewUserPermission(PersonnelBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			
		StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM TABLE (PKG_OPEN_CUSTOMER.FX_GET_USER_CUST_ADMIN_DATA(");
		query.append(bean.getPersonnelId()).append(", NULL)) WHERE LOWER(ADMIN_ROLE) LIKE '%admin%'");
			
//	log.debug(query);	
		return factory.selectSingleValue(query.toString());
	}

	public String updateValue(int userId, PersonnelBean bean) throws
	BaseException {
		Collection inArgs = new Vector();
		Collection outArgs = new Vector();
		String error = "";
		try {
			inArgs.add(bean.getPersonnelId());

			if (bean.getLogonId() != null) {
				inArgs.add(bean.getLogonId());
			} else {
				inArgs.add("");
			}

			if (!StringHandler.isBlankString(bean.getEmail())) {
				inArgs.add(bean.getEmail());
			} else {
				inArgs.add("***delete***");
			}

			/*if (bean.getPassword() != null && bean.getPassword() != "") {
				inArgs.add(EncryptHandler.encrypt(bean.getPassword()));
			} else {
				inArgs.add("");
			}*/

			if (bean.getPassword() != null && bean.getPassword() != "") {
				inArgs.add(EncryptHandler.pbkdf2Encrypt(bean.getPassword()));
			} else {
				inArgs.add("");
			}
			
			if (!StringHandler.isBlankString(bean.getFirstName())) {
				inArgs.add(bean.getFirstName());
			} else {
				inArgs.add("***delete***");
			}

			if (!StringHandler.isBlankString(bean.getLastName())) {
				inArgs.add(bean.getLastName());
			} else {
				inArgs.add("***delete***");
			}

			if (!StringHandler.isBlankString(bean.getMidInitial())) {
				inArgs.add(bean.getMidInitial());
			} else {
				inArgs.add("***delete***");
			}

			if (!StringHandler.isBlankString(bean.getPhone())) {
				inArgs.add(bean.getPhone());
			} else {
				inArgs.add("***delete***");
			}

			if (!StringHandler.isBlankString(bean.getAltPhone())) {
				inArgs.add(bean.getAltPhone());
			} else {
				inArgs.add("***delete***");
			}

			if (!StringHandler.isBlankString(bean.getPager())) {
				inArgs.add(bean.getPager());
			} else {
				inArgs.add("***delete***");
			}

			if (!StringHandler.isBlankString(bean.getFax())) {
				inArgs.add(bean.getFax());
			} else {
				inArgs.add("***delete***");
			}

			if (!StringHandler.isBlankString(bean.getPrinterLocation())) {
				inArgs.add(bean.getPrinterLocation());
			} else {
				inArgs.add("***delete***");
			}

			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Vector inArgs2 = new Vector();
			if (bean.getPasswordExpireDate() != null) {
				inArgs2.add(bean.getPasswordExpireDate());
			} else {
				inArgs2.add("");
			}

			//modifer
			inArgs2.add(userId);

			if (!StringHandler.isBlankString(bean.getFontSizePreference())) {
				inArgs2.add(bean.getFontSizePreference());
			} else {
				inArgs2.add("Medium");
			}

			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection c = procFactory.doProcedure("PKG_OPEN_CUSTOMER.P_PERSONNEL_UPDATE", inArgs, outArgs, inArgs2);
			if (log.isDebugEnabled()) {
				if (c.size() == 1) {
					Vector v = (Vector) c;
					String errorMsg = (String) v.get(0);
					log.debug("Message from procedure (PKG_OPEN_CUSTOMER.P_PERSONNEL_UPDATE):" + error);
					
					if(errorMsg!= null && errorMsg.length() > 0 && !errorMsg.equalsIgnoreCase("ok"))
						error =  errorMsg;
				}
			}
		} catch (Exception ex) {
			error = "error calling PKG_OPEN_CUSTOMER.P_PERSONNEL_UPDATE";
			throw new BaseException(ex.getMessage());
		}
		
		return error;

	}

	public Collection createNewUser(PersonnelBean bean,int userId, BigDecimal defaultCustomer) throws BaseException {
		Collection inArgs = new Vector();
		Collection outArgs = new Vector();

		inArgs.add(bean.getNewLogonId());
		inArgs.add(bean.getEmail());
		inArgs.add(userId);
		inArgs.add(defaultCustomer);

		outArgs.add(java.sql.Types.NUMERIC);
		outArgs.add(java.sql.Types.VARCHAR);

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Collection c = procFactory.doProcedure("PKG_OPEN_CUSTOMER.P_PERSONNEL_CREATE", inArgs, outArgs);
		return c;
	} //end of method

	public PersonnelBean refreshPersonalBean(PersonnelBean loginBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new PersonnelBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_OPEN_CUSTOMER.FX_GET_PERSONNEL_DATA(");
		query.append(StringHandler.emptyIfNull(loginBean.getPersonnelId()));
		query.append("))");
		
		Vector<PersonnelBean> results = (Vector) factory.selectQuery(query.toString());
		return results.get(0);
		
	}
	
	public Collection getDefaultCustomerColl(String userId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserCustomerAdminViewBean());
		
		StringBuilder query = new StringBuilder("SELECT * FROM TABLE (PKG_OPEN_CUSTOMER.FX_GET_USER_CUST_ADMIN_DATA(");
		query.append(new BigDecimal(userId));
		query.append(", NULL)) WHERE LOWER(ADMIN_ROLE) LIKE '%admin%'");
		
		return factory.selectQuery(query.toString());
		
	}
}
