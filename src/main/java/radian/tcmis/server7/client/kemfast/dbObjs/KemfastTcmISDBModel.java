package radian.tcmis.server7.client.kemfast.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.kemfast.helpers.*;

public class KemfastTcmISDBModel  extends TcmISDBModel {
   public KemfastTcmISDBModel(){
      this("Kemfast");
   }

   public KemfastTcmISDBModel(String client){
      super(client);
   }

   public KemfastTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public KemfastTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new KemfastServerResourceBundle();
   }

}