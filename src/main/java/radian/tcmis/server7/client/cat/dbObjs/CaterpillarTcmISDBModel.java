package radian.tcmis.server7.client.cat.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.cat.helpers.*;

public class CaterpillarTcmISDBModel  extends TcmISDBModel {
   public CaterpillarTcmISDBModel(){
      this("Caterpillar");
   }

   public CaterpillarTcmISDBModel(String client){
      super(client);
   }

   public CaterpillarTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public CaterpillarTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new CaterpillarServerResourceBundle();
   }

}