package radian.tcmis.server7.client.pepsi.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.pepsi.helpers.*;

public class PepsiTcmISDBModel  extends TcmISDBModel {
   public PepsiTcmISDBModel(){
      this("Pepsi");
   }

   public PepsiTcmISDBModel(String client){
      super(client);
   }

   public PepsiTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public PepsiTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new PepsiServerResourceBundle();
   }

}