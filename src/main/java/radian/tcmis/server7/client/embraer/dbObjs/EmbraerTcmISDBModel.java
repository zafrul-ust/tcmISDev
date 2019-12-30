package radian.tcmis.server7.client.embraer.dbObjs;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.embraer.helpers.EmbraerServerResourceBundle;

public class EmbraerTcmISDBModel  extends TcmISDBModel {
   public EmbraerTcmISDBModel(){
      this("EMBRAER");
   }

   public EmbraerTcmISDBModel(String client){
      super(client);
   }

   public EmbraerTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public EmbraerTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new EmbraerServerResourceBundle();
   }

}