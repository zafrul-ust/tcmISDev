package radian.tcmis.server7.client.ibm.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.ibm.helpers.IBMServerResourceBundle;

public class IBMTcmISDBModel  extends TcmISDBModel {
   public IBMTcmISDBModel(){
      this("IBM");
   }

   public IBMTcmISDBModel(String client){
      super(client);
   }

   public IBMTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public IBMTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new IBMServerResourceBundle();
   }

}