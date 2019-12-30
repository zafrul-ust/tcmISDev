package radian.tcmis.server7.client.hal.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.hal.helpers.*;

public class HALTcmISDBModel  extends TcmISDBModel {
   public HALTcmISDBModel(){
      this("HAL");
   }

   public HALTcmISDBModel(String client){
      super(client);
   }

   public HALTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public HALTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new HALServerResourceBundle();
   }

}