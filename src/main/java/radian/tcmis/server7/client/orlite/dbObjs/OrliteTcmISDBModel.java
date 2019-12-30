package radian.tcmis.server7.client.orlite.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.orlite.helpers.*;

public class OrliteTcmISDBModel  extends TcmISDBModel {
   public OrliteTcmISDBModel(){
      this("Orlite");
   }

   public OrliteTcmISDBModel(String client){
      super(client);
   }

   public OrliteTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public OrliteTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new OrliteServerResourceBundle();
   }

}