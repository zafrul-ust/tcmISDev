package radian.tcmis.server7.client.spirit.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.spirit.helpers.SpiritServerResourceBundle;

public class SpiritTcmISDBModel  extends TcmISDBModel {
   public SpiritTcmISDBModel(){
      this("SPIRIT");
   }

   public SpiritTcmISDBModel(String client){
      super(client);
   }

   public SpiritTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public SpiritTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SpiritServerResourceBundle();
   }

}
