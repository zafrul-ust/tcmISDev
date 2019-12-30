package radian.tcmis.server7.client.seagate.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.seagate.helpers.*;

public class SeagateTcmISDBModel  extends TcmISDBModel {
   public SeagateTcmISDBModel(){
      this("Seagate");
   }

   public SeagateTcmISDBModel(String client){
      super(client);
   }

   public SeagateTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SeagateTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SeagateServerResourceBundle();
   }

}