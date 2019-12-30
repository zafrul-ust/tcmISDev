package radian.tcmis.server7.client.hans_sasserath.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.hans_sasserath.helpers.*;

public class HansSasserathTcmISDBModel  extends TcmISDBModel {
   public HansSasserathTcmISDBModel(){
      this("HansSasserath");
   }

   public HansSasserathTcmISDBModel(String client){
      super(client);
   }

   public HansSasserathTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public HansSasserathTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new HansSasserathServerResourceBundle();
   }

}