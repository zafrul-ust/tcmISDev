package radian.tcmis.server7.client.tambour.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.tambour.helpers.*;

public class TambourTcmISDBModel  extends TcmISDBModel {
   public TambourTcmISDBModel(){
      this("Tambour");
   }

   public TambourTcmISDBModel(String client){
      super(client);
   }

   public TambourTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public TambourTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new TambourServerResourceBundle();
   }

}