package radian.tcmis.server7.client.zf.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.zf.helpers.*;

public class ZFTcmISDBModel  extends TcmISDBModel {
   public ZFTcmISDBModel(){
      this("ZF");
   }

   public ZFTcmISDBModel(String client){
      super(client);
   }

   public ZFTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public ZFTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new ZFServerResourceBundle();
   }

}