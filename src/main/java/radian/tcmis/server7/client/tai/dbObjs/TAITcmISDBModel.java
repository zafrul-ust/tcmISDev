package radian.tcmis.server7.client.tai.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.tai.helpers.*;

public class TAITcmISDBModel  extends TcmISDBModel {
   public TAITcmISDBModel(){
      this("TAI");
   }

   public TAITcmISDBModel(String client){
      super(client);
   }

   public TAITcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public TAITcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new TAIServerResourceBundle();
   }

}