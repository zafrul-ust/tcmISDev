package radian.tcmis.server7.client.lmco.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.lmco.helpers.*;

public class LMCOTcmISDBModel  extends TcmISDBModel {
   public LMCOTcmISDBModel(){
      this("LMCO");
   }

   public LMCOTcmISDBModel(String client){
      super(client);
   }

   public LMCOTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public LMCOTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new LMCOServerResourceBundle();
   }

}