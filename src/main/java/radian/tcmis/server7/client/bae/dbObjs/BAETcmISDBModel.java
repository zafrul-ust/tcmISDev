package radian.tcmis.server7.client.bae.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.bae.helpers.*;

public class BAETcmISDBModel  extends TcmISDBModel {
   public BAETcmISDBModel(){
      this("BAE");
   }

   public BAETcmISDBModel(String client){
      super(client);
   }

   public BAETcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public BAETcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new BAEServerResourceBundle();
   }

}