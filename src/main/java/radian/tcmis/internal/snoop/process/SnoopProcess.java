package radian.tcmis.internal.snoop.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.framework.BaseProcess;
import radian.tcmis.internal.snoop.beans.UserEnvLogBean;
import radian.tcmis.internal.snoop.factory.UserEnvLogBeanFactory;

/******************************************************************************
 * Process to load currencies.
 * @version 1.0
 *****************************************************************************/
public class SnoopProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public SnoopProcess(String client) {
    super(client);
  }

  public void registerFlash(UserEnvLogBean bean) throws BaseException {
    try {
      UserEnvLogBeanFactory factory = new UserEnvLogBeanFactory(getClient());
      factory.insert(bean, getDbModel());
    }
    catch(Exception e) {
      BaseException be = new BaseException(e);
      be.setRootCause(e);
      throw be;
    }
    finally {
      try {
        closeDbModel();
      }
      catch(Exception ex) {
        //ignore
      }
    }
  }

}