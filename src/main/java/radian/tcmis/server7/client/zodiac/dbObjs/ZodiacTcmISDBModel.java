package radian.tcmis.server7.client.zodiac.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.zodiac.helpers.ZodiacServerResourceBundle;

public class ZodiacTcmISDBModel  extends TcmISDBModel {
   public ZodiacTcmISDBModel(){
      this("ZODIAC");
   }

   public ZodiacTcmISDBModel(String client){
      super(client);
   }

   public ZodiacTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public ZodiacTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new ZodiacServerResourceBundle();
   }

}
