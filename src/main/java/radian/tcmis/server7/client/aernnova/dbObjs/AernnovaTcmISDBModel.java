package radian.tcmis.server7.client.aernnova.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.aernnova.helpers.AernnovaServerResourceBundle;

public class AernnovaTcmISDBModel  extends TcmISDBModel {
   public AernnovaTcmISDBModel(){
      this("AERNNOVA");
   }

   public AernnovaTcmISDBModel(String client){
      super(client);
   }

   public AernnovaTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AernnovaTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AernnovaServerResourceBundle();
   }

}
