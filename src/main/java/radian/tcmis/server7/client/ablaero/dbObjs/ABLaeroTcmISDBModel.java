package radian.tcmis.server7.client.ablaero.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ablaero.helpers.*;

public class ABLaeroTcmISDBModel  extends TcmISDBModel {
   public ABLaeroTcmISDBModel(){
      this("ABLaero");
   }

   public ABLaeroTcmISDBModel(String client){
      super(client);
   }

   public ABLaeroTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public ABLaeroTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new ABLaeroServerResourceBundle();
   }

}