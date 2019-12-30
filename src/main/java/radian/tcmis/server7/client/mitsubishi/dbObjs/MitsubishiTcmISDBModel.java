package radian.tcmis.server7.client.mitsubishi.dbObjs;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.mitsubishi.helpers.MitsubishiServerResourceBundle;

public class MitsubishiTcmISDBModel  extends TcmISDBModel {
   public MitsubishiTcmISDBModel(){
      this("MITSUBISHI");
   }

   public MitsubishiTcmISDBModel(String client){
      super(client);
   }

   public MitsubishiTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public MitsubishiTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new MitsubishiServerResourceBundle();
   }

}