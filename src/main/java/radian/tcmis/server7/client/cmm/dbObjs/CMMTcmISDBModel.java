package radian.tcmis.server7.client.cmm.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.cmm.helpers.*;

public class CMMTcmISDBModel  extends TcmISDBModel {
   public CMMTcmISDBModel(){
      this("CMM");
   }

   public CMMTcmISDBModel(String client){
      super(client);
   }

   public CMMTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public CMMTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new CMMServerResourceBundle();
   }

}