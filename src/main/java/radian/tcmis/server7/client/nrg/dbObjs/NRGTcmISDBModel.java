package radian.tcmis.server7.client.nrg.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.nrg.helpers.*;

public class NRGTcmISDBModel  extends TcmISDBModel {
   public NRGTcmISDBModel(){
      this("NRG");
   }

   public NRGTcmISDBModel(String client){
      super(client);
   }

   public NRGTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public NRGTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new NRGServerResourceBundle();
   }

}