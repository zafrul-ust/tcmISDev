package com.tcmis.client.openCustomer.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.CompanyBeanFactory;
import com.tcmis.common.admin.factory.MenuItemOvBeanFactory;
import com.tcmis.common.admin.process.PasswordProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.InvalidPasswordException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.EncryptHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process to login user
 * @version 1.0
 *****************************************************************************/
public class LoginProcess extends GenericProcess {

	public LoginProcess(String client) {
		super(client, new Locale("en", "US"));
	}

	@SuppressWarnings("unchecked")
	public PersonnelBean loginOpenCustomer(PersonnelBean loginBean) throws NoDataException, BaseException, Exception {
		if (log.isInfoEnabled()) {
			log.info(loginBean.getLogonId() + " logging in for " + loginBean.getClient());
		}

		String query = "select pkg_open_customer.fx_check_open_customer_login('" + loginBean.getLogonId() + "') from dual";
		if (log.isDebugEnabled()) {
			log.debug("Logon query -> " + query);
		}
		String personnelId = factory.selectSingleValue(query);

		if (StringHandler.isBlankString(personnelId) || "0".equals(personnelId)) {
			throw new NoDataException("error.login.invalid");
		}

		PersonnelBean user = new PersonnelBean();
		try {
			factory.setBean(user);
			Collection data = factory.select(null, null, "table(pkg_open_customer.fx_get_personnel_data(" + personnelId + "))");
			if (data != null && !data.isEmpty()) {				
				user = (PersonnelBean) (data.toArray()[0]);
				
				//check password
				PasswordProcess passwordProcess = new PasswordProcess(getClient(), user);
				if (!passwordProcess.isPasswordValid(loginBean.getPassword())) {
					throw new InvalidPasswordException("error.login.invalidpasswd");
				}
				
				user.setCompanyId(PersonnelBean.OPEN_CUSTOMER_ID);
				user.setCompanyName("Open Customer");

				//now add permissions
				PermissionBean permissionBean = new PermissionBean();
				user.setPermissionBean(permissionBean);
				permissionBean.setPersonnelBean(user);
				SearchCriteria criteria = new SearchCriteria();
				criteria.addCriterion("personnelId", SearchCriterion.EQUALS, "" + user.getPersonnelId());
				if (loginBean.getCompanyId() != null && loginBean.getCompanyId().length() > 0) {
					criteria.addCriterion("companyId", SearchCriterion.EQUALS, loginBean.getCompanyId());
				}

				permissionBean.setLazyLoadCriteria(criteria);

				SearchCriteria menuCriteria = new SearchCriteria();
				menuCriteria.addCriterion("personnelId", SearchCriterion.EQUALS, "" + user.getPersonnelId());
				menuCriteria.addCriterion("parentMenuId", SearchCriterion.EQUALS, "opencustomer");
				MenuItemOvBeanFactory menuFactory = new MenuItemOvBeanFactory(dbManager);
				user.setMenuItemOvBeanCollection(menuFactory.selectObject(menuCriteria));
			}
			else {
				//BeanHandler.copyAttributes(loginBean, user);
				throw new NoDataException("error.login.invalid");
			}
		}
		catch (Exception ex) {
			throw new BaseException(ex);
		}

		return user;
	}

	public boolean isPasswordExpired(PersonnelBean bean) {
		boolean result = false;
		try {
			PasswordProcess passwordProcess = new PasswordProcess(getClient(), bean);
			result = passwordProcess.isPasswordExpired();
		}
		catch (Exception e) {
			log.error("Base Exception in isPasswordExpired: " + e);
		}
		return result;
	}

	public void storeUserLoginData(PersonnelBean loginBean, String URL) throws NoDataException, BaseException, Exception {
		if (!"TCM_OPS".equalsIgnoreCase(getClient())) {
			Collection inArgs = new Vector(3);
			inArgs.add(loginBean.getPersonnelId());
			inArgs.add(loginBean.getCompanyId());
			inArgs.add(URL);
			factory.doProcedure("p_web_app_connection_log", inArgs);
		}
	}
	
//	public int getTimeout(PersonnelBean personnelBean) throws NoDataException, BaseException {
//		CompanyBean bean = null;
//		CompanyBeanFactory companyBeanfactory = new CompanyBeanFactory(dbManager);
//
//		SearchCriteria criteria = new SearchCriteria();
//		criteria.addCriterion("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
//
//		Collection c = companyBeanfactory.select(criteria);
//		if (c.isEmpty()) {
//			throw new NoDataException("error.companyid.invalid");
//		}
//		Iterator iterator = c.iterator();
//		while (iterator.hasNext()) {
//			bean = (CompanyBean) iterator.next();
//		}
//		
//		return bean.getAppTimeout();
//	}
}