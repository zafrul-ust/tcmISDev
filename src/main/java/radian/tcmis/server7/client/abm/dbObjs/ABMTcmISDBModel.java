package radian.tcmis.server7.client.abm.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.abm.helpers.*;

public class ABMTcmISDBModel  extends TcmISDBModel {
   public ABMTcmISDBModel(){
      this("ABM");
   }

   public ABMTcmISDBModel(String client){
      super(client);
   }

   public ABMTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public ABMTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new ABMServerResourceBundle();
   }

}