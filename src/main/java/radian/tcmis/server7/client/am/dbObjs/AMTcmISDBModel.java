package radian.tcmis.server7.client.am.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.am.helpers.*;

public class AMTcmISDBModel  extends TcmISDBModel {
   public AMTcmISDBModel(){
      this("AM");
   }

   public AMTcmISDBModel(String client){
      super(client);
   }

   public AMTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AMTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AMServerResourceBundle();
   }

}