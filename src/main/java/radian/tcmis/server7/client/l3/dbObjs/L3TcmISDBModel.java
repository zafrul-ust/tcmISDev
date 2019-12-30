package radian.tcmis.server7.client.l3.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.l3.helpers.*;

public class L3TcmISDBModel  extends TcmISDBModel {
   public L3TcmISDBModel(){
      this("L3");
   }

   public L3TcmISDBModel(String client){
      super(client);
   }

   public L3TcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public L3TcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new L3ServerResourceBundle();
   }

}