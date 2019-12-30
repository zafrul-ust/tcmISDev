package radian.tcmis.server7.client.eaton.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.eaton.helpers.EatonServerResourceBundle;

public class EatonTcmISDBModel  extends TcmISDBModel {
   public EatonTcmISDBModel(){
      this("EATON");
   }

   public EatonTcmISDBModel(String client){
      super(client);
   }

   public EatonTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public EatonTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new EatonServerResourceBundle();
   }

}
