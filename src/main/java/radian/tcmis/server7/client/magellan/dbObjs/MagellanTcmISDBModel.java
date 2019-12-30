package radian.tcmis.server7.client.magellan.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.magellan.helpers.MagellanServerResourceBundle;

public class MagellanTcmISDBModel  extends TcmISDBModel {
   public MagellanTcmISDBModel(){
      this("MAGELLAN");
   }

   public MagellanTcmISDBModel(String client){
      super(client);
   }

   public MagellanTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MagellanTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MagellanServerResourceBundle();
   }

}
