package radian.tcmis.server7.client.baz.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.baz.helpers.*;

public class BAZTcmISDBModel  extends TcmISDBModel {
   public BAZTcmISDBModel(){
      this("BAZ");
   }

   public BAZTcmISDBModel(String client){
      super(client);
   }

   public BAZTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public BAZTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new BAZServerResourceBundle();
   }

}