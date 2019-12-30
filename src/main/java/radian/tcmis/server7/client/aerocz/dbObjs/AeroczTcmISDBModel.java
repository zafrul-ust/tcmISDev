package radian.tcmis.server7.client.aerocz.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.aerocz.helpers.*;

public class AeroczTcmISDBModel  extends TcmISDBModel {
   public AeroczTcmISDBModel(){
      this("Aerocz");
   }

   public AeroczTcmISDBModel(String client){
      super(client);
   }

   public AeroczTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AeroczTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AeroczServerResourceBundle();
   }

}