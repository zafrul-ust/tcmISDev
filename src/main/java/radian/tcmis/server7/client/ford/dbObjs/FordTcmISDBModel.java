package radian.tcmis.server7.client.ford.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ford.helpers.*;

public class FordTcmISDBModel  extends TcmISDBModel {
   public FordTcmISDBModel(){
      this("Ford");
   }

   public FordTcmISDBModel(String client){
      super(client);
   }

   public FordTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public FordTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new FordServerResourceBundle();
   }

}