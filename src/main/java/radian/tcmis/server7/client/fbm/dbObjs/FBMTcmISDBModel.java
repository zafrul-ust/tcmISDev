package radian.tcmis.server7.client.fbm.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.fbm.helpers.*;

public class FBMTcmISDBModel  extends TcmISDBModel {
   public FBMTcmISDBModel(){
      this("FBM");
   }

   public FBMTcmISDBModel(String client){
      super(client);
   }

   public FBMTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public FBMTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new FBMServerResourceBundle();
   }

}