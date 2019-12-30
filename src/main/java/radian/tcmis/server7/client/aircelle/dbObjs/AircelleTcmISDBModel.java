package radian.tcmis.server7.client.aircelle.dbObjs;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.aircelle.helpers.AircelleServerResourceBundle;

public class AircelleTcmISDBModel  extends TcmISDBModel {
   public AircelleTcmISDBModel(){
      this("AIRCELLE");
   }

   public AircelleTcmISDBModel(String client){
      super(client);
   }

   public AircelleTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public AircelleTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new AircelleServerResourceBundle();
   }

}