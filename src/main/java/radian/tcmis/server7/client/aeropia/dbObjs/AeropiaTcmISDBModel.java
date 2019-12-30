package radian.tcmis.server7.client.aeropia.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.aeropia.helpers.*;

public class AeropiaTcmISDBModel  extends TcmISDBModel {
   public AeropiaTcmISDBModel(){
      this("Aeropia");
   }

   public AeropiaTcmISDBModel(String client){
      super(client);
   }

   public AeropiaTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AeropiaTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AeropiaServerResourceBundle();
   }

}