package radian.tcmis.server7.client.sd.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.sd.helpers.*;

public class SDTcmISDBModel  extends TcmISDBModel {
   public SDTcmISDBModel(){
      this("SD");
   }

   public SDTcmISDBModel(String client){
      super(client);
   }

   public SDTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SDTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SDServerResourceBundle();
   }

}