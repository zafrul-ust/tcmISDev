package radian.tcmis.server7.client.fec.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.fec.helpers.*;

public class FECTcmISDBModel  extends TcmISDBModel {
   public FECTcmISDBModel(){
      this("FEC");
   }

   public FECTcmISDBModel(String client){
      super(client);
   }

   public FECTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public FECTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new FECServerResourceBundle();
   }

}