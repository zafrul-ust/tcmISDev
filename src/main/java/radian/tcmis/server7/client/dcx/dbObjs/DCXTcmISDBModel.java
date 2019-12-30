package radian.tcmis.server7.client.dcx.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.dcx.helpers.*;

public class DCXTcmISDBModel  extends TcmISDBModel {
   public DCXTcmISDBModel(){
      this("DCX");
   }

   public DCXTcmISDBModel(String client){
      super(client);
   }

   public DCXTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public DCXTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new DCXServerResourceBundle();
   }

}