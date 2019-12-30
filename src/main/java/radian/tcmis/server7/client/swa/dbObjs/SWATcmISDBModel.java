package radian.tcmis.server7.client.swa.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.swa.helpers.*;

public class SWATcmISDBModel  extends TcmISDBModel {
   public SWATcmISDBModel(){
      this("SWA");
   }

   public SWATcmISDBModel(String client){
      super(client);
   }

   public SWATcmISDBModel(String client, String logonVersion){
      super(client,logonVersion);
   }

   public SWATcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SWAServerResourceBundle();
   }

}