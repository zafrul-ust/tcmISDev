package radian.tcmis.server7.client.cal.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.cal.helpers.*;

public class CALTcmISDBModel  extends TcmISDBModel {
   public CALTcmISDBModel(){
      this("CAL");
   }

   public CALTcmISDBModel(String client){
      super(client);
   }

   public CALTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public CALTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new CALServerResourceBundle();
   }

}