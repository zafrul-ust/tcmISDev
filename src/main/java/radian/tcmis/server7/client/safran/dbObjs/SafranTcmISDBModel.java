package radian.tcmis.server7.client.safran.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.safran.helpers.SafranServerResourceBundle;

public class SafranTcmISDBModel  extends TcmISDBModel {
   public SafranTcmISDBModel(){
      this("SAFRAN");
   }

   public SafranTcmISDBModel(String client){
      super(client);
   }

   public SafranTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SafranTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SafranServerResourceBundle();
   }

}
