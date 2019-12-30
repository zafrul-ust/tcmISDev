package radian.tcmis.server7.client.standardaero.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.standardaero.helpers.*;

public class StandardAeroTcmISDBModel  extends TcmISDBModel {
   public StandardAeroTcmISDBModel(){
      this("STANDARD_AERO");
   }

   public StandardAeroTcmISDBModel(String client){
      super(client);
   }

   public StandardAeroTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public StandardAeroTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new StandardAeroServerResourceBundle();
   }

}