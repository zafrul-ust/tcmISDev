package radian.tcmis.server7.client.kanfit.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.kanfit.helpers.*;

public class KanfitTcmISDBModel  extends TcmISDBModel {
   public KanfitTcmISDBModel(){
      this("Kanfit");
   }

   public KanfitTcmISDBModel(String client){
      super(client);
   }

   public KanfitTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public KanfitTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new KanfitServerResourceBundle();
   }

}