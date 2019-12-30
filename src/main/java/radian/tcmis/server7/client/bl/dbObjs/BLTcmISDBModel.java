package radian.tcmis.server7.client.bl.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.bl.helpers.*;

public class BLTcmISDBModel  extends TcmISDBModel {
   public BLTcmISDBModel(){
      this("BL");
   }

   public BLTcmISDBModel(String client){
      super(client);
   }

   public BLTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public BLTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new BLServerResourceBundle();
   }

}