package radian.tcmis.server7.client.drs.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.drs.helpers.*;

public class DRSTcmISDBModel  extends TcmISDBModel {
   public DRSTcmISDBModel(){
      this("DRS");
   }

   public DRSTcmISDBModel(String client){
      super(client);
   }

   public DRSTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public DRSTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new DRSServerResourceBundle();
   }
}