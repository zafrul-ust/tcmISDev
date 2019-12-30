package radian.tcmis.server7.client.pcc.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.pcc.helpers.PCCServerResourceBundle;

public class PCCTcmISDBModel  extends TcmISDBModel {
   public PCCTcmISDBModel(){
      this("PCC");
   }

   public PCCTcmISDBModel(String client){
      super(client);
   }

   public PCCTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public PCCTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new PCCServerResourceBundle();
   }

}