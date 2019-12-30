package radian.tcmis.server7.client.oma.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.oma.helpers.*;

public class OMATcmISDBModel  extends TcmISDBModel {
   public OMATcmISDBModel(){
      this("OMA");
   }

   public OMATcmISDBModel(String client){
      super(client);
   }

   public OMATcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public OMATcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new OMAServerResourceBundle();
   }

}