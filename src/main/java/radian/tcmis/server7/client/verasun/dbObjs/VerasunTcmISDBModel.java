package radian.tcmis.server7.client.verasun.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.verasun.helpers.*;

public class VerasunTcmISDBModel  extends TcmISDBModel {
   public VerasunTcmISDBModel(){
      this("Verasun");
   }

   public VerasunTcmISDBModel(String client){
      super(client);
   }

   public VerasunTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public VerasunTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new VerasunServerResourceBundle();
   }

}