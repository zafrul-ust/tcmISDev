package radian.tcmis.server7.client.ba.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ba.helpers.*;

public class BATcmISDBModel  extends TcmISDBModel {
   public BATcmISDBModel(){
      this("BA");
   }

   public BATcmISDBModel(String client){
      super(client);
   }

   public BATcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public BATcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new BAServerResourceBundle();
   }

}