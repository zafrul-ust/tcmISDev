package radian.tcmis.server7.client.northrop.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.tai.helpers.*;

public class NorthropGrummanTcmISDBModel  extends TcmISDBModel {
   public NorthropGrummanTcmISDBModel(){
      this("NORTHROP_GRUMMAN");
   }

   public NorthropGrummanTcmISDBModel(String client){
      super(client);
   }

   public NorthropGrummanTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public NorthropGrummanTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new TAIServerResourceBundle();
   }

}