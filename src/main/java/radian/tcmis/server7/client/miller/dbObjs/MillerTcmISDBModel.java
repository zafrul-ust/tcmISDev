package radian.tcmis.server7.client.miller.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.miller.helpers.*;

public class MillerTcmISDBModel  extends TcmISDBModel {
   public MillerTcmISDBModel(){
      this("Miller");
   }

   public MillerTcmISDBModel(String client){
      super(client);
   }

   public MillerTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MillerTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MillerServerResourceBundle();
   }

}