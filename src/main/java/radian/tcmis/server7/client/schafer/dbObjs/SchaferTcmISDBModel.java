package radian.tcmis.server7.client.schafer.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.schafer.helpers.*;


public class SchaferTcmISDBModel  extends TcmISDBModel {
   public SchaferTcmISDBModel(){
      this("SCHAFER_GEAR");
   }

   public SchaferTcmISDBModel(String client){
      super(client);
   }

   public SchaferTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SchaferTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SchaferServerResourceBundle();
   }

}