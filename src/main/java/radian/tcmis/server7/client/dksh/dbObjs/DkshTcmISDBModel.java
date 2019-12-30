package radian.tcmis.server7.client.dksh.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.dksh.helpers.DkshServerResourceBundle;

public class DkshTcmISDBModel  extends TcmISDBModel {
   public DkshTcmISDBModel(){
      this("DKSH");
   }

   public DkshTcmISDBModel(String client){
      super(client);
   }

   public DkshTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public DkshTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new DkshServerResourceBundle();
   }

}