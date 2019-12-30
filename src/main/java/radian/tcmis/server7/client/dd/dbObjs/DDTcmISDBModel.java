package radian.tcmis.server7.client.dd.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.dd.helpers.*;

public class DDTcmISDBModel  extends TcmISDBModel {
   public DDTcmISDBModel(){
      this("DD");
   }

   public DDTcmISDBModel(String client){
      super(client);
   }

   public DDTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public DDTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new DDServerResourceBundle();
   }

}