package radian.tcmis.server7.client.ge.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ge.helpers.*;

public class GETcmISDBModel  extends TcmISDBModel {
   public GETcmISDBModel(){
      this("GE");
   }

   public GETcmISDBModel(String client){
      super(client);
   }

   public GETcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public GETcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new GEServerResourceBundle();
   }

}