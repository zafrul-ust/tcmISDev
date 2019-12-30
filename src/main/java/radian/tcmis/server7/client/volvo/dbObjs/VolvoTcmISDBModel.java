package radian.tcmis.server7.client.volvo.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.volvo.helpers.*;

public class VolvoTcmISDBModel  extends TcmISDBModel {
   public VolvoTcmISDBModel(){
      this("Volvo");
   }

   public VolvoTcmISDBModel(String client){
      super(client);
   }

   public VolvoTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public VolvoTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new VolvoServerResourceBundle();
   }

}