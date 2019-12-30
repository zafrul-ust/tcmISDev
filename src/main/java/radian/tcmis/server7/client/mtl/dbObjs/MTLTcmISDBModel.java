package radian.tcmis.server7.client.mtl.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.mtl.helpers.*;

public class MTLTcmISDBModel  extends TcmISDBModel {
   public MTLTcmISDBModel(){
      this("MTL");
   }

   public MTLTcmISDBModel(String client){
      super(client);
   }

   public MTLTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MTLTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MTLServerResourceBundle();
   }

}