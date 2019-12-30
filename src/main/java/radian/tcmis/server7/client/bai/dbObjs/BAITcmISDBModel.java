package radian.tcmis.server7.client.bai.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.bai.helpers.*;

public class BAITcmISDBModel  extends TcmISDBModel {
   public BAITcmISDBModel(){
      this("BAI");
   }

   public BAITcmISDBModel(String client){
      super(client);
   }

   public BAITcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public BAITcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new BAIServerResourceBundle();
   }

}