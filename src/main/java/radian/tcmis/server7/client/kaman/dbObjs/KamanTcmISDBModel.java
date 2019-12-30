package radian.tcmis.server7.client.kaman.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.kaman.helpers.KamanServerResourceBundle;

public class KamanTcmISDBModel  extends TcmISDBModel {
   public KamanTcmISDBModel(){
      this("KAMAN");
   }

   public KamanTcmISDBModel(String client){
      super(client);
   }

   public KamanTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public KamanTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new KamanServerResourceBundle();
   }

}
