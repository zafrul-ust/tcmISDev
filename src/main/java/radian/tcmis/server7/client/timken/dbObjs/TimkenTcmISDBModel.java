package radian.tcmis.server7.client.timken.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.timken.helpers.*;

public class TimkenTcmISDBModel  extends TcmISDBModel {
   public TimkenTcmISDBModel(){
      this("Timken");
   }

   public TimkenTcmISDBModel(String client){
      super(client);
   }

   public TimkenTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public TimkenTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new TimkenServerResourceBundle();
   }

}