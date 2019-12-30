package radian.tcmis.server7.client.getrag.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.getrag.helpers.*;

public class GetragTcmISDBModel  extends TcmISDBModel {
   public GetragTcmISDBModel(){
      this("Getrag");
   }

   public GetragTcmISDBModel(String client){
      super(client);
   }

   public GetragTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public GetragTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GetragServerResourceBundle();
   }

}