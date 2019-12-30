package radian.tcmis.server7.client.iai.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.iai.helpers.*;

public class IAITcmISDBModel  extends TcmISDBModel {
   public IAITcmISDBModel(){
      this("IAI");
   }

   public IAITcmISDBModel(String client){
      super(client);
   }

   public IAITcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public IAITcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new IAIServerResourceBundle();
   }

}