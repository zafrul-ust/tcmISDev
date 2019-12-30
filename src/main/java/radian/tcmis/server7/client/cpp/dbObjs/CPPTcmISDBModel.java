package radian.tcmis.server7.client.cpp.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.cpp.helpers.CPPServerResourceBundle;

public class CPPTcmISDBModel  extends TcmISDBModel {
   public CPPTcmISDBModel(){
      this("CPP");
   }

   public CPPTcmISDBModel(String client){
      super(client);
   }

   public CPPTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public CPPTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new CPPServerResourceBundle();
   }

}
