package radian.tcmis.server7.client.coproducer.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.coproducer.helpers.CoproducerServerResourceBundle;

public class CoproducerTcmISDBModel  extends TcmISDBModel {
   public CoproducerTcmISDBModel(){
      this("COPRODUCER");
   }

   public CoproducerTcmISDBModel(String client){
      super(client);
   }

   public CoproducerTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public CoproducerTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new CoproducerServerResourceBundle();
   }

}
