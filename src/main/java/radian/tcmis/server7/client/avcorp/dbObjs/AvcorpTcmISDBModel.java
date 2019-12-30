package radian.tcmis.server7.client.avcorp.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.avcorp.helpers.*;

public class AvcorpTcmISDBModel  extends TcmISDBModel {
   public AvcorpTcmISDBModel(){
      this("Avcorp");
   }

   public AvcorpTcmISDBModel(String client){
      super(client);
   }

   public AvcorpTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AvcorpTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AvcorpServerResourceBundle();
   }

}