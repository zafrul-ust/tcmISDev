package radian.tcmis.server7.client.gkn.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.gkn.helpers.*;

public class GKNTcmISDBModel  extends TcmISDBModel {
   public GKNTcmISDBModel(){
      this("GKN");
   }

   public GKNTcmISDBModel(String client){
      super(client);
   }

   public GKNTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public GKNTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GKNServerResourceBundle();
   }

}