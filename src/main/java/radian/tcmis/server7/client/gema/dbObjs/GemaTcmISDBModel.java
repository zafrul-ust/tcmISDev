package radian.tcmis.server7.client.gema.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.gema.helpers.*;

public class GemaTcmISDBModel  extends TcmISDBModel {
   public GemaTcmISDBModel(){
      this("GEMA");
   }

   public GemaTcmISDBModel(String client){
      super(client);
   }

   public GemaTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public GemaTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GemaServerResourceBundle();
   }

}