package radian.tcmis.server7.client.boeing.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.boeing.helpers.*;

public class BoeingTcmISDBModel  extends TcmISDBModel {
   public BoeingTcmISDBModel(){
      this("Boeing");
   }

   public BoeingTcmISDBModel(String client){
      super(client);
   }

   public BoeingTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public BoeingTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new BoeingServerResourceBundle();
   }

}