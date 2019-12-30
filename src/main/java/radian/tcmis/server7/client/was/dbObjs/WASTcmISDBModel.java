package radian.tcmis.server7.client.was.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.web.servlets.was.WASServerResourceBundle;



public class WASTcmISDBModel  extends TcmISDBModel {
   public WASTcmISDBModel(){
      this("WHIPPANY_ACTUATION_SYSTEMS");
   }

   public WASTcmISDBModel(String client){
      super(client);
   }

   public WASTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public WASTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new WASServerResourceBundle();
   }

}