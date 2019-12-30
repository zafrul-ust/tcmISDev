package radian.tcmis.server7.client.zwl.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.zwl.helpers.*;

public class ZWLTcmISDBModel  extends TcmISDBModel {
   public ZWLTcmISDBModel(){
      this("ZWL");
   }

   public ZWLTcmISDBModel(String client){
      super(client);
   }

   public ZWLTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public ZWLTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new ZWLServerResourceBundle();
   }

}