package radian.tcmis.server7.client.imco.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.imco.helpers.*;

public class IMCOTcmISDBModel  extends TcmISDBModel {
   public IMCOTcmISDBModel(){
      this("IMCO");
   }

   public IMCOTcmISDBModel(String client){
      super(client);
   }

   public IMCOTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public IMCOTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new IMCOServerResourceBundle();
   }

}