package radian.tcmis.server7.client.amt.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.amt.helpers.AmtServerResourceBundle;

public class AmtTcmISDBModel  extends TcmISDBModel {
   public AmtTcmISDBModel(){
      this("AMT");
   }

   public AmtTcmISDBModel(String client){
      super(client);
   }

   public AmtTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AmtTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AmtServerResourceBundle();
   }

}
