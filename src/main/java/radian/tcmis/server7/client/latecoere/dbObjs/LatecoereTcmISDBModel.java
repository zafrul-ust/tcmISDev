package radian.tcmis.server7.client.latecoere.dbObjs;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.latecoere.helpers.LatecoereServerResourceBundle;

public class LatecoereTcmISDBModel  extends TcmISDBModel {
   public LatecoereTcmISDBModel(){
      this("LATECOERE");
   }

   public LatecoereTcmISDBModel(String client){
      super(client);
   }

   public LatecoereTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public LatecoereTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new LatecoereServerResourceBundle();
   }

}