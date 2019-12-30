package radian.tcmis.server7.client.fedco.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.fedco.helpers.*;

public class FedcoTcmISDBModel  extends TcmISDBModel {
   public FedcoTcmISDBModel(){
      this("Fedco");
   }

   public FedcoTcmISDBModel(String client){
      super(client);
   }

   public FedcoTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public FedcoTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new FedcoServerResourceBundle();
   }

}