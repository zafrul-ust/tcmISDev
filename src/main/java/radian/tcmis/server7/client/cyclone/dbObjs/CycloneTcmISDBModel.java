package radian.tcmis.server7.client.cyclone.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.cyclone.helpers.*;

public class CycloneTcmISDBModel  extends TcmISDBModel {
   public CycloneTcmISDBModel(){
      this("Cyclone");
   }

   public CycloneTcmISDBModel(String client){
      super(client);
   }

   public CycloneTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public CycloneTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new CycloneServerResourceBundle();
   }

}