package radian.tcmis.server7.client.moog.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.moog.helpers.MoogServerResourceBundle;

public class MoogTcmISDBModel  extends TcmISDBModel {
   public MoogTcmISDBModel(){
      this("MOOG");
   }

   public MoogTcmISDBModel(String client){
      super(client);
   }

   public MoogTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MoogTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MoogServerResourceBundle();
   }

}