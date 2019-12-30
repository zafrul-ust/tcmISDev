package radian.tcmis.server7.client.internal.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.internal.helpers.*;

public class InternalTcmISDBModel  extends TcmISDBModel {
   public InternalTcmISDBModel(){
      this("tcm_ops");
   }

   public InternalTcmISDBModel(String client){
      super(client);
   }

   public InternalTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public InternalTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new InternalServerResourceBundle();
   }

}