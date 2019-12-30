package radian.tcmis.server7.client.goodrich.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.goodrich.helpers.*;

public class GoodrichTcmISDBModel  extends TcmISDBModel {
   public GoodrichTcmISDBModel(){
      this("Goodrich");
   }

   public GoodrichTcmISDBModel(String client){
      super(client);
   }

   public GoodrichTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public GoodrichTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GoodrichServerResourceBundle();
   }

}