package radian.tcmis.server7.client.dana.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.dana.helpers.*;

public class DanaTcmISDBModel  extends TcmISDBModel {
   public DanaTcmISDBModel(){
      this("Dana");
   }

   public DanaTcmISDBModel(String client){
      super(client);
   }

   public DanaTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public DanaTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new DanaServerResourceBundle();
   }

}