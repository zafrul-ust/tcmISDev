package radian.tcmis.server7.client.siemens.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.siemens.helpers.*;

public class SiemensTcmISDBModel  extends TcmISDBModel {
   public SiemensTcmISDBModel(){
      this("Siemens");
   }

   public SiemensTcmISDBModel(String client){
      super(client);
   }

   public SiemensTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SiemensTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SiemensServerResourceBundle();
   }

}