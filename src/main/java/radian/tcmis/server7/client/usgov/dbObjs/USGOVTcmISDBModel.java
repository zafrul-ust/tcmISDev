package radian.tcmis.server7.client.usgov.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.usgov.helpers.*;

public class USGOVTcmISDBModel  extends TcmISDBModel {
   public USGOVTcmISDBModel(){
      this("USGOV");
   }

   public USGOVTcmISDBModel(String client){
      super(client);
   }

   public USGOVTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public USGOVTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new USGOVServerResourceBundle();
   }

}