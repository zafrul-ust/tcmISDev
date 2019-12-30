package radian.tcmis.server7.client.optimetal.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.optimetal.helpers.*;

public class OptimetalTcmISDBModel  extends TcmISDBModel {
   public OptimetalTcmISDBModel(){
      this("Optimetal");
   }

   public OptimetalTcmISDBModel(String client){
      super(client);
   }

   public OptimetalTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public OptimetalTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new OptimetalServerResourceBundle();
   }

}