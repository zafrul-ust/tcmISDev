package radian.tcmis.server7.client.aerojet.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.aeropia.helpers.*;

public class AerojetTcmISDBModel  extends TcmISDBModel {
   public AerojetTcmISDBModel(){
      this("Aerojet");
   }

   public AerojetTcmISDBModel(String client){
      super(client);
   }

   public AerojetTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AerojetTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AeropiaServerResourceBundle();
   }

}