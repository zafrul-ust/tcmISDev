package radian.tcmis.server7.client.hrgivon.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.hrgivon.helpers.*;

public class HrgivonTcmISDBModel  extends TcmISDBModel {
   public HrgivonTcmISDBModel(){
      this("Hrgivon");
   }

   public HrgivonTcmISDBModel(String client){
      super(client);
   }

   public HrgivonTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public HrgivonTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new HrgivonServerResourceBundle();
   }

}