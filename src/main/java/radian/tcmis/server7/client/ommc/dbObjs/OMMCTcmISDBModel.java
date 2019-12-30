package radian.tcmis.server7.client.ommc.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ommc.helpers.*;

public class OMMCTcmISDBModel  extends TcmISDBModel {
   public OMMCTcmISDBModel(){
      this("OMMC");
   }

   public OMMCTcmISDBModel(String client){
      super(client);
   }

   public OMMCTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public OMMCTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new OMMCServerResourceBundle();
   }

}