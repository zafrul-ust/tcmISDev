package radian.tcmis.server7.client.utc.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.utc.helpers.*;

public class UTCTcmISDBModel  extends TcmISDBModel {
   public UTCTcmISDBModel(){
      this("UTC");
   }

   public UTCTcmISDBModel(String client){
      super(client);
   }

   public UTCTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public UTCTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new UTCServerResourceBundle();
   }

}