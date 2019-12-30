package radian.tcmis.server7.client.hudson.dbObjs;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.hudson.helpers.HudsonServerResourceBundle;

public class HudsonTcmISDBModel  extends TcmISDBModel {
   public HudsonTcmISDBModel(){
      this("HUDSON");
   }

   public HudsonTcmISDBModel(String client){
      super(client);
   }

   public HudsonTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public HudsonTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new HudsonServerResourceBundle();
   }

}