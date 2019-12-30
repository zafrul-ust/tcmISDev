package radian.tcmis.server7.client.ray.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ray.helpers.*;

public class RayTcmISDBModel  extends TcmISDBModel {
   public RayTcmISDBModel(){
      this("Ray");
   }

   public RayTcmISDBModel(String client){
      super(client);
   }

   public RayTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public RayTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new RayServerResourceBundle();
   }

}