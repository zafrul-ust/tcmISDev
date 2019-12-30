package radian.tcmis.server7.client.merck.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.merck.helpers.*;



public class MerckTcmISDBModel  extends TcmISDBModel {
   public MerckTcmISDBModel(){
      this("MERCK");
   }

   public MerckTcmISDBModel(String client){
      super(client);
   }

   public MerckTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MerckTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MerckServerResourceBundle();
   }

}