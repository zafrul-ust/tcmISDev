package radian.tcmis.server7.client.nalco.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.nalco.helpers.*;

public class NalcoTcmISDBModel  extends TcmISDBModel {
   public NalcoTcmISDBModel(){
      this("Nalco");
   }

   public NalcoTcmISDBModel(String client){
      super(client);
   }

   public NalcoTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public NalcoTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new NalcoServerResourceBundle();
   }

}