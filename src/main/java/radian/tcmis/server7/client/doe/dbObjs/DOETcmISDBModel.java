package radian.tcmis.server7.client.doe.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.doe.helpers.*;

public class DOETcmISDBModel  extends TcmISDBModel {
   public DOETcmISDBModel(){
      this("DOE");
   }

   public DOETcmISDBModel(String client){
      super(client);
   }

   public DOETcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public DOETcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new DOEServerResourceBundle();
   }

}