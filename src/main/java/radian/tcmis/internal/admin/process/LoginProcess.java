package radian.tcmis.internal.admin.process;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import radian.tcmis.internal.admin.beans.PersonnelBean;
import radian.tcmis.internal.admin.beans.PermissionBean;
import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.exceptions.NoDataException;
import radian.tcmis.internal.admin.factory.PersonnelBeanFactory;
import radian.tcmis.internal.admin.factory.UserGroupMemberIgBeanFactory;
import radian.tcmis.internal.admin.factory.UserGroupMemberBeanFactory;
import radian.tcmis.common.framework.BaseProcess;
import radian.tcmis.common.util.SearchCriteria;
import radian.tcmis.common.util.SearchCriterion;
import radian.tcmis.server7.share.dbObjs.Password;

/******************************************************************************
 * Process to login user
 * @version 1.0
 *****************************************************************************/
public class LoginProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public LoginProcess(String client) {
    super(client);
  }

  public PersonnelBean loginWeb(PersonnelBean loginBean) throws NoDataException,
      BaseException, Exception {
    if (log.isInfoEnabled()) {
      log.info(loginBean.getLogonId() + " logging in for " +
               loginBean.getClient());
    }
    PersonnelBean bean = null;
    try {
      //authenticate user
      if (Password.checkPasswordWeb(getDbModel(), loginBean.getLogonId(),
                                    loginBean.getPassword())) {
        PersonnelBeanFactory factory = new PersonnelBeanFactory(getClient());
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("logonId",
                              SearchCriterion.EQUALS,
                              loginBean.getLogonId());

        if (loginBean.getCompanyId() != null &&
            loginBean.getCompanyId().length() > 0) {
          criteria.addCriterion("companyId",
                                SearchCriterion.EQUALS,
                                loginBean.getCompanyId());
        }
        Collection c = factory.select(criteria, getDbModel());
        if (log.isDebugEnabled()) {
          log.debug("Collection size=" + c.size());
        }
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
          bean = (PersonnelBean) iterator.next();
        }
        //need to add client to the bean
        bean.setClient(loginBean.getClient());
        //now add permissions
        UserGroupMemberIgBeanFactory secFactory = new
            UserGroupMemberIgBeanFactory(getClient());
        criteria = new SearchCriteria();
        criteria.addCriterion("personnelId",
                              SearchCriterion.EQUALS,
                              "" + bean.getPersonnelId());
        if (loginBean.getCompanyId() != null &&
            loginBean.getCompanyId().length() > 0) {
          criteria.addCriterion("companyId",
                                SearchCriterion.EQUALS,
                                loginBean.getCompanyId());
        }

        PermissionBean permissionBean = new PermissionBean();
        permissionBean.setUserGroupMemberIgBeanCollection(secFactory.select(
            criteria, getDbModel()));
        bean.setPermissionBean(permissionBean);

        //now add permissions
        UserGroupMemberBeanFactory usgmemFactory = new
            UserGroupMemberBeanFactory(getClient());
        criteria = new SearchCriteria();
        criteria.addCriterion("personnelId",
                              SearchCriterion.EQUALS,
                              "" + bean.getPersonnelId());
        if (loginBean.getCompanyId() != null &&
            loginBean.getCompanyId().length() > 0) {
          criteria.addCriterion("companyId",
                                SearchCriterion.EQUALS,
                                loginBean.getCompanyId());
        }

        permissionBean.setUserGroupMemberBeanCollection(usgmemFactory.select(
            criteria, getDbModel()));
        bean.setFacPermissionBean(permissionBean);
      }
      else {
        if (log.isDebugEnabled()) {
          log.debug("Login failed for " + loginBean.getLogonId() +
                    " on " + loginBean.getClient());
        }
      }
    }
    finally {
      try {
        closeDbModel();
      }
      catch (Exception e) {
        //ignore
      }
    }
    return bean;
  }

}