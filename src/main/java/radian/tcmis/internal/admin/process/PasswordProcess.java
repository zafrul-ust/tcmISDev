
package radian.tcmis.internal.admin.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import radian.tcmis.internal.admin.beans.PasswordBean;
import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.framework.BaseProcess;
import radian.tcmis.server7.share.dbObjs.Password;

/******************************************************************************
 * Process to create MSDS excel report
 * @version 1.0
 *****************************************************************************/
public class PasswordProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PasswordProcess(String client) {
    super(client);
  }

  public void changePassword(PasswordBean bean)
      throws BaseException, Exception {
    //first check if the user typed in the same password twice
    if(this.validateBean(bean)) {
      Password password = new Password(getDbModel(),
                                       bean.getLogon(),
                                       bean.getPasswordOld());
      password.changePassword(bean.getPasswordNew());
    }
    else {
      throw new BaseException("Invalid password change");
    }
  }

  private boolean validateBean(PasswordBean bean) {
    boolean b = true;
    if(bean.getLogon() == null || bean.getLogon().length() < 1) {
      b = false;
    }
    if(bean.getPasswordOld() == null || bean.getPasswordOld().length() < 1) {
      b = false;
    }
    if(bean.getPasswordNew() == null || bean.getPasswordNew().length() < 1) {
      b = false;
    }
    if(bean.getPasswordCopy() == null || bean.getPasswordCopy().length() < 1) {
      b = false;
    }
    if(!bean.getPasswordNew().equalsIgnoreCase(bean.getPasswordCopy())) {
      b = false;
    }
    return b;
  }

}

