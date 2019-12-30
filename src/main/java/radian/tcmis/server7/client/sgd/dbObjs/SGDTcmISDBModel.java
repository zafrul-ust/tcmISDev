package radian.tcmis.server7.client.sgd.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.sgd.helpers.*;

public class SGDTcmISDBModel  extends TcmISDBModel {
   public SGDTcmISDBModel(){
      this("SGD");
   }

   public SGDTcmISDBModel(String client){
      super(client);
   }

   public SGDTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SGDTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SGDServerResourceBundle();
   }

}