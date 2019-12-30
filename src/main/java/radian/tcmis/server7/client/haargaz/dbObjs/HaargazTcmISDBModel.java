package radian.tcmis.server7.client.haargaz.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.haargaz.helpers.*;

public class HaargazTcmISDBModel  extends TcmISDBModel {
   public HaargazTcmISDBModel(){
      this("Haargaz");
   }

   public HaargazTcmISDBModel(String client){
      super(client);
   }

   public HaargazTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public HaargazTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new HaargazServerResourceBundle();
   }

}