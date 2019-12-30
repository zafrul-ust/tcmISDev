package radian.tcmis.server7.client.samsung.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.samsung.helpers.SamsungServerResourceBundle;

public class SamsungTcmISDBModel  extends TcmISDBModel {
   public SamsungTcmISDBModel(){
      this("SAMSUNG");
   }

   public SamsungTcmISDBModel(String client){
      super(client);
   }

   public SamsungTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SamsungTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SamsungServerResourceBundle();
   }

}