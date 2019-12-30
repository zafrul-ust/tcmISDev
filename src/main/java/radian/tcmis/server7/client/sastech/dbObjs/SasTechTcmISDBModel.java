package radian.tcmis.server7.client.sastech.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.sastech.helpers.*;

public class SasTechTcmISDBModel  extends TcmISDBModel {
   public SasTechTcmISDBModel(){
      this("SAS_TECH");
   }

   public SasTechTcmISDBModel(String client){
      super(client);
   }

   public SasTechTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SasTechTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SasTechServerResourceBundle();
   }

}