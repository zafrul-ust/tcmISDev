package radian.tcmis.server7.client.maeet.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.maeet.helpers.*;

public class MAEETTcmISDBModel  extends TcmISDBModel {
   public MAEETTcmISDBModel(){
      this("MAEET");
   }

   public MAEETTcmISDBModel(String client){
      super(client);
   }

   public MAEETTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MAEETTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MAEETServerResourceBundle();
   }

}