package radian.tcmis.server7.client.pge.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.pge.helpers.*;

public class PGETcmISDBModel  extends TcmISDBModel {
   public PGETcmISDBModel(){
      this("PGE");
   }

   public PGETcmISDBModel(String client){
      super(client);
   }

   public PGETcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public PGETcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new PGEServerResourceBundle();
   }

}