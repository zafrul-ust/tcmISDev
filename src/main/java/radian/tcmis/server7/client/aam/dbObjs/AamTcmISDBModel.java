package radian.tcmis.server7.client.aam.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.aam.helpers.AamServerResourceBundle;

public class AamTcmISDBModel  extends TcmISDBModel {
   public AamTcmISDBModel(){
      this("AAM");
   }

   public AamTcmISDBModel(String client){
      super(client);
   }

   public AamTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AamTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AamServerResourceBundle();
   }

}
