package radian.tcmis.server7.client.algat.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.algat.helpers.*;

public class AlgatTcmISDBModel  extends TcmISDBModel {
   public AlgatTcmISDBModel(){
      this("Algat");
   }

   public AlgatTcmISDBModel(String client){
      super(client);
   }

   public AlgatTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AlgatTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AlgatServerResourceBundle();
   }

}