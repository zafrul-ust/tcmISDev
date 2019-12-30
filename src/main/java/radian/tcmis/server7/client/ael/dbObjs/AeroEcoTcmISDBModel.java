package radian.tcmis.server7.client.ael.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ael.helpers.*;

public class AeroEcoTcmISDBModel  extends TcmISDBModel {
   public AeroEcoTcmISDBModel(){
      this("AeroEco");
   }

   public AeroEcoTcmISDBModel(String client){
      super(client);
   }

   public AeroEcoTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AeroEcoTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AeroEcoServerResourceBundle();
   }

}