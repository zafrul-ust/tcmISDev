package radian.tcmis.server7.client.tomkins.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.tomkins.helpers.TomkinsServerResourceBundle;

public class TomkinsTcmISDBModel  extends TcmISDBModel {
   public TomkinsTcmISDBModel(){
      this("Tomkins");
   }

   public TomkinsTcmISDBModel(String client){
      super(client);
   }

   public TomkinsTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public TomkinsTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new TomkinsServerResourceBundle();
   }

}