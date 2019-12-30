package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.helpers.*;

public class GenericTcmISDBModel  extends TcmISDBModel {
   public GenericTcmISDBModel(String client){
      super(client);
   }

   public GenericTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GenericServerResourceBundle(this.getClient());
   }

}

