package radian.tcmis.common.framework;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.common.db.GenericTcmISDBModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  private String client;
  private TcmISDBModel dbModel;

  public BaseProcess(String client) {
    if(client.equalsIgnoreCase("RAYTHEON")) {
      client = "RAY";
    }
    this.client = client;
  }

  public String getClient() {
    return this.client;
  }

  protected TcmISDBModel getDbModel() {
      if(dbModel == null)
        dbModel = new GenericTcmISDBModel(getClient(),"2");
    return dbModel;
  }

  protected void closeDbModel() {
    try {
      dbModel.close();
      dbModel = null;
    }
    catch(Exception e) {
      //ignore
      dbModel = null;
    }
  }


}