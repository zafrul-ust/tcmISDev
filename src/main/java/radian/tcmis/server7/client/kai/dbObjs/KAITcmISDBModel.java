package radian.tcmis.server7.client.kai.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.kai.helpers.KAIServerResourceBundle;

public class KAITcmISDBModel  extends TcmISDBModel {
   public KAITcmISDBModel(){
      this("KOREA_AEROSPACE_INDUSTRIES");
   }

   public KAITcmISDBModel(String client){
      super(client);
   }

   public KAITcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public KAITcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new KAIServerResourceBundle();
   }

}