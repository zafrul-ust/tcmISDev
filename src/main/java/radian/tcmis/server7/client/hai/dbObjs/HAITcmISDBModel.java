package radian.tcmis.server7.client.hai.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.hai.helpers.HAIServerResourceBundle;

public class HAITcmISDBModel  extends TcmISDBModel {
   public HAITcmISDBModel(){
      this("HAI");
   }

   public HAITcmISDBModel(String client){
      super(client);
   }

   public HAITcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public HAITcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new HAIServerResourceBundle();
   }

}