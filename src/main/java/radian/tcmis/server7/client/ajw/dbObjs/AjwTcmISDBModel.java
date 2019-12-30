package radian.tcmis.server7.client.ajw.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.dksh.helpers.DkshServerResourceBundle;

public class AjwTcmISDBModel  extends TcmISDBModel {
   public AjwTcmISDBModel(){
      this("DKSH");
   }

   public AjwTcmISDBModel(String client){
      super(client);
   }

   public AjwTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AjwTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new DkshServerResourceBundle();
   }

}