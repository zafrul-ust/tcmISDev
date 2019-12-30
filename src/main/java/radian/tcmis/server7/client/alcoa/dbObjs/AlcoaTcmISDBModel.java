package radian.tcmis.server7.client.alcoa.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.alcoa.helpers.*;

public class AlcoaTcmISDBModel  extends TcmISDBModel {
   public AlcoaTcmISDBModel(){
      this("Alcoa");
   }

   public AlcoaTcmISDBModel(String client){
      super(client);
   }

   public AlcoaTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AlcoaTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AlcoaServerResourceBundle();
   }

}