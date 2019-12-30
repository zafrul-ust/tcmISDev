package radian.tcmis.server7.client.gm.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.gm.helpers.*;

public class GMTcmISDBModel  extends TcmISDBModel {
   public GMTcmISDBModel(){
      this("GM");
   }

   public GMTcmISDBModel(String client){
      super(client);
   }

   public GMTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public GMTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GMServerResourceBundle();
   }

}